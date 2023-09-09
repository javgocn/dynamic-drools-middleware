package cn.javgo.drools.mapper;

import cn.javgo.drools.model.RuleDefinition;
import cn.javgo.drools.model.RuleDefinitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleDefinitionMapper {
    long countByExample(RuleDefinitionExample example);

    int deleteByExample(RuleDefinitionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RuleDefinition row);

    int insertSelective(RuleDefinition row);

    List<RuleDefinition> selectByExample(RuleDefinitionExample example);

    RuleDefinition selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RuleDefinition row, @Param("example") RuleDefinitionExample example);

    int updateByExample(@Param("row") RuleDefinition row, @Param("example") RuleDefinitionExample example);

    int updateByPrimaryKeySelective(RuleDefinition row);

    int updateByPrimaryKey(RuleDefinition row);
}