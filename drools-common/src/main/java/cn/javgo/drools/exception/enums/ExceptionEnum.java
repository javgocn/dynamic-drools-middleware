package cn.javgo.drools.exception.enums;

import cn.javgo.drools.exception.IExceptionEnum;
import lombok.Getter;

/**
 * 异常枚举<br><br>
 * 异常取值范围划分：<br>
 * 1、系统保留异常：00000 —— 10000<br>
 * 2、Redis 异常：10001 —— 11000<br>
 * 3、MongoDB 异常：11001 —— 12000<br>
 * 4、RabbitMQ 异常：12001 —— 13000<br>
 */
public enum ExceptionEnum implements IExceptionEnum {

    SYS_INVOKING_ERROR("LLI-00000", "操作失败"),
    SYS_INVOKING_SUCCESS("LLI-00001", "操作成功"),
    SYS_JSON_FAILURE("LLI-00003", "Json序列化对象错误"),
    SYS_METHOD_NOT_ALLOWED("LLI-00004", "POST/GET请求方式错误"),
    SYS_JSON_DATA_ERROR("LLI-00005", "JSON数据格式错误"),
    SYS_UNSUPPORTED_MEDIA_TYPE("LLI-00006", "请求数据类型不正确"),
    SYS_REQUEST_PARAM_MISSING("LLI-00007", "参数缺失"),
    SYS_SERVICE_NOT_FOUND_ERROR("LLI-00008", "不存在此接口"),
    SYS_DATABASE_FAILURE("LLI-00009", "数据库调用失败"),
    SYS_DATABASE_NULL_FAILURE("LLI-00010", "数据不存在"),
    SYS_DATABASE_FIELD_NOT_EXIST("CPYY-00011", "数据库表字段缺失"),
    SYS_REDIS_LOCK_RUNNING_ERROR("LLI-00012", "存在进行中的任务,请稍后再试"),
    SYS_FAILURE_EXCEPTION("LLI-10000", "系统异常");

    /**
     * 异常编码
     */
    @Getter
    private String code;

    /**
     * 异常信息
     */
    @Getter
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
