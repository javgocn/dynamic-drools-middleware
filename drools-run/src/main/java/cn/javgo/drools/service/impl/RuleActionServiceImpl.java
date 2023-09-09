package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.RuleActionMapper;
import cn.javgo.drools.model.RuleAction;
import cn.javgo.drools.model.RuleActionExample;
import cn.javgo.drools.service.RuleActionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则动作 Service 实现类
 */
public class RuleActionServiceImpl implements RuleActionService {

    @Resource
    private RuleActionMapper ruleActionMapper;

    @Override
    public List<RuleAction> listAll() {
        return ruleActionMapper.selectByExample(null);
    }

    @Override
    public List<RuleAction> getRuleActionByRuleIds(List<Long> ruleIds) {
        RuleActionExample example = new RuleActionExample();
        RuleActionExample.Criteria criteria = example.createCriteria();
        criteria.andRuleIdIn(ruleIds);

        return ruleActionMapper.selectByExample(example);
    }
}
