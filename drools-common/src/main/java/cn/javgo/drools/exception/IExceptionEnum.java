package cn.javgo.drools.exception;

/**
 * API 返回码与返回信息接口
 */
public interface IExceptionEnum {

    /**
     * 返回码
     * @return long
     */
    String getCode();

    /**
     * 返回信息
     * @return String
     */
    String getMessage();
}
