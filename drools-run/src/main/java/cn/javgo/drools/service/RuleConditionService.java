package cn.javgo.drools.service;

import cn.javgo.drools.model.RuleCondition;

import java.util.List;

/**
 * 规则条件 Service
 */
public interface RuleConditionService {

    /**
     * 分页查询规则条件
     *
     * @param ruleCondition 规则条件
     * @param pageNum    当前页数
     * @param pageSize   每页条数
     * @return 规则条件列表
     */
    List<RuleCondition> listPage(RuleCondition ruleCondition,int pageNum,int pageSize);

    /**
     * 根据规则id获取规则条件信息
     *
     * @param ruleId 规则id
     * @return 规则条件信息列表
     */
    List<RuleCondition> getRuleConditionById(final Long ruleId);

    /**
     * 根据父id获取子条件集合
     *
     * @param parentId 父id
     * @return 子条件集合
     */
    List<RuleCondition> getRuleConditionByParentId(final Long parentId);

    /**
     * 创建规则条件
     *
     * @param ruleCondition 规则条件
     * @return 创建结果
     */
    int create(RuleCondition ruleCondition);
}
