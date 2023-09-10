package cn.javgo.drools.mapper;

import cn.javgo.drools.model.RuleAttribute;
import cn.javgo.drools.model.RuleAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleAttributeMapper {
    long countByExample(RuleAttributeExample example);

    int deleteByExample(RuleAttributeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RuleAttribute row);

    int insertSelective(RuleAttribute row);

    List<RuleAttribute> selectByExample(RuleAttributeExample example);

    RuleAttribute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RuleAttribute row, @Param("example") RuleAttributeExample example);

    int updateByExample(@Param("row") RuleAttribute row, @Param("example") RuleAttributeExample example);

    int updateByPrimaryKeySelective(RuleAttribute row);

    int updateByPrimaryKey(RuleAttribute row);
}