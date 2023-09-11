package cn.javgo.drools.service;

import cn.javgo.drools.dto.RuleAttributeRelationDto;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.RuleInfo;
import java.util.List;

/**
 * 规则信息 Service
 */
public interface RuleInfoService {

    /**
     * 获取所有规则信息
     * @return 规则信息列表
     */
    List<RuleInfo> listAll(RuleInfo ruleInfo);

    /**
     * 分页查询规则信息
     *
     * @param ruleInfo 查询参数
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @return 规则信息列表
     */
    List<RuleInfo> listPage(RuleInfo ruleInfo, Integer pageNum, Integer pageSize);

    /**
     * 根据规则ID查询规则属性
     * @param ruleId 规则ID
     * @return 规则属性列表
     */
    List<RuleAttributeRelationDto> getRuleAttributeByRuleId(final Long ruleId);

    /**
     * 根据场景查询规则信息
     *
     * @param busScene 场景信息
     * @return 规则信息列表
     */
    List<RuleInfo> getRuleInfoByScene(BusScene busScene);

    /**
     * 创建规则信息
     *
     * @param ruleInfo 规则信息
     * @return 创建结果
     */
    int create(RuleInfo ruleInfo);

}
