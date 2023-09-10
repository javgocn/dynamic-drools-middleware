package cn.javgo.drools.service;

import cn.javgo.drools.model.MetaEntity;

import java.util.List;

/**
 * 元数据实体 Service
 */
public interface MetaEntityService {

    /**
     * 查询所有元数据实体
     * @return 元数据实体集合
     */
    List<MetaEntity> listAll();

    /**
     * 根据ID查询元数据实体
     * @param entityId 元数据实体ID
     * @return 元数据实体
     */
    MetaEntity getMetaEntityById(final Long entityId);

    /**
     * 创建元数据实体
     *
     * @param metaEntity 元数据实体
     * @return 创建结果
     */
    int create(MetaEntity metaEntity);
}
