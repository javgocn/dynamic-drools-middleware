package cn.javgo.drools.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装 Alibaba fastjson 的 JSON 序列化工具类
 */
public class JsonSerializeUtil implements Serializable {

    //=================================================  private  =================================================

    /**
     * 隐藏构造器：工具类不允许实例化
     */
    private JsonSerializeUtil() {
    }

    //=================================================  public  ==================================================

    //==========================================  序列化（对象 -> JSON）  ===========================================

    public static String toJson(final Object object) {
        if (object == null){
            return null;
        }
        return JSON.toJSONString(object);
    }

    public static String toJson(final Object object, final SerializerFeature... features) {
        if (object == null){
            return null;
        }
        return JSON.toJSONString(object, features);
    }

    public static String toJsonNoRef(final Object object) {
        if (object == null){
            return null;
        }
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toJsonWriteNull(final Object object) {
        return toJson(object,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty);
    }

    //==========================================  反序列化（JSON -> 对象） ==========================================

    public static JSONObject toJsonObj(final String json) {
        return JSON.parseObject(json);
    }

    public static <T> T formJson(final JSON json,final Class<T> classOfT){
        return JSON.toJavaObject(json,classOfT);
    }

    public static <T> T fromJson(final String json, final Type typeOfT) {
        if (StrUtil.isEmpty(json))
            return null;
        return JSON.parseObject(json, typeOfT);

    }

    public static <T> T fromJson(final String json, final Class<T> classOfT) {
        return fromJson(json, (Type) classOfT);
    }

    public static <T> T fromJson(final InputStream is, final Type typeOfT) throws IOException {
        return JSON.parseObject(is, typeOfT);
    }

    public static <T> T fromJson(final InputStream is, final Class<T> classOfT) throws IOException {
        return fromJson(is, (Type) classOfT);
    }

    public static <T> List<T> fromJsonList(final String json, final Class<T> classOfT) {
        // 如果 json == null 或者 json == ""，则返回空集合
        if (StrUtil.isEmpty(json)) {
            return new ArrayList<>();
        }
        return JSON.parseArray(json, classOfT);
    }
}
