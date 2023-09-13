package cn.javgo.drools.service.impl;

import cn.javgo.drools.dao.RuleInfoDao;
import cn.javgo.drools.dto.RuleAttributeRelationDto;
import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import cn.javgo.drools.mapper.RuleInfoMapper;
import cn.javgo.drools.model.*;
import cn.javgo.drools.service.RuleInfoService;
import cn.javgo.drools.util.StringUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 规则信息 Service 实现
 */
@Service
public class RuleInfoServiceImpl implements RuleInfoService {

    @Resource
    private RuleInfoMapper ruleInfoMapper;

    @Resource
    private RuleInfoDao ruleInfoDao;

    @Override
    public List<RuleInfo> listAll(RuleInfo ruleInfo) {
        // 创建查询条件
        RuleInfoExample example = new RuleInfoExample();
        RuleInfoExample.Criteria criteria = example.createCriteria();
        // 添加场景ID查询条件
        if (ruleInfo.getSceneId() != null) {
            criteria.andSceneIdEqualTo(ruleInfo.getSceneId());
        }
        // 添加是否启用查询条件
        if (ruleInfo.getIsEffect() != null) {
            criteria.andIsEffectEqualTo(ruleInfo.getIsEffect());
        }

        return ruleInfoMapper.selectByExample(example);
    }

    @Override
    public List<RuleInfo> listPage(RuleInfo ruleInfo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        // 创建查询条件
        RuleInfoExample example = new RuleInfoExample();
        RuleInfoExample.Criteria criteria = example.createCriteria();
        // 添加场景ID查询条件
        if (ruleInfo.getSceneId() != null) {
            criteria.andSceneIdEqualTo(ruleInfo.getSceneId());
        }
        // 添加是否启用查询条件
        if (ruleInfo.getIsEffect() != null) {
            criteria.andIsEffectEqualTo(ruleInfo.getIsEffect());
        }

        return ruleInfoMapper.selectByExample(example);
    }

    @Override
    public List<RuleAttributeRelationDto> getRuleAttributeByRuleId(Long ruleId) {
        return ruleInfoDao.getRuleAttributeByRuleId(ruleId);
    }

    @Override
    public List<RuleInfo> getRuleInfoByScene(BusScene busScene) {
        // 如果场景信息为 null 或者 场景标识和场景ID都为空，则抛出参数缺失异常
        if(busScene == null || (busScene.getId() == null && StringUtil.isNullOrNullValue(busScene.getIdentify()))){
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }
        return ruleInfoDao.getRuleInfoByScene(busScene);
    }

    @Override
    public int create(RuleInfo ruleInfo) {
        return ruleInfoMapper.insertSelective(ruleInfo);
    }

}
