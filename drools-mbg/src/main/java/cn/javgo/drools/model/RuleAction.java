package cn.javgo.drools.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class RuleAction implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "规则ID")
    private Long ruleId;

    @ApiModelProperty(value = "动作名称")
    private String name;

    @ApiModelProperty(value = "动作类型，目前仅支持回调(CALLBACK)")
    private String type;

    @ApiModelProperty(value = "是否回调")
    private Integer callbackEnable;

    @ApiModelProperty(value = "回调地址")
    private String callbackUrl;

    @ApiModelProperty(value = "动作表达式")
    private String actionExpression;

    @ApiModelProperty(value = "动作描述")
    private String desc;

    @ApiModelProperty(value = "是否有效：1->有效（默认）0->无效")
    private Integer isEffect;

    @ApiModelProperty(value = "创建人ID")
    private Long creUserId;

    @ApiModelProperty(value = "创建时间")
    private Date creTime;

    @ApiModelProperty(value = "更新时间")
    private Date updTime;

    @ApiModelProperty(value = "其他备注")
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCallbackEnable() {
        return callbackEnable;
    }

    public void setCallbackEnable(Integer callbackEnable) {
        this.callbackEnable = callbackEnable;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getActionExpression() {
        return actionExpression;
    }

    public void setActionExpression(String actionExpression) {
        this.actionExpression = actionExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect) {
        this.isEffect = isEffect;
    }

    public Long getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(Long creUserId) {
        this.creUserId = creUserId;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", callbackEnable=").append(callbackEnable);
        sb.append(", callbackUrl=").append(callbackUrl);
        sb.append(", actionExpression=").append(actionExpression);
        sb.append(", desc=").append(desc);
        sb.append(", isEffect=").append(isEffect);
        sb.append(", creUserId=").append(creUserId);
        sb.append(", creTime=").append(creTime);
        sb.append(", updTime=").append(updTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}