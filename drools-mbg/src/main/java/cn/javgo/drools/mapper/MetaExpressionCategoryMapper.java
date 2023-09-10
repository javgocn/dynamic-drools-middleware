package cn.javgo.drools.mapper;

import cn.javgo.drools.model.MetaExpressionCategory;
import cn.javgo.drools.model.MetaExpressionCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetaExpressionCategoryMapper {
    long countByExample(MetaExpressionCategoryExample example);

    int deleteByExample(MetaExpressionCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetaExpressionCategory row);

    int insertSelective(MetaExpressionCategory row);

    List<MetaExpressionCategory> selectByExample(MetaExpressionCategoryExample example);

    MetaExpressionCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MetaExpressionCategory row, @Param("example") MetaExpressionCategoryExample example);

    int updateByExample(@Param("row") MetaExpressionCategory row, @Param("example") MetaExpressionCategoryExample example);

    int updateByPrimaryKeySelective(MetaExpressionCategory row);

    int updateByPrimaryKey(MetaExpressionCategory row);
}