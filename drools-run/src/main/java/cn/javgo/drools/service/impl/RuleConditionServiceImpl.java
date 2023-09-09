package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.RuleConditionMapper;
import cn.javgo.drools.model.RuleCondition;
import cn.javgo.drools.service.RuleConditionService;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则条件 Service 实现类
 */
public class RuleConditionServiceImpl implements RuleConditionService {

    @Resource
    private RuleConditionMapper ruleConditionMapper;

    @Override
    public List<RuleCondition> listAll() {
        return ruleConditionMapper.selectByExample(null);
    }

    @Override
    public List<RuleCondition> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ruleConditionMapper.selectByExample(null);
    }

    @Override
    public RuleCondition getRuleConditionById(Long id) {
        return ruleConditionMapper.selectByPrimaryKey(id);
    }
}
