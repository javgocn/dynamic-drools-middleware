package cn.javgo.drools.mapper;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.BusSceneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BusSceneMapper {
    long countByExample(BusSceneExample example);

    int deleteByExample(BusSceneExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BusScene row);

    int insertSelective(BusScene row);

    List<BusScene> selectByExample(BusSceneExample example);

    BusScene selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") BusScene row, @Param("example") BusSceneExample example);

    int updateByExample(@Param("row") BusScene row, @Param("example") BusSceneExample example);

    int updateByPrimaryKeySelective(BusScene row);

    int updateByPrimaryKey(BusScene row);
}