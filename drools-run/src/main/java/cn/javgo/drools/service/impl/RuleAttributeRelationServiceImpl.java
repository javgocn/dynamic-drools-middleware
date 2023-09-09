package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.RuleAttributeRelationMapper;
import cn.javgo.drools.model.RuleAttributeRelation;
import cn.javgo.drools.model.RuleAttributeRelationExample;
import cn.javgo.drools.service.RuleAttributeRelationService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则属性关系 Service 实现类
 */
public class RuleAttributeRelationServiceImpl implements RuleAttributeRelationService {

    @Resource
    private RuleAttributeRelationMapper ruleAttributeRelationMapper;

    @Override
    public List<Long> getAttributeIdByRuleId(Long ruleId) {
        RuleAttributeRelationExample example = new RuleAttributeRelationExample();
        RuleAttributeRelationExample.Criteria criteria = example.createCriteria();
        criteria.andRuleIdEqualTo(ruleId);

        List<RuleAttributeRelation> ruleAttributeRelationList = ruleAttributeRelationMapper.selectByExample(example);

        return ruleAttributeRelationList.stream()
                .map(RuleAttributeRelation::getRuleAttributeId)
                .collect(java.util.stream.Collectors.toList());
    }
}
