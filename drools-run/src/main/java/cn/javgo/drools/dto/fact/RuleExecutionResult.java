package cn.javgo.drools.dto.fact;

import lombok.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 规则执行结果
 */
@Data
public class RuleExecutionResult implements Serializable {

    /**
     * 规则执行中需要保存的数据
     */
    private Map<String,Object> resultMap = new HashMap<>();

    /**
     * 添加结果
     *
     * @param key key
     * @param value value
     */
    public void setResult(String key, Object value) {
        this.resultMap.put(key, value);
    }
}
