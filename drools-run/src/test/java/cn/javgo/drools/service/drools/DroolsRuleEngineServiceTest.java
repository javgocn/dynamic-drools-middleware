package cn.javgo.drools.service.drools;

import cn.javgo.drools.DroolsRunApplication;
import cn.javgo.drools.dao.RuleInfoDao;
import cn.javgo.drools.dto.RuleAttributeRelationDto;
import cn.javgo.drools.dto.fact.DynamicEntity;
import cn.javgo.drools.dto.fact.RuleExecutionObject;
import cn.javgo.drools.dto.fact.RuleExecutionResult;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.model.RuleInfo;
import cn.javgo.drools.service.BusSceneService;
import cn.javgo.drools.service.MetaEntityService;
import cn.javgo.drools.service.RuleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Drools 规则引擎 Service 测试类
 */
@Slf4j
@SpringBootTest(classes = DroolsRunApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class DroolsRuleEngineServiceTest {

    @Resource
    private DroolsRuleEngineService droolsRuleEngineService;

    @Resource
    private MetaEntityService metaEntityService;

    @Resource
    private BusSceneService busSceneService;

    @Resource
    private RuleInfoService ruleInfoService;

    @Resource
    private RuleInfoDao ruleInfoDao;

    @Test
    void testBusSceneService(){
        BusScene busScene = busSceneService.getBusSceneByIdentify("test");
        System.out.println(busScene);
    }

    @Test
    void testRuleInfoService(){
        BusScene busScene = busSceneService.getBusSceneByIdentify("test");
        // List<RuleInfo> ruleInfoList = ruleInfoService.getRuleInfoByScene(busScene);
        List<RuleInfo> ruleInfoList = ruleInfoDao.getRuleInfoByScene(busScene);
        System.out.println(ruleInfoList);
    }



    @Test
    void execute() {
        try {
            RuleExecutionObject ruleExecutionObject = new RuleExecutionObject();

            DynamicEntity dynamicEntity = new DynamicEntity();
            dynamicEntity.setId(1L);
            dynamicEntity.setName("用户");
            dynamicEntity.setIdentify("user");
            dynamicEntity.setAttributes("{\"username\": \"admin\",\"password\": \"admin\",\"email\": \"admin@localhost\"}");
            dynamicEntity.setDescription("用户实体");
            dynamicEntity.setCreUserId(1L);
            dynamicEntity.setRemark("备注");
            dynamicEntity.parseAttributes();

            ruleExecutionObject.addFactObject(dynamicEntity);

            RuleExecutionResult result = new RuleExecutionResult();
            ruleExecutionObject.setGlobal("_result", result);

            RuleExecutionObject executionObject = droolsRuleEngineService.execute(ruleExecutionObject, "test");

            System.out.println(executionObject);
        }catch (Exception e) {
            log.error("执行规则失败，失败原因：{}", e.getMessage());
        }
    }

    /**
     * 将元数据实体转换为动态实体
     *
     * @param metaEntity 元数据实体
     * @param dynamicEntity 动态实体
     * @return 动态实体
     */
    private DynamicEntity convertMetaEntityToDynamicEntity(MetaEntity metaEntity, DynamicEntity dynamicEntity){
        DynamicEntity dynamicEntity1 = new DynamicEntity();
        dynamicEntity.setId(metaEntity.getId());
        dynamicEntity.setName(metaEntity.getName());
        dynamicEntity.setIdentify(metaEntity.getIdentify());
        dynamicEntity.setDescription(metaEntity.getDescription());
        dynamicEntity.setCreUserId(metaEntity.getCreUserId());
        dynamicEntity.setRemark(metaEntity.getRemark());

        return dynamicEntity;
    }
}

