package cn.javgo.drools.mapper;

import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.model.MetaEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetaEntityMapper {
    long countByExample(MetaEntityExample example);

    int deleteByExample(MetaEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetaEntity row);

    int insertSelective(MetaEntity row);

    List<MetaEntity> selectByExample(MetaEntityExample example);

    MetaEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") MetaEntity row, @Param("example") MetaEntityExample example);

    int updateByExample(@Param("row") MetaEntity row, @Param("example") MetaEntityExample example);

    int updateByPrimaryKeySelective(MetaEntity row);

    int updateByPrimaryKey(MetaEntity row);
}