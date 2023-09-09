package cn.javgo.drools.dto.fact;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态实体(用于规则中动态创建对象)
 */
@Data
public class DynamicEntity {

    /**
     * key：属性名
     * value：属性值
     */
    private Map<String,Object> attributeMap = new HashMap<>();
}
