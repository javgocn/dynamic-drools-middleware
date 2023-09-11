package cn.javgo.drools.dto.fact;

import cn.javgo.drools.model.MetaEntity;
import cn.javgo.drools.util.JsonSerializeUtil;
import cn.javgo.drools.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 * 动态通用实体，主要用于解析出 MetaEntity 的 JSON 格式的 attributes 字段为实体属性（用 Map 存储）
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class DynamicEntity extends MetaEntity {

    @ApiModelProperty(value = "解析后的实体属性：key 为属性名，value 为属类型")
    private Map<String, String> realAttributes;

    /**
     * 获取解析后的实体属性
     *
     * @return 解析后的实体属性
     */
    public Map<String,String> getRealAttributes(){
        // 如果 realAttributes 为空，则解析 attributes 字段为实体属性
        if (realAttributes == null){
            parseAttributes();
        }

        // 返回 realAttributes
        return realAttributes;
    }

    /**
     * 解析 attributes 字段为实体属性(用 Map 存储)
     */
    public void parseAttributes() {
        // 如果 attributes 为空，则直接返回
        if (StringUtil.isNull(this.getAttributes())) {
            return;
        }

        // 获取 attributes 字段的值
        String attributes = this.getAttributes();

        try {
            // 将 JSON 格式的 attributes 字段的值转换为 Map
            Map<String,String> map = JsonSerializeUtil.fromJson(attributes, Map.class);

            // 将反序列化后的 Map 赋值给 realAttributes
            this.realAttributes = map;
        }catch (Exception e){
            // 反序列化失败，打印日志
            log.error("反序列化失败，attributes = {},失败原因：{}", attributes, e.getMessage());
        }
    }
}
