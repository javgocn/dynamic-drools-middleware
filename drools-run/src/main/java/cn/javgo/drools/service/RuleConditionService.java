package cn.javgo.drools.service;

import cn.javgo.drools.model.RuleCondition;

import java.util.List;

/**
 * 规则条件 Service
 */
public interface RuleConditionService {

    /**
     * 查询所有规则条件
     * @return List<RuleCondition> 规则条件列表
     */
    List<RuleCondition> listAll();

    /**
     * 分页查询规则条件
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return List<RuleCondition> 规则条件列表
     */
    List<RuleCondition> list(int pageNum, int pageSize);

    /**
     * 根据规则条件ID查询规则条件
     *
     * @param id 规则条件ID
     * @return RuleCondition 规则条件
     */
    RuleCondition getRuleConditionById(final Long id);
}
