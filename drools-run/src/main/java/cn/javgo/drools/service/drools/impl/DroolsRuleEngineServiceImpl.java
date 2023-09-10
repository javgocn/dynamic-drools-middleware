package cn.javgo.drools.service.drools.impl;

import cn.javgo.drools.dto.fact.RuleExecutionObject;
import cn.javgo.drools.service.drools.DroolsRuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    public RuleExecutionObject execute(RuleExecutionObject ruleExecutionObject, String scene) {
        return null;
    }
}
