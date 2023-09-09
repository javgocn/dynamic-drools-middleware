package cn.javgo.drools.service.drools;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * Http Service（用于进行同步 HTTP 回调）
 */
public interface HttpService {

    /**
     * 执行 HTTP 请求
     *
     * @param url 回调下游业务的 URL
     * @param method 请求方法
     * @param body 请求体
     * @return 返回的 HTTP 响应体内容
     */
    public String executeHttpRequest(String url, String method, String body);

    /**
     * 执行 HTTP 请求
     *
     * @param request 需要执行的 HTTP 请求（即回调下游业务的 HTTP 请求）
     * @return 返回的 HTTP 响应体内容
     */
    public String executeHttpRequest(HttpRequestBase request);
}
