package cn.javgo.drools.component.listener;

import cn.javgo.drools.service.drools.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.rule.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Slf4j
@Component
public class AgendaCallBackEventListener implements AgendaEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgendaCallBackEventListener.class);

    @Resource
    private HttpService httpService;

    @Override
    public void matchCreated(MatchCreatedEvent event) {

    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {

    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {

    }

    /**
     * 匹配规则并执行后回调方法
     * @param event 规则匹配后事件(其中包含了规则的执行结果)
     */
    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        // 从监听器事件中获取规则匹配的结果，其中包含了规则的执行结果和规则的元数据信息等信息
        Match match = event.getMatch();
        // 获取规则的执行结果
        Object result = match.getObjects().toArray()[0];
    }

    private void executeClientCallback(String url, String body){
        // 创建一个 HTTP POST 请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求体
        httpPost.setEntity(new StringEntity(body, "UTF-8"));

        try {
            // 执行 HTTP 请求，并获取响应体
            String response = httpService.executeHttpRequest(httpPost);
            // 打印响应体
            LOGGER.info("executeClientCallback response:{}", response);
        }catch (Exception e){
            LOGGER.error("executeClientCallback error:{}", e.getMessage());
        }
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {

    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {

    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }
}
