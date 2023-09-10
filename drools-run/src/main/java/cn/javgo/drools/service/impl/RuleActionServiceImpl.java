package cn.javgo.drools.service.impl;

import cn.hutool.db.Page;
import cn.javgo.drools.mapper.RuleActionMapper;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.RuleAction;
import cn.javgo.drools.model.RuleActionExample;
import cn.javgo.drools.service.RuleActionService;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则动作 Service 实现
 */
public class RuleActionServiceImpl implements RuleActionService {

    @Resource
    private RuleActionMapper ruleActionMapper;

    @Override
    public List<RuleAction> listPage(RuleAction ruleAction, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        RuleActionExample example = new RuleActionExample();
        RuleActionExample.Criteria criteria = example.createCriteria();
        // 如果规则 id 不为空，则根据规则 id 查询
        if (ruleAction.getRuleId() != null) {
            criteria.andRuleIdEqualTo(ruleAction.getRuleId());
        }

        return ruleActionMapper.selectByExample(example);
    }

    @Override
    public List<RuleAction> getRuleConditionByBusScene(BusScene busScene) {
        return null;
    }

    @Override
    public List<RuleAction> getRuleActionByRuleId(Long ruleId) {
        return null;
    }

    @Override
    public int create(RuleAction ruleAction) {
        return ruleActionMapper.insertSelective(ruleAction);
    }
}
