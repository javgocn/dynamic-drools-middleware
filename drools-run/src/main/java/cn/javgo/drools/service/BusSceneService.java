package cn.javgo.drools.service;

import cn.javgo.drools.model.BusScene;
import java.util.List;

/**
 * 业务场景 Service
 */
public interface BusSceneService {

    /**
     * 查询所有业务场景
     * @return 业务场景集合
     */
    List<BusScene> listAll();

    /**
     * 创建业务场景
     *
     * @param busScene 业务场景
     * @return 创建结果
     */
    int create(BusScene busScene);
}
