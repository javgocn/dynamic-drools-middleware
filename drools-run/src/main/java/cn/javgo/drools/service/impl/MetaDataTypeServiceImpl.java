package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.MetaDataTypeMapper;
import cn.javgo.drools.model.MetaDataType;
import cn.javgo.drools.service.MetaDataTypeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据类型 Service 实现
 */
public class MetaDataTypeServiceImpl implements MetaDataTypeService {

    @Resource
    private MetaDataTypeMapper metaDataTypeMapper;

    @Override
    public List<MetaDataType> listAll() {
        return metaDataTypeMapper.selectByExample(null);
    }
}
