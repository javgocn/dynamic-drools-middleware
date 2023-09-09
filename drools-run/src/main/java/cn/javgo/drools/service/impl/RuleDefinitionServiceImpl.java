package cn.javgo.drools.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.javgo.drools.mapper.RuleDefinitionMapper;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaRuleAttribute;
import cn.javgo.drools.model.RuleDefinition;
import cn.javgo.drools.model.RuleDefinitionExample;
import cn.javgo.drools.service.MetaRuleAttributeService;
import cn.javgo.drools.service.RuleAttributeRelationService;
import cn.javgo.drools.service.RuleDefinitionService;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 规则定义 Service 实现类
 */
public class RuleDefinitionServiceImpl implements RuleDefinitionService {

    @Resource
    private RuleDefinitionMapper ruleDefinitionMapper;

    @Resource
    private RuleAttributeRelationService ruleAttributeRelationService;

    @Resource
    private MetaRuleAttributeService metaRuleAttributeService;

    @Override
    public List<RuleDefinition> listAll() {
        return ruleDefinitionMapper.selectByExample(null);
    }

    @Override
    public List<RuleDefinition> list(String ruleName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        RuleDefinitionExample example = new RuleDefinitionExample();
        RuleDefinitionExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(ruleName)){
            criteria.andNameLike("%" + ruleName + "%");
        }

        return ruleDefinitionMapper.selectByExample(example);
    }

    @Override
    public RuleDefinition getRuleDefinitionById(Long id) {
        return ruleDefinitionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MetaRuleAttribute> getRuleAttributeByRuleId(Long id) {
        // 获取规则属性ID列表
        List<Long> attributeIdList = ruleAttributeRelationService.getAttributeIdByRuleId(id);

        // 根据规则属性ID列表获取规则属性列表
        return metaRuleAttributeService.listMetaRuleAttributeByIds(attributeIdList);
    }

    @Override
    public List<Long> getRuleDefinitionIdByBusScene(BusScene busScene) {
        RuleDefinitionExample example = new RuleDefinitionExample();
        RuleDefinitionExample.Criteria criteria = example.createCriteria();
        criteria.andScenIdEqualTo(busScene.getId());

        List<RuleDefinition> ruleDefinitionList = ruleDefinitionMapper.selectByExample(example);
        List<Long> ids = ruleDefinitionList.stream()
                .map(RuleDefinition::getId)
                .collect(Collectors.toList());

        return ids;
    }

    @Override
    public List<RuleDefinition> getRuleDefinitionByBusSceneId(Long busSceneId) {
        RuleDefinitionExample example = new RuleDefinitionExample();
        RuleDefinitionExample.Criteria criteria = example.createCriteria();
        criteria.andScenIdEqualTo(busSceneId);

        return ruleDefinitionMapper.selectByExample(example);
    }
}
