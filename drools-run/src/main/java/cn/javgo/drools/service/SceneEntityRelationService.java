package cn.javgo.drools.service;

import java.util.List;

/**
 * 场景实体关系 Service
 */
public interface SceneEntityRelationService {

    /**
     * 根据场景ID获取实体ID列表
     *
     * @param sceneId 场景ID
     * @return List<Long> 实体ID列表
     */
    List<Long> getEntityIdsBySceneId(Long sceneId);
}
