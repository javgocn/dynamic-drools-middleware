package cn.javgo.drools.service;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.RuleAction;
import java.util.List;

/**
 * 规则动作 Service
 */
public interface RuleActionService {

    /**
     * 分页查询规则动作
     *
     * @param ruleAction 规则动作
     * @param pageNum    当前页数
     * @param pageSize   每页条数
     * @return 规则动作列表
     */
    List<RuleAction> listPage(RuleAction ruleAction, int pageNum, int pageSize);

    /**
     * 根据场景查询规则动作
     * @param busScene 场景信息
     * @return  规则动作列表
     */
    List<RuleAction> getRuleConditionByBusScene(BusScene busScene);

    /**
     * 根据规则id获取规则动作信息
     *
     * @param ruleId 规则id
     * @return 规则动作信息
     */
    List<RuleAction> getRuleActionByRuleId(final Long ruleId);

    /**
     * 创建规则动作
     *
     * @param ruleAction 规则动作
     * @return 创建结果
     */
    int create(RuleAction ruleAction);
}
