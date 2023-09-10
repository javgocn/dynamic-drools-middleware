package cn.javgo.drools.service;

import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.model.SceneEntityRelation;

import java.util.List;

/**
 * 场景实体关系 Service
 */
public interface SceneEntityRelationService {

    /**
     * 分页查询场景实体关系
     *
     * @param sceneEntityRelation 场景实体关系
     * @param pageNum    当前页数
     * @param pageSize   每页条数
     * @return 场景实体关系列表
     */
    List<SceneEntityRelation> listPage(SceneEntityRelation sceneEntityRelation, int pageNum, int pageSize);

    /**
     * 根据场景获取场景实体
     *
     * @param busScene 场景
     * @return 场景实体列表
     */
    List<MetaEntity> getMetaEntityByScene(BusScene busScene);

    /**
     * 创建场景实体关系
     *
     * @param sceneEntityRelation 场景实体关系
     * @return 创建结果
     */
    int create(SceneEntityRelation sceneEntityRelation);
}
