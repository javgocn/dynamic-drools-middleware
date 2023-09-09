package cn.javgo.drools.exception;

/**
 * 业务异常
 */
public class ServiceException extends EnumException{

    private static final long serialVersionUID = 1L;

    public ServiceException(IExceptionEnum responseEnum, String suffix) {
        super(responseEnum, suffix);
    }

    public ServiceException(IExceptionEnum responseEnum) {
        super(responseEnum);
    }
}
