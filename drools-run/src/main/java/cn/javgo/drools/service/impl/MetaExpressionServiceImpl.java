package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.MetaExpressionMapper;
import cn.javgo.drools.model.MetaExpression;
import cn.javgo.drools.service.MetaExpressionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表达式 Service 实现类
 */
public class MetaExpressionServiceImpl implements MetaExpressionService {

    @Resource
    private MetaExpressionMapper metaExpressionMapper;

    @Override
    public List<MetaExpression> listAll() {
        return metaExpressionMapper.selectByExample(null);
    }
}
