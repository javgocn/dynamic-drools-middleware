package cn.javgo.drools.mapper;

import cn.javgo.drools.model.RuleAttributeRelation;
import cn.javgo.drools.model.RuleAttributeRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RuleAttributeRelationMapper {
    long countByExample(RuleAttributeRelationExample example);

    int deleteByExample(RuleAttributeRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RuleAttributeRelation row);

    int insertSelective(RuleAttributeRelation row);

    List<RuleAttributeRelation> selectByExample(RuleAttributeRelationExample example);

    RuleAttributeRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") RuleAttributeRelation row, @Param("example") RuleAttributeRelationExample example);

    int updateByExample(@Param("row") RuleAttributeRelation row, @Param("example") RuleAttributeRelationExample example);

    int updateByPrimaryKeySelective(RuleAttributeRelation row);

    int updateByPrimaryKey(RuleAttributeRelation row);
}