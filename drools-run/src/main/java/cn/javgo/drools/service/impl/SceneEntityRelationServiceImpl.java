package cn.javgo.drools.service.impl;

import cn.javgo.drools.dao.SceneEntityRelationDao;
import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import cn.javgo.drools.mapper.SceneEntityRelationMapper;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.model.SceneEntityRelation;
import cn.javgo.drools.model.SceneEntityRelationExample;
import cn.javgo.drools.service.SceneEntityRelationService;
import cn.javgo.drools.util.StringUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 场景实体关系 Service 实现
 */
@Service
public class SceneEntityRelationServiceImpl implements SceneEntityRelationService {

    @Resource
    private SceneEntityRelationMapper sceneEntityRelationMapper;

    @Resource
    private SceneEntityRelationDao sceneEntityRelationDao;

    @Override
    public List<SceneEntityRelation> listPage(SceneEntityRelation sceneEntityRelation, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SceneEntityRelationExample example = new SceneEntityRelationExample();
        SceneEntityRelationExample.Criteria criteria = example.createCriteria();
        // 如果场景ID不为空，则根据场景ID查询对应的实体
        if (sceneEntityRelation.getSceneId() != null) {
            criteria.andSceneIdEqualTo(sceneEntityRelation.getSceneId());
        }

        return sceneEntityRelationMapper.selectByExample(example);
    }

    @Override
    public List<MetaEntity> getMetaEntityByScene(BusScene busScene) {
        // 如果场景对象为 null 或者 场景标识和场景ID都为空，则抛出参数缺失异常
        if (busScene == null || (StringUtil.isNullOrNullValue(busScene.getIdentify()) && busScene.getId() == null)) {
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }

        return sceneEntityRelationDao.getMetaEntityByScene(busScene);
    }

    @Override
    public int create(SceneEntityRelation sceneEntityRelation) {
        return sceneEntityRelationMapper.insertSelective(sceneEntityRelation);
    }
}
