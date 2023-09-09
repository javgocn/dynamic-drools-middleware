package cn.javgo.drools.service.impl;

import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import cn.javgo.drools.mapper.MetaEntityMapper;
import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.model.MetaEntityExample;
import cn.javgo.drools.service.MetaEntityService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实体 Service 实现类
 */
public class MetaEntityServiceImpl implements MetaEntityService {

    @Resource
    private MetaEntityMapper metaEntityMapper;

    @Override
    public List<MetaEntity> listAll() {
        return metaEntityMapper.selectByExample(null);
    }

    @Override
    public MetaEntity getMetaEntityById(Long id) {
        // 如果id为空，抛出异常
        if (id == null){
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }
        return metaEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MetaEntity> getMetaEntityByIds(List<Long> ids) {
        MetaEntityExample example = new MetaEntityExample();
        example.createCriteria().andIdIn(ids);
        return metaEntityMapper.selectByExample(example);
    }
}
