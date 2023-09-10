package cn.javgo.drools.mapper;

import cn.javgo.drools.model.DatatypeExpressionRelation;
import cn.javgo.drools.model.DatatypeExpressionRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatatypeExpressionRelationMapper {
    long countByExample(DatatypeExpressionRelationExample example);

    int deleteByExample(DatatypeExpressionRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DatatypeExpressionRelation row);

    int insertSelective(DatatypeExpressionRelation row);

    List<DatatypeExpressionRelation> selectByExample(DatatypeExpressionRelationExample example);

    DatatypeExpressionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") DatatypeExpressionRelation row, @Param("example") DatatypeExpressionRelationExample example);

    int updateByExample(@Param("row") DatatypeExpressionRelation row, @Param("example") DatatypeExpressionRelationExample example);

    int updateByPrimaryKeySelective(DatatypeExpressionRelation row);

    int updateByPrimaryKey(DatatypeExpressionRelation row);
}