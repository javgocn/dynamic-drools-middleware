package cn.javgo.drools.mapper;

import cn.javgo.drools.model.RuleAction;
import cn.javgo.drools.model.RuleActionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleActionMapper {
    long countByExample(RuleActionExample example);

    int deleteByExample(RuleActionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RuleAction row);

    int insertSelective(RuleAction row);

    List<RuleAction> selectByExample(RuleActionExample example);

    RuleAction selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RuleAction row, @Param("example") RuleActionExample example);

    int updateByExample(@Param("row") RuleAction row, @Param("example") RuleActionExample example);

    int updateByPrimaryKeySelective(RuleAction row);

    int updateByPrimaryKey(RuleAction row);
}