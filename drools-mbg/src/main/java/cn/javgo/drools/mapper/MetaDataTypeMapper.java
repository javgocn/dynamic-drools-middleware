package cn.javgo.drools.mapper;

import cn.javgo.drools.model.MetaDataType;
import cn.javgo.drools.model.MetaDataTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetaDataTypeMapper {
    long countByExample(MetaDataTypeExample example);

    int deleteByExample(MetaDataTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetaDataType row);

    int insertSelective(MetaDataType row);

    List<MetaDataType> selectByExample(MetaDataTypeExample example);

    MetaDataType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MetaDataType row, @Param("example") MetaDataTypeExample example);

    int updateByExample(@Param("row") MetaDataType row, @Param("example") MetaDataTypeExample example);

    int updateByPrimaryKeySelective(MetaDataType row);

    int updateByPrimaryKey(MetaDataType row);
}