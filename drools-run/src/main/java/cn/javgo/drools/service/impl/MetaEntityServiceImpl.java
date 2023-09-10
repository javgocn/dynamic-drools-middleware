package cn.javgo.drools.service.impl;

import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import cn.javgo.drools.mapper.MetaEntityMapper;
import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.service.MetaEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 元数据实体 Service 实现
 */
@Service
public class MetaEntityServiceImpl implements MetaEntityService {

    @Resource
    private MetaEntityMapper metaEntityMapper;

    @Override
    public List<MetaEntity> listAll() {
        return metaEntityMapper.selectByExample(null);
    }

    @Override
    public MetaEntity getMetaEntityById(Long entityId) {
        // 如果实体ID为空，抛出参数缺失异常
        if (entityId == null) {
            throw new ServiceException(ExceptionEnum.SYS_REQUEST_PARAM_MISSING);
        }
        return metaEntityMapper.selectByPrimaryKey(entityId);
    }

    @Override
    public int create(MetaEntity metaEntity) {
        return metaEntityMapper.insertSelective(metaEntity);
    }
}
