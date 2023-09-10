package cn.javgo.drools.dao;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 场景实体关系 Dao
 */
public interface SceneEntityRelationDao {

    /**
     * 根据场景获取元数据实体
     *
     * @param busScene 场景
     * @return 元数据实体集合
     */
    List<MetaEntity> getMetaEntityByScene(@Param("busScene") BusScene busScene);
}
