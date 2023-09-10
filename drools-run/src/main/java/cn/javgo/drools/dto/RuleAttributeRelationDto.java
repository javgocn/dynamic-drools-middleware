package cn.javgo.drools.dto;

import cn.javgo.drools.model.RuleAttribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 规则属性关系 Dto
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RuleAttributeRelationDto extends RuleAttribute {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "规则ID")
    private Long ruleId;

    @ApiModelProperty(value = "属性ID")
    private Long attributeId;

    @ApiModelProperty(value = "属性实际值")
    private String attributeValue;
}
