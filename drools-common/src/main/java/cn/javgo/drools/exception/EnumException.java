package cn.javgo.drools.exception;

import lombok.Getter;
import java.util.Objects;

/**
 * 集成运行时异常的自定义异常
 */
public class EnumException extends RuntimeException {

    /**
     * 错误的枚举返回
     */
    @Getter
    private IExceptionEnum responseEnum;

    /**
     * 补充错误信息
     */
    private String suffix;

    EnumException(IExceptionEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    EnumException(IExceptionEnum responseEnum, String suffix) {
        super();
        this.responseEnum = responseEnum;
        this.suffix = suffix;
    }

    /**
     * 获取错误信息
     */
    String getSuffix() {
        return Objects.isNull(this.suffix) ? "" : this.suffix;
    }
}
