package cn.javgo.drools.service;

import cn.javgo.drools.model.BusScene;

import java.util.List;

/**
 * 业务场景 Service
 */
public interface BusSceneService {

    /**
     * 查询所有场景
     * @return List<BusScene> 场景列表
     */
    List<BusScene> listAll();

    /**
     * 根据ID查询场景
     *
     * @param id 场景ID
     * @return BusScene 场景
     */
    BusScene getSceneById(final Long id);

    /**
     * 根据名称查询场景
     *
     * @param Name 场景名称
     * @return BusScene 场景
     */
    BusScene getSceneByName(final String Name);
}
