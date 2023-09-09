package cn.javgo.drools.mapper;

import cn.javgo.drools.model.RuleCondition;
import cn.javgo.drools.model.RuleConditionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleConditionMapper {
    long countByExample(RuleConditionExample example);

    int deleteByExample(RuleConditionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RuleCondition row);

    int insertSelective(RuleCondition row);

    List<RuleCondition> selectByExample(RuleConditionExample example);

    RuleCondition selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RuleCondition row, @Param("example") RuleConditionExample example);

    int updateByExample(@Param("row") RuleCondition row, @Param("example") RuleConditionExample example);

    int updateByPrimaryKeySelective(RuleCondition row);

    int updateByPrimaryKey(RuleCondition row);
}