package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.BusSceneMapper;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.service.BusSceneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务场景 Service 实现
 */
@Service
public class BusSceneServiceImpl implements BusSceneService {

    @Resource
    private BusSceneMapper busSceneMapper;


    @Override
    public List<BusScene> listAll() {
        return busSceneMapper.selectByExample(null);
    }

    @Override
    public int create(BusScene busScene) {
        return busSceneMapper.insertSelective(busScene);
    }
}
