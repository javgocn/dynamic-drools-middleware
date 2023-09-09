package cn.javgo.drools.service;

import cn.javgo.drools.model.RuleAction;
import java.util.List;

/**
 * 规则动作 Service
 */
public interface RuleActionService {

    /**
     * 查询所有规则动作
     * @return List<RuleAction> 规则动作列表
     */
    List<RuleAction> listAll();

    /**
     * 根据规则ID批量查询规则动作
     *
     * @param ruleIds 规则ID列表
     * @return List<RuleAction> 规则动作列表
     */
    List<RuleAction> getRuleActionByRuleIds(List<Long> ruleIds);
}
