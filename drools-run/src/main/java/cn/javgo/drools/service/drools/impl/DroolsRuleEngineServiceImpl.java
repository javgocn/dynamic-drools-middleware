package cn.javgo.drools.service.drools.impl;

import cn.javgo.drools.dto.RuleAttributeRelationDto;
import cn.javgo.drools.dto.fact.RuleExecutionObject;
import cn.javgo.drools.exception.ServiceException;
import cn.javgo.drools.exception.enums.DroolsExceptionEnum;
import cn.javgo.drools.model.*;
import cn.javgo.drools.service.*;
import cn.javgo.drools.service.drools.DroolsRuleEngineService;
import cn.javgo.drools.util.CollectionUtil;
import cn.javgo.drools.util.drools.DroolsUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Drools 规则引擎 Service 实现
 */
@Service
public class DroolsRuleEngineServiceImpl implements DroolsRuleEngineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsRuleEngineServiceImpl.class);

    /**
     * DRL 文件中的 import 关键字
     */
    private static final String IMPORT = "import";

    /**
     * DRL 文件中的 package 关键字
     */
    private static final String PACKAGE = "package";

    /**
     * DRL 文件中的 global 关键字
     */
    private static final String GLOBAL = "global";

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 值为字符串类型的 DRL 属性需要加上双引号
     */
    private static final String[] arr = new String[]{"date-effective", "date-expires", "dialect", "activation-group", "agenda-group", "ruleflow-group"}

    @Resource
    private BusSceneService busSceneService;

    @Resource
    private MetaEntityService metaEntityService;

    @Resource
    private SceneEntityRelationService sceneEntityRelationService;

    @Resource
    private RuleInfoService ruleInfoService;

    @Resource
    private RuleConditionService ruleConditionService;

    @Resource
    private RuleActionService ruleActionService;

    /**
     * 执行规则引擎
     *
     * @param ruleExecutionObject 规则执行对象
     * @param scene 业务场景（标识）
     * @return 规则执行对象
     */
    @Override
    public RuleExecutionObject execute(RuleExecutionObject ruleExecutionObject, String scene) {
        try {
            // 获取指定场景的 KieSession
            KieSession kieSession = DroolsUtil.getInstance().getDrlSessionInCache(scene);

            // 如果 KieSession 不为空，则执行规则
            if (kieSession != null){
                // 直接执行规则
                return executeRuleEngine(kieSession,ruleExecutionObject,scene);
            }else {
                // 如果 KieSession 为空，则重新加载规则
                return compileRule(ruleExecutionObject,scene);
            }
        }catch (Exception e){
            LOGGER.error("Drools 规则引擎执行异常",e);
            throw new ServiceException(DroolsExceptionEnum.DROOLS_EXECUTE_FAIL_ERROR);
        }
    }

    /**
     * 执行规则引擎
     *
     * @param kieSession KieSession
     * @param ruleExecutionObject 规则执行对象
     * @param scene 场景（标识）
     * @return 规则执行对象
     */
    private RuleExecutionObject executeRuleEngine(KieSession kieSession,RuleExecutionObject ruleExecutionObject,final String scene){
        try {
            // 1.插入全局对象（global）
            Map<String, Object> globalMap = ruleExecutionObject.getGlobalMap();
            for (Map.Entry<String, Object> entry : globalMap.entrySet()) {
                LOGGER.info("插入全局对象：key:{},value:{}",entry.getKey(),entry.getValue());
                kieSession.setGlobal(entry.getKey(), entry.getValue());
            }

            // 2.插入事实（因子）对象（fact）
            // 2.1 插入业务事实对象（fact）
            List<Object> factObjectList = ruleExecutionObject.getFactObjectList();
            for (Object o : factObjectList) {
                LOGGER.info("插入事实对象：{}",o);
                kieSession.insert(o);
            }
            // 2.2 插入规则执行对象（fact），回调时使用
            kieSession.insert(ruleExecutionObject);

            // 3.构建动作（action）
            // 3.1 根据场景获取规则动作
            BusScene busScene = new BusScene();
            busScene.setIdentify(scene);
            List<RuleAction> ruleActionList = ruleActionService.getRuleActionByBusScene(busScene);
            // 3.2 插入规则动作
            for (RuleAction ruleAction : ruleActionList) {
                LOGGER.info("插入规则动作：{}",ruleAction);
                kieSession.insert(ruleAction);
            }

            // 4.执行规则
            // 4.1 检查是否全部执行
            if(ruleExecutionObject.isExecuteAll()){
                kieSession.fireAllRules();
            }else { // 4.2 执行指定规则(根据规则名称前缀匹配)
                kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleExecutionObject.getRuleName()));
            }

            // 5.执行完规则后，返回规则执行对象
            return ruleExecutionObject;
        }catch (Exception e){
            LOGGER.error("Drools 规则引擎执行异常",e);
            throw new ServiceException(DroolsExceptionEnum.DROOLS_EXECUTE_FAIL_ERROR);
        }finally {
            // 6.释放 KieSession
            kieSession.dispose();
        }
    }

    /**
     *  拼接 Drools 语句、编译规则、执行规则
     *
     * @param ruleExecutionObject 规则执行对象
     * @param scene 场景（标识）
     * @return 规则执行对象
     */
    private RuleExecutionObject compileRule(RuleExecutionObject ruleExecutionObject,final String scene){
        // 创建 StringBuilder 对象（线程不安全）用于拼接 Drools 语句
        StringBuilder droolsRuleStr = new StringBuilder();

        LOGGER.info("开始拼接 Drools 语句");

        // 1.拼接 package 语句
        droolsRuleStr.append(PACKAGE).append(" ").append("com.drools.rule").append(";").append(LINE_SEPARATOR);

        // 2.拼接 import 语句
        droolsRuleStr.append(IMPORT).append(" ").append("cn.javgo.drools.dto.fact.RuleExecutionObject").append(";").append(LINE_SEPARATOR);
        droolsRuleStr.append(IMPORT).append(" ").append("cn.javgo.drools.dto.fact.RuleExecutionResult").append(";").append(LINE_SEPARATOR);

        // 3.导入业务场景对应的实体
        droolsRuleStr.append(IMPORT).append(" ").append("cn.javgo.drools.model.MetaEntity").append(";").append(LINE_SEPARATOR);

        // 4.导入基本数据类型（主要用到 String、Map、List）
        droolsRuleStr.append(IMPORT).append(" ").append("java.lang.*").append(";").append(LINE_SEPARATOR);
        droolsRuleStr.append(IMPORT).append(" ").append("java.util.*").append(";").append(LINE_SEPARATOR);

        // 5.导入规则动作
        droolsRuleStr.append(IMPORT).append(" ").append("cn.javgo.drools.model.RuleAction").append(";").append(LINE_SEPARATOR);

        // 6.拼接 global 语句
        droolsRuleStr.append(GLOBAL).append(" ").append("RuleExecutionResult").append(" ").append("_result").append(";").append(LINE_SEPARATOR);

        // 7.拼接每个规则的 Drools 语句
        // 7.1 通过场景获取规则对象
        BusScene busScene = new BusScene();
        busScene.setIdentify(scene);
        List<RuleInfo> ruleInfoList = ruleInfoService.getRuleInfoByScene(busScene);

        LOGGER.info("{} 场景下共有 {} 条规则",scene,ruleInfoList.size());

        // 7.2 遍历拼接出每个规则的 Drools 语句
        for (RuleInfo ruleInfo : ruleInfoList) {
            StringBuffer ruleTemp;
            ruleTemp = getDroolsInfoByRule(ruleInfo);
            droolsRuleStr.append(ruleTemp);
        }
    }

    /**
     * 组装 Drools 规则信息
     *
     * @param ruleInfo 规则信息
     * @return 某个规则的 Drools 语句
     */
    private StringBuffer getDroolsInfoByRule(RuleInfo ruleInfo){
        // 创建 StringBuffer 对象（线程安全）用于拼接 Drools 规则信息
        StringBuffer ruleStr = new StringBuffer();
        // 1.拼接规则自身涉及到的属性信息
        insertRuleAttribute(ruleStr,ruleInfo);
        // 2.拼接规则条件
        insertRuleCondition(ruleStr,ruleInfo);
        // 3.拼接规则动作
        insertRuleAction(ruleStr,ruleInfo);

        return ruleStr;
    }

    /**
     * 根据规则信息拼接规则自身涉及到的属性信息
     *
     * @param rulerStr 规则字符串
     * @param ruleInfo 规则信息
     */
    private void insertRuleAttribute(StringBuffer rulerStr,RuleInfo ruleInfo){
        // 1.拼接规则名称（默认带双引号）
        rulerStr.append(LINE_SEPARATOR).append("rule").append(" ").append("\"").append(ruleInfo.getName()).append("\"").append(LINE_SEPARATOR);

        // 2.拼接规则自身所使用的属性信息
        List<RuleAttributeRelationDto> attributeList = ruleInfoService.getRuleAttributeByRuleId(ruleInfo.getId());
        if (!CollectionUtil.collectionIsNull(attributeList)){
            for (RuleAttributeRelationDto ruleAttributeRelationDto : attributeList) {
                // 如果配置的属性值是字符串类型，则需要单独处理
                if (ArrayUtils.contains(arr,ruleAttributeRelationDto.getIdentify())){
                    rulerStr.append("    ").append(ruleAttributeRelationDto.getIdentify()).append(" ").append("\"").append(ruleAttributeRelationDto.getAttributeValue()).append("\"").append(LINE_SEPARATOR);
                }else {
                    rulerStr.append("    ").append(ruleAttributeRelationDto.getIdentify()).append(" ").append(ruleAttributeRelationDto.getAttributeValue()).append(LINE_SEPARATOR);
                }
            }
        }
    }

    /**
     * 根据规则信息拼接规则条件信息
     *
     * @param ruleStr 规则字符串
     * @param ruleInfo 规则信息
     */
    private void insertRuleCondition(StringBuffer ruleStr,RuleInfo ruleInfo){
        // 1.拼接 when 关键字
        ruleStr.append(LINE_SEPARATOR).append("when").append(LINE_SEPARATOR);

        // 2.拼接参数
        ruleStr.append(LINE_SEPARATOR).append("$ruleExecutionObject : RuleExecutionObject()").append(LINE_SEPARATOR);
        ruleStr.append(LINE_SEPARATOR).append("$action : RuleAction()").append(LINE_SEPARATOR);

        // 根据规则 ID 获取规则条件
        List<RuleCondition> conditionList = ruleConditionService.getRuleConditionById(ruleInfo.getId());
        // 如果存在规则条件，则拼接规则条件
        if (!CollectionUtil.collectionIsNull(conditionList)){
            insertRuleConditionFromList(ruleStr,conditionList);
        }else { // 不存在规则条件，则默认满足
            ruleStr.append("eval(true)").append(LINE_SEPARATOR);
        }
    }

    /**
     * 处理条件部分内容
     *
     * @param ruleStr 规则字符串
     * @param conditionList 条件列表
     */
    private void insertRuleConditionFromList(StringBuffer ruleStr,List<RuleCondition> conditionList){
        // 临时存储每个实体的条件字符串
        Map<String,StringBuffer> entityConditionMap = new HashMap<>();

        // 遍历条件列表
        for (RuleCondition condition : conditionList) {
            // 获取该条件对应的实体
            Long entityId = condition.getEntityId();
            MetaEntity metaEntity = metaEntityService.getMetaEntityById(entityId);
            String entityIdentify = metaEntity.getIdentify();

            // 获取该实体对应的条件字符串
            StringBuffer conditionStr = entityConditionMap.getOrDefault(entityIdentify,new StringBuffer());

            // 如果当前条件是逻辑运算符（&&、||），则递归处理其子条件
            if (isLogicalOperator(condition)){
                // 添加左括号
                conditionStr.append("(");
                // 递归处理子条件
                buildConditionStr(conditionStr,getSubConditions(condition));
                // 添加右括号
                conditionStr.append(")");
            }else { // 如果当前条件是普通条件，则直接拼接
                // 如果当前条件内容长度大于 0，则不同属性之间添加逗号
                if (conditionStr.length() > 0) {
                    conditionStr.append(", ");
                }
                // 拼接条件内容
                conditionStr.append(condition.getConditionExpression());
            }

            // 将当前实体对应的条件字符串放入临时存储中
            entityConditionMap.put(entityIdentify,conditionStr);
        }

        // 遍历实体条件字符串，拼接到规则字符串中
        for (Map.Entry<String, StringBuffer> entry : entityConditionMap.entrySet()) {
            // 获取实体标识
            String entityIdentify = entry.getKey();
            // 获取实体条件字符串
            StringBuffer conditionsForEntity = entry.getValue();
            // 拼接实体条件字符串
            ruleStr.append("$").append(entityIdentify).append(" : DynamicEntity(identify == \"").append(entityIdentify)
                    .append("\", ").append(conditionsForEntity).append(")").append(LINE_SEPARATOR);
        }

    }

    /**
     * 递归构建条件内容
     *
     * @param conditionStr 条件内容字符串
     * @param subConditions 子条件列表
     */
    private void buildConditionStr(StringBuffer conditionStr,List<RuleCondition> subConditions){
        // 遍历条件列表
        for (RuleCondition subCondition : subConditions) {
            // 如果当前条件是逻辑运算符（&&、||），则递归处理其子条件
            if (isLogicalOperator(subCondition)){
                // 添加左括号
                conditionStr.append("(");
                // 递归处理子条件
                buildConditionStr(conditionStr,getSubConditions(subCondition));
                // 添加右括号
                conditionStr.append(") ").append(subCondition.getConditionExpression()).append(" ");
            }else { // 如果当前条件是普通条件，则直接拼接
                // 如果当前条件内容长度大于 0，则不同属性之间添加逗号
                if (conditionStr.length() > 0) {
                    conditionStr.append(", ");
                }
                // 拼接条件内容
                conditionStr.append(subCondition.getConditionExpression());
            }
        }
    }

    /**
     * 判断条件是否是逻辑运算符
     *
     * @param ruleCondition 条件
     * @return 是否是逻辑运算符
     */
    private boolean isLogicalOperator(RuleCondition ruleCondition){
        return "&&".equals(ruleCondition.getConditionExpression()) || "||".equals(ruleCondition.getConditionExpression());
    }

    /**
     * 根据父条件获取子条件
     *
     * @param parentCondition 父条件
     * @return 子条件列表
     */
    private List<RuleCondition> getSubConditions(RuleCondition parentCondition){
        return ruleConditionService.getRuleConditionByParentId(parentCondition.getId());
    }

    /**
     * 根据规则信息拼接规则动作信息 TODO
     *
     * @param ruleStr 规则字符串
     * @param ruleInfo 规则信息
     */
    private void insertRuleAction(StringBuffer ruleStr,RuleInfo ruleInfo){
        // 1.拼接 then 关键字
        ruleStr.append(LINE_SEPARATOR).append("then").append(LINE_SEPARATOR);

        // 2.根据规则 ID 获取规则动作
        List<RuleAction> action = ruleActionService.getRuleActionByRuleId(ruleInfo.getId());
    }
}
