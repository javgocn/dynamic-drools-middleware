package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.SceneEntityRelationMapper;
import cn.javgo.drools.model.SceneEntityRelation;
import cn.javgo.drools.model.SceneEntityRelationExample;
import cn.javgo.drools.service.SceneEntityRelationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 场景实体关系 Service 实现
 */
public class SceneEntityRelationServiceImpl implements SceneEntityRelationService {

    @Resource
    private SceneEntityRelationMapper sceneEntityRelationMapper;

    @Override
    public List<Long> getEntityIdsBySceneId(Long sceneId) {
        SceneEntityRelationExample example = new SceneEntityRelationExample();
        example.createCriteria().andSceneIdEqualTo(sceneId);
        List<SceneEntityRelation> sceneEntityRelations = sceneEntityRelationMapper.selectByExample(example);

        return sceneEntityRelations.stream()
                .map(SceneEntityRelation::getEntityId)
                .collect(Collectors.toList());
    }
}
