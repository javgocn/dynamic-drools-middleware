package cn.javgo.drools.service.drools.impl;

import cn.javgo.drools.dto.fact.RuleExecutionObject;
import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.DroolsExceptionEnum;
import cn.javgo.drools.model.BusScene;
import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.model.RuleAction;
import cn.javgo.drools.model.RuleDefinition;
import cn.javgo.drools.service.*;
import cn.javgo.drools.service.drools.DroolsRuleEngineService;
import cn.javgo.drools.util.drools.DroolsUtil;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 规则引擎Service实现
 */
@Service
@Transactional // 开启事务
public class DroolsRuleEngineServiceImpl implements DroolsRuleEngineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsRuleEngineServiceImpl.class);

    @Resource
    private BusSceneService busSceneService;

    @Resource
    private BusSceneCategoryService busSceneCategoryService;

    @Resource
    private MetaEntityService metaEntityService;

    @Resource
    private MetaDataTypeService metaDataTypeService;

    @Resource
    private MetaRuleAttributeService metaRuleAttributeService;

    @Resource
    private MetaExpressionService metaExpressionService;

    @Resource
    private RuleActionService ruleActionService;

    @Resource
    private RuleAttributeRelationService ruleAttributeRelationService;

    @Resource
    private RuleConditionService ruleConditionService;

    @Resource
    private RuleDefinitionService ruleDefinitionService;

    @Resource
    private SceneEntityRelationService sceneEntityRelationService;

    /**
     * DRL 文件导入关键字
     */
    private static final String IMPORT = "import";

    /**
     * DRL 文件换行符
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");


    /**
     * 执行规则引擎(核心方法)
     *
     * @param ruleExecutionObject 规则执行对象
     * @param scene 业务场景
     * @return 规则执行对象
     */
    @Override
    public RuleExecutionObject execute(RuleExecutionObject ruleExecutionObject, final String scene) {
        try {
            // 获取指定场景对应的 KieSession（用于执行规则）
            KieSession kieSession = DroolsUtil.getInstance().getDrlSessionInCache(scene);

            // 如果 KieSession 不为空，则执行规则
            if (kieSession != null){
                // 直接执行规则
                return executeRuleEngine(kieSession,ruleExecutionObject,scene);
            }else {
                // 重新编译加载规则，然后执行规则
                return compileRule(ruleExecutionObject, scene);
            }

            // TODO
        }
    }

    /**
     * 执行规则引擎
     *
     * @param kieSession KieSession
     * @param ruleExecutionObject 规则执行对象
     * @param scene 业务场景
     * @return 规则执行对象
     */
    private RuleExecutionObject executeRuleEngine(KieSession kieSession, RuleExecutionObject ruleExecutionObject, final String scene){
        try{
            // 1.插入全局对象到 KieSession 中
            Map<String, Object> globalMap = ruleExecutionObject.getGlobalMap();
            // 遍历全局对象
            for (Object glb : globalMap.entrySet()) {
                // 获取全局对象
                Map.Entry global = (Map.Entry) glb;
                // 获取全局对象的 key 和 value
                String key = (String) global.getKey();
                Object value = global.getValue();

                // 记录日志
                LOGGER.info("插入全局对象到 KieSession 中: {}",value.getClass().getName());

                // 插入全局对象到 KieSession 中
                kieSession.setGlobal(key, value);
            }

            // 2.插入事实对象到 KieSession 中
            // 2.1 插入业务对象
            List<Object> factObjectList = ruleExecutionObject.getFactObjectList();
            for (Object o : factObjectList) {
                LOGGER.info("插入业务对象到 KieSession 中: {}",o.getClass().getName());
                kieSession.insert(o);
            }
            // 2.2 插入 ruleExecutionObject 对象，用于回调
            kieSession.insert(ruleExecutionObject);

            // 3.插入规则动作
            BusScene busScene = busSceneService.getSceneByName(scene);
            List<Long> ruleDefinitionIds = ruleDefinitionService.getRuleDefinitionIdByBusScene(busScene);
            List<RuleAction> ruleActions = ruleActionService.getRuleActionByRuleIds(ruleDefinitionIds);
            for (RuleAction ruleAction : ruleActions) {
                LOGGER.info("插入规则动作到 KieSession 中: {}",ruleAction.getActionType());
                kieSession.insert(ruleAction);
            }

            // 4.执行所有规则
            // 4.1 检查是否全部执行
            if (ruleExecutionObject.isExecuteAll()){
                kieSession.fireAllRules();
            }else { // 4.2 执行指定规则（规则名称以 ruleName 开头的规则）
                kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleExecutionObject.getRuleName()));
            }

            // 5.返回规则执行对象
            return ruleExecutionObject;
        }catch (Exception e){
            LOGGER.error("规则引擎执行失败",e);
            throw new ServiceException(DroolsExceptionEnum.DROOLS_EXECUTE_FAIL_ERROR);
        }finally {
            // 释放 KieSession
            kieSession.dispose();
        }
    }

    /**
     * 重新编译加载规则（其实就是拼接 DRL 文件，然后重新加载到 KieSession 中）
     *
     * @param ruleExecutionObject 规则执行对象
     * @param scene 业务场景
     * @return 规则执行对象
     */
    private RuleExecutionObject compileRule(RuleExecutionObject ruleExecutionObject, final String scene){
        // 使用 StringBuilder 拼接 DRL 文件
        StringBuilder droolsRuleStr = new StringBuilder();

        LOGGER.info(LINE_SEPARATOR,"===================== 重新拼接 DRL 文件 =====================");

        // 1.引入 package 路径（同一包下的规则文件互相可见）
        droolsRuleStr.append("package com.drools.rule").append(";").append(LINE_SEPARATOR);

        // 2.引入 import 关键字（导入规则执行对象 RuleExecutionObject 和规则执行结果 RuleExecutionResult）
        droolsRuleStr.append(IMPORT).append(" ").append("cn.javgo.drools.dto.fact.RuleExecutionObject").append(";").append(LINE_SEPARATOR);
        droolsRuleStr.append(IMPORT).append(" ").append("cn.javgo.drools.dto.fact.RuleExecutionResult").append(";").append(LINE_SEPARATOR);

        // 3.引入 global 关键字（引入全局对象）
        droolsRuleStr.append("global RuleExecutionResult _result").append(";").append(LINE_SEPARATOR);

        // 4.引入实体信息（根据场景获取相关实体信息）
        /*BusScene busScene = busSceneService.getSceneByName(scene);
        List<Long> entityIds = sceneEntityRelationService.getEntityIdsBySceneId(busScene.getId());
        List<MetaEntity> metaEntities = metaEntityService.getMetaEntityByIds(entityIds);
        this.insertImportInfo(droolsRuleStr,metaEntities,busScene);*/

        // 获取场景对应的实体信息

        // 转化为 DynamicEntity 对象

        // DynamicEntity Many  ——》 key value   ——》 Bean

        // 加入 factObjectList 中

        LOGGER.info("场景对应的实体个数: {}",metaEntities.size());


        // 5.引入规则信息（根据场景获取相关规则信息）
        List<RuleDefinition> ruleDefinitions = ruleDefinitionService.getRuleDefinitionByBusSceneId(busScene.getId());

        LOGGER.info("场景对应的规则个数: {}",ruleDefinitions.size());

        // TODO

    }

    /**
     * 插入 DRL 的 import 语句
     *
     * @param droolsRuleStr DRL 文件字符串
     * @param metaEntityList 实体信息
     * @param busScene 业务场景
     */
    private void insertImportInfo(StringBuilder droolsRuleStr,List<MetaEntity> metaEntityList,BusScene busScene){

        // 1.导入场景对应的所有实体（这里导入通用动态实体类 DynamicEntity 即可，其中的 attributeMap 属性会根据实际情况动态赋值）
        List<Long> entityIds = sceneEntityRelationService.getEntityIdsBySceneId(busScene.getId());

        //
    }
}
