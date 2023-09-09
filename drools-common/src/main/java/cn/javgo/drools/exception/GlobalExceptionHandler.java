package cn.javgo.drools.exception;

import cn.javgo.drools.api.CommonResult;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLSyntaxErrorException;

/**
 * 全局异常处理
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 匹配 error 错误并处理
     */
    private static final String ERROR_PATH = "/error";

    /**
     * 处理 Exception 抛出异常的情况
     *
     * @param e 传递的最顶级的异常
     * @return 返回响应
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<Object> handle(Exception e){

        // 处理自定义异常
        if (e instanceof EnumException){
            // 强转为自定义异常
            EnumException enumException = (EnumException) e;
            // 获取异常枚举
            IExceptionEnum responseEnum = enumException.getResponseEnum();

            // 打印异常信息
            LOGGER.error("异常提示如下，CODE:{}，额外 Message:{}", responseEnum.getCode(), responseEnum.getMessage());

            // 返回异常信息
            return CommonResult.failed(responseEnum, responseEnum.getMessage() + enumException.getSuffix());
        }

        // 打印异常信息
        LOGGER.error("异常如下行所示：", e);

        // 如果是请求方法不支持异常
        if (e instanceof HttpRequestMethodNotSupportedException){
            return CommonResult.failed(ExceptionEnum.SYS_METHOD_NOT_ALLOWED);
        }

        // 如果是 JSON 数据格式错误异常
        if (e instanceof HttpMessageNotReadableException){
            return CommonResult.failed(ExceptionEnum.SYS_JSON_DATA_ERROR);
        }

        // 如果是媒体类型不支持异常
        if (e instanceof HttpMediaTypeNotSupportedException){
            return CommonResult.failed(ExceptionEnum.SYS_UNSUPPORTED_MEDIA_TYPE);
        }

        // 如果是数据库字段不存在异常
        if (e instanceof SQLSyntaxErrorException){
            return CommonResult.failed(ExceptionEnum.SYS_DATABASE_FIELD_NOT_EXIST);
        }

        // 如果是数据库操作异常
        if (e instanceof DataAccessException){
            return CommonResult.failed(ExceptionEnum.SYS_DATABASE_FAILURE);
        }

        // 其他情况返回系统异常
        return CommonResult.failed(ExceptionEnum.SYS_FAILURE_EXCEPTION);
    }
}
