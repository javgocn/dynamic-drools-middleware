package cn.javgo.drools.service.impl;

import cn.hutool.db.Page;
import cn.javgo.drools.dao.RuleActionDao;
import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import cn.javgo.drools.mapper.RuleActionMapper;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.RuleAction;
import cn.javgo.drools.model.RuleActionExample;
import cn.javgo.drools.service.RuleActionService;
import cn.javgo.drools.util.StringUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 规则动作 Service 实现
 */
@Service
public class RuleActionServiceImpl implements RuleActionService {

    @Resource
    private RuleActionMapper ruleActionMapper;

    @Resource
    private RuleActionDao ruleActionDao;

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
    public List<RuleAction> getRuleActionByBusScene(BusScene busScene) {
        // 如果业务场景为 null 或 业务场景 ID 和场景标识为空，抛出参数缺失异常
        if (busScene == null || (busScene.getId() == null && StringUtil.isNullOrNullValue(busScene.getIdentify()))){
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }
        return ruleActionDao.getRuleActionByBusScene(busScene);
    }

    @Override
    public List<RuleAction> getRuleActionByRuleId(Long ruleId) {
        // 如果规则 ID 为空，抛出参数缺失异常
        if (ruleId == null) {
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }
        return ruleActionDao.getRuleActionByRuleId(ruleId);
    }

    @Override
    public int create(RuleAction ruleAction) {
        return ruleActionMapper.insertSelective(ruleAction);
    }
}
