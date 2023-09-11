package cn.javgo.drools.service.impl;

import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import cn.javgo.drools.mapper.RuleConditionMapper;
import cn.javgo.drools.model.RuleCondition;
import cn.javgo.drools.model.RuleConditionExample;
import cn.javgo.drools.service.RuleConditionService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则条件 Service 实现
 */
@Service
public class RuleConditionServiceImpl implements RuleConditionService {

    @Resource
    private RuleConditionMapper ruleConditionMapper;

    @Override
    public List<RuleCondition> listPage(RuleCondition ruleCondition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        RuleConditionExample example = new RuleConditionExample();
        RuleConditionExample.Criteria criteria = example.createCriteria();
        // 如果规则ID不为空，查询指定规则ID的规则条件
        if (ruleCondition.getRuleId() != null) {
            criteria.andRuleIdEqualTo(ruleCondition.getRuleId());
        }

        return ruleConditionMapper.selectByExample(example);
    }

    @Override
    public List<RuleCondition> getRuleConditionById(Long ruleId) {
        // 如果规则ID为 null，抛出参数缺失异常
        if (ruleId == null) {
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }

        RuleConditionExample example = new RuleConditionExample();
        RuleConditionExample.Criteria criteria = example.createCriteria();
        criteria.andRuleIdEqualTo(ruleId);

        return ruleConditionMapper.selectByExample(example);
    }

    @Override
    public List<RuleCondition> getRuleConditionByParentId(Long parentId) {
        RuleConditionExample example = new RuleConditionExample();
        RuleConditionExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        return ruleConditionMapper.selectByExample(example);
    }

    @Override
    public int create(RuleCondition ruleCondition) {
        return ruleConditionMapper.insertSelective(ruleCondition);
    }
}
