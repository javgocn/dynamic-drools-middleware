package cn.javgo.drools.dto.fact;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装规则执行对象
 */
@Data
public class RuleExecutionObject implements Serializable {

    /**
     * fact 集合（即规则要操作的对象）
     */
    private List<Object> factObjectList = new ArrayList<>();

    /**
     * 全局对象集合
     */
    private Map<String, Object> globalMap = new HashMap<>();

    /**
     * 是否全部执行（默认全部）
     */
    private boolean executeAll = true;

    /**
     * 根据名称过滤要执行的规则（不全部执行时使用）
     */
    private String ruleName;

    /**
     * 添加 fact 对象
     */
    public void addFactObject(Object factObject) {
        this.factObjectList.add(factObject);
    }

    /**
     * 添加 fact 对象
     *
     * @param entityJson JSON 字符串
     */
    public void addFactObject(String entityJson) {
        // 反序列化 JSON 字符串为 Map
        Map<String,Object> parsedData = JSON.parseObject(entityJson, Map.class);

        // 创建动态实体
        DynamicEntity dynamicEntity = new DynamicEntity();

        // 将 Map 中的数据设置到动态实体中
        dynamicEntity.setAttributeMap(parsedData);

        // 将动态实体添加到 fact 集合中
        this.factObjectList.add(dynamicEntity);
    }

    /**
     * 设置 Global 参数
     *
     * @param key  key
     * @param value 值
     */
    public void setGlobal(String key, Object value) {
        this.globalMap.put(key, value);
    }
}
