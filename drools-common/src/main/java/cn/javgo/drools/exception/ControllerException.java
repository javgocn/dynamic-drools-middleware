package cn.javgo.drools.exception;

/**
 * 控制层异常
 */
public class ControllerException extends EnumException{

    private static final long serialVersionUID = 1L;

    public ControllerException(IExceptionEnum responseEnum, String suffix) {
        super(responseEnum, suffix);
    }

    public ControllerException(IExceptionEnum responseEnum) {
        super(responseEnum);
    }
}
