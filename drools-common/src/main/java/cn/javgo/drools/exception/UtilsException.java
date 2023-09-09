package cn.javgo.drools.exception;

/**
 * 工具类异常
 */
public class UtilsException extends EnumException{

    private static final long serialVersionUID = 1L;

    public UtilsException(IExceptionEnum responseEnum, String suffix) {
        super(responseEnum, suffix);
    }

    public UtilsException(IExceptionEnum responseEnum) {
        super(responseEnum);
    }
}
