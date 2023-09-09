package cn.javgo.drools.exception.enums;

import cn.javgo.drools.exception.IExceptionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Drools 异常枚举<br><br>
 * 异常取值范围划分：<br>
 * 1、系统保留异常：00000 —— 10000<br>
 * 2、Redis 异常：10001 —— 11000<br>
 * 3、MongoDB 异常：11001 —— 12000<br>
 * 4、RabbitMQ 异常：12001 —— 13000<br>
 * 5、Drools 异常：14001 —— 15000<br>
 */
public enum DroolsExceptionEnum implements IExceptionEnum {

    DROOLS_EXECUTE_FAIL_ERROR("LLI-14001", "规则引擎执行出错"),
    DROOLS_BUILDER_ACTION_FAIL_ERROR("LLI-14002", "构造规则动作对象时出错,请检查"),
    DROOLS_INIT_RULE_FAIL_ERROR("LLI-14003", "Drools初始化规则失败,请检查DRL语句"),
    DROOLS_UTIL_INIT_FAIL_ERROR("LLI-14004", "Drools初始化失败");

    @Getter
    private String code;

    @Getter
    @Setter
    private String message;

    DroolsExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
