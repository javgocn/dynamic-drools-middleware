package cn.javgo.drools.service;

import cn.javgo.drools.model.MetaRuleAttribute;
import java.util.List;

/**
 * 规则属性 Service
 */
public interface MetaRuleAttributeService {

    /**
     * 查询所有规则属性
     * @return List<MetaRuleAttribute> 规则属性列表
     */
    List<MetaRuleAttribute> listAll();

    /**
     * 根据规则属性ID集合查询规则属性列表
     *
     * @param ids 规则属性ID集合
     * @return List<MetaRuleAttribute> 规则属性列表
     */
    List<MetaRuleAttribute> listMetaRuleAttributeByIds(List<Long> ids);
}
