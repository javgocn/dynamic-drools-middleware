package cn.javgo.drools.service;

import cn.javgo.drools.model.MetaExpression;

import java.util.List;

/**
 * 表达式 Service
 */
public interface MetaExpressionService {

    /**
     * 查询所有表达式
     * @return List<MetaExpression> 表达式列表
     */
    List<MetaExpression> listAll();
}
