package cn.javgo.drools.service;

import java.util.List;

/**
 * 规则属性关系 Service
 */
public interface RuleAttributeRelationService {

    /**
     * 根据规则ID查询属性ID列表
     *
     * @param ruleId 规则ID
     * @return List<Long> 属性ID列表
     */
    List<Long> getAttributeIdByRuleId(final Long ruleId);
}
