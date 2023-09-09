package cn.javgo.drools.service.drools;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.WaitStrategies;
import com.github.rholder.retry.WaitStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Http Service（用于进行同步 HTTP 回调）实现类
 */
@Service
public class HttpServiceImpl implements HttpService{

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServiceImpl.class);

    /**
     * 最大重试次数
     */
    private final int MAX_RETRIES = 3;

    /**
     * 超时时间，单位：秒 （暂时不用）
     */
    private final int TIMEOUT_SECONDS = 10;

    /**
     * 执行 HTTP 请求
     *
     * @param url 回调下游业务的 URL
     * @param method 请求方法
     * @param body 请求体
     * @return 返回的 HTTP 响应体内容
     */
    @Override
    public String executeHttpRequest(String url, String method, String body) {
        return null;
    }

    /**
     * 执行 HTTP 请求
     *
     * @param request 需要执行的 HTTP 请求（即回调下游业务的 HTTP 请求）
     * @return 返回的 HTTP 响应体内容
     */
    @Override
    public String executeHttpRequest(HttpRequestBase request) {
        // 构建 Http 客户端（可关闭的，即每次请求后会自动关闭 Http 客户端）
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.useSystemProperties();
        CloseableHttpClient closeableHttpClient = builder.build();

        // 创建一个线程执行任务，执行 HTTP 请求拿到响应
        Callable<CloseableHttpResponse> callable = () -> closeableHttpClient.execute(request);

        // 设置等待策略：固定每次重试间隔 1 秒
        WaitStrategy waitStrategy = WaitStrategies.fixedWait(1, TimeUnit.SECONDS);

        // 设置重试器：定义在异常时进行重试，使用上面的等待策略，并设置最大重试次数
        Retryer<CloseableHttpResponse> retryer = RetryerBuilder.<CloseableHttpResponse>newBuilder()
                .retryIfException()
                .withWaitStrategy(waitStrategy)
                .withStopStrategy((attempt, result) -> attempt >= MAX_RETRIES)
                .build();

        // 执行重试器
        try {
            // 执行 HTTP 请求
            CloseableHttpResponse httpResponse = retryer.call(callable);
            // 获取 HTTP 响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            // 获取 HTTP 响应体内容
            String responseBody = EntityUtils.toString(httpResponse.getEntity());

            // 打印日志
            LOGGER.info("HTTP request status code: {}", statusCode);
            LOGGER.info("HTTP response body: {}", responseBody);

            // 关闭 HTTP 响应
            httpResponse.close();
            // 关闭 HTTP 客户端
            closeableHttpClient.close();

            // 返回 HTTP 响应体内容
            return responseBody;
        }catch (Exception e){
            // 打印日志
            LOGGER.error("HTTP request failed after {} retries", MAX_RETRIES, e);
            // 抛出运行时异常
            throw new RuntimeException(e);
        }
    }
}
