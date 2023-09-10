package cn.javgo.drools.listener;

import cn.javgo.drools.service.drools.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.kie.api.event.rule.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 规则回调监听器（设计模式：观察者模式）<br/>
 */
@Slf4j
@Component
public class AgendaCallBackEventListener implements AgendaEventListener {

    @Resource
    private HttpService HttpService;

    /**
     * 客户端回调地址（此处仅作演示，实际项目中应该通过配置文件或数据库获取）
     */
    private String clientUrl = "https://baidu.com";

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
     * 规则匹配并执行后回调方法
     * @param event 规则匹配并执行后回调事件
     */
    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        // 从 AfterMatchFiredEvent 中获取规则执行结果，即规则文件中定义的对象（fact）
        Object result = event.getMatch().getObjects().toArray()[0];
        // 将结果回调给调用方，例如通过消息队列、REST API等方式
        log.info("================MyAgendaEventListener=========== get drools execute result:{}",result);
        this.executeClientCallback(clientUrl);
    }

    /**
     * 执行客户端回调
     * @param url 客户端回调地址
     */
    private void executeClientCallback(String url){
        // 如果要携带参数，可以使用 HttpPost，此处使用 HttpGet 仅作演示
        HttpGet httpGet = new HttpGet(url);

        // 调用 HttpService 执行 HTTP 请求
        try {
            String response = HttpService.executeHttpRequest(httpGet);
            log.info("================MyAgendaEventListener=========== execute client callback response:{}",response);
        }catch (Exception e) {
            log.error("================MyAgendaEventListener=========== execute client callback error:{}", e.getMessage());
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
