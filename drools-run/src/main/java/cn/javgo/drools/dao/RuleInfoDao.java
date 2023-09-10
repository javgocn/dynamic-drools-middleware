package cn.javgo.drools.dao;

import cn.javgo.drools.dto.RuleAttributeRelationDto;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.RuleInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 规则信息 Dao
 */
public interface RuleInfoDao {

    /**
     * 根据规则ID查询规则属性
     * @param ruleId 规则ID
     * @return 规则属性列表
     */
    List<RuleAttributeRelationDto> getRuleAttributeByRuleId(@Param("ruleId") Long ruleId);

    /**
     * 根据场景查询规则信息
     *
     * @param busScene 场景信息
     * @return 规则信息列表
     */
    List<RuleInfo> getRuleInfoByScene(@Param("busScene") BusScene busScene);
}
