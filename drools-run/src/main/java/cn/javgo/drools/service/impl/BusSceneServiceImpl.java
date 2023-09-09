package cn.javgo.drools.service.impl;

import cn.javgo.drools.mapper.BusSceneMapper;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.BusSceneExample;
import cn.javgo.drools.service.BusSceneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务场景 Service 实现类
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
    public BusScene getSceneById(Long id) {
        return busSceneMapper.selectByPrimaryKey(id);
    }

    @Override
    public BusScene getSceneByName(String Name) {
        BusSceneExample example = new BusSceneExample();
        example.createCriteria().andNameEqualTo(Name);
        List<BusScene> busScenes = busSceneMapper.selectByExample(example);
        if (busScenes.size() > 0) {
            return busScenes.get(0);
        }
        return null;
    }
}
