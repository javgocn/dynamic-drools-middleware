package cn.javgo.drools.dao;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.RuleAction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 规则动作 Dao
 */
public interface RuleActionDao {

    /**
     * 根据场景查询规则动作
     * @param busScene 场景信息
     * @return  规则动作列表
     */
    List<RuleAction> getRuleActionByBusScene(@Param("busScene") BusScene busScene);

    /**
     * 根据规则id获取规则动作信息
     *
     * @param ruleId 规则id
     * @return 规则动作信息
     */
    List<RuleAction> getRuleActionByRuleId(@Param("ruleId") Long ruleId);
}
