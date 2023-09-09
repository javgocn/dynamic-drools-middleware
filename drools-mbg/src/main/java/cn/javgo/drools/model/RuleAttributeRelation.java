package cn.javgo.drools.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RuleAttributeRelation implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "规则id")
    private Long ruleId;

    @ApiModelProperty(value = "规则属性id")
    private Long ruleAttributeId;

    @ApiModelProperty(value = "规则属性值，回覆盖默认值")
    private String ruleAttributeValue;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRuleAttributeId() {
        return ruleAttributeId;
    }

    public void setRuleAttributeId(Long ruleAttributeId) {
        this.ruleAttributeId = ruleAttributeId;
    }

    public String getRuleAttributeValue() {
        return ruleAttributeValue;
    }

    public void setRuleAttributeValue(String ruleAttributeValue) {
        this.ruleAttributeValue = ruleAttributeValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", ruleAttributeId=").append(ruleAttributeId);
        sb.append(", ruleAttributeValue=").append(ruleAttributeValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}