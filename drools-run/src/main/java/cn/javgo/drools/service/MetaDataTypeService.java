package cn.javgo.drools.service;

import cn.javgo.drools.model.MetaDataType;

import java.util.List;

/**
 * 数据类型 Service
 */
public interface MetaDataTypeService {

    /**
     * 查询所有数据类型
     * @return List<MetaDataType> 数据类型列表
     */
    List<MetaDataType> listAll();
}
