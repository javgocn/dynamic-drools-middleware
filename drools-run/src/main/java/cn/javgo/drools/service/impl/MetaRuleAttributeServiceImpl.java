package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.MetaRuleAttributeMapper;
import cn.javgo.drools.model.MetaRuleAttribute;
import cn.javgo.drools.model.MetaRuleAttributeExample;
import cn.javgo.drools.service.MetaRuleAttributeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则属性 Service 实现类
 */
public class MetaRuleAttributeServiceImpl implements MetaRuleAttributeService {

    @Resource
    private MetaRuleAttributeMapper metaRuleAttributeMapper;

    @Override
    public List<MetaRuleAttribute> listAll() {
        return metaRuleAttributeMapper.selectByExample(null);
    }

    @Override
    public List<MetaRuleAttribute> listMetaRuleAttributeByIds(List<Long> ids) {
        MetaRuleAttributeExample example = new MetaRuleAttributeExample();
        MetaRuleAttributeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        return metaRuleAttributeMapper.selectByExample(example);
    }
}
