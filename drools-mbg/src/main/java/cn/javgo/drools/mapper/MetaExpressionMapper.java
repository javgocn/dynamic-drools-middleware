package cn.javgo.drools.mapper;

import cn.javgo.drools.model.MetaExpression;
import cn.javgo.drools.model.MetaExpressionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetaExpressionMapper {
    long countByExample(MetaExpressionExample example);

    int deleteByExample(MetaExpressionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetaExpression row);

    int insertSelective(MetaExpression row);

    List<MetaExpression> selectByExample(MetaExpressionExample example);

    MetaExpression selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MetaExpression row, @Param("example") MetaExpressionExample example);

    int updateByExample(@Param("row") MetaExpression row, @Param("example") MetaExpressionExample example);

    int updateByPrimaryKeySelective(MetaExpression row);

    int updateByPrimaryKey(MetaExpression row);
}