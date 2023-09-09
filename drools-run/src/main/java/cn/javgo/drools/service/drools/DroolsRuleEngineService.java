package cn.javgo.drools.service.drools;

import cn.javgo.drools.dto.fact.RuleExecutionObject;

/**
 * 规则引擎Service
 */
public interface DroolsRuleEngineService {

    /**
     * 执行规则引擎
     *
     * @param ruleExecutionObject 规则执行对象
     * @param scene 业务场景
     * @return 规则执行对象
     */
    RuleExecutionObject execute(RuleExecutionObject ruleExecutionObject, final String scene);
}
