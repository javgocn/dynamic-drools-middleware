package cn.javgo.drools.service;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaRuleAttribute;
import cn.javgo.drools.model.RuleDefinition;
import java.util.List;

/**
 * 规则定义 Service
 */
public interface RuleDefinitionService {

    /**
     * 查询所有规则定义
     * @return List<RuleDefinition> 规则定义列表
     */
    List<RuleDefinition> listAll();

    /**
     * 分页查询规则定义
     *
     * @param ruleName 规则名称（模糊匹配）
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return List<RuleDefinition> 规则定义列表
     */
    List<RuleDefinition> list(String ruleName, int pageNum, int pageSize);

    /**
     * 根据ID查询规则定义
     *
     * @param id 规则定义ID
     * @return RuleDefinition 规则定义
     */
    RuleDefinition getRuleDefinitionById(final Long id);

    /**
     * 根据ID查询规则属性
     * @param id 规则定义ID
     * @return List<MetaRuleAttribute> 规则属性列表
     */
    List<MetaRuleAttribute> getRuleAttributeByRuleId(final Long id);

    /**
     * 根据业务场景获取规则定义ID
     *
     * @param busScene 业务场景
     * @return Long 规则定义ID
     */
    List<Long> getRuleDefinitionIdByBusScene(final BusScene busScene);

    /**
     * 根据业务场景ID获取规则定义
     *
     * @param busSceneId 业务场景ID
     * @return List<RuleDefinition> 规则定义列表
     */
    List<RuleDefinition> getRuleDefinitionByBusSceneId(final Long busSceneId);
}
