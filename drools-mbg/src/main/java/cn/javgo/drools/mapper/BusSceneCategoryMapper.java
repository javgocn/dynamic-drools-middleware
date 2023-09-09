package cn.javgo.drools.mapper;

import cn.javgo.drools.model.BusSceneCategory;
import cn.javgo.drools.model.BusSceneCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusSceneCategoryMapper {
    long countByExample(BusSceneCategoryExample example);

    int deleteByExample(BusSceneCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BusSceneCategory row);

    int insertSelective(BusSceneCategory row);

    List<BusSceneCategory> selectByExample(BusSceneCategoryExample example);

    BusSceneCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") BusSceneCategory row, @Param("example") BusSceneCategoryExample example);

    int updateByExample(@Param("row") BusSceneCategory row, @Param("example") BusSceneCategoryExample example);

    int updateByPrimaryKeySelective(BusSceneCategory row);

    int updateByPrimaryKey(BusSceneCategory row);
}