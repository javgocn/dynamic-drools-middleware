package cn.javgo.drools.service;

import cn.javgo.drools.model.MetaEntity;
import java.util.List;

/**
 * 实体 Service
 */
public interface MetaEntityService {

    /**
     * 查询所有实体
     * @return List<BusScene> 实体列表
     */
    List<MetaEntity> listAll();

    /**
     * 根据id查询实体
     * @param id 实体id
     * @return MetaEntity 实体
     */
    MetaEntity getMetaEntityById(final Long id);

    /**
     * 根据id列表查询实体列表
     *
     * @param ids 实体id列表
     * @return List<MetaEntity> 实体列表
     */
    List<MetaEntity> getMetaEntityByIds(final List<Long> ids);
}
