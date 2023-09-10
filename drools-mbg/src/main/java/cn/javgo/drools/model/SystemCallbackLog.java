package cn.javgo.drools.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SystemCallbackLog implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "触发回调的规则ID")
    private Long ruleId;

    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;

    @ApiModelProperty(value = "回调URL")
    private String callbackUrl;

    @ApiModelProperty(value = "回调结果")
    private String callbackResult;

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

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackResult() {
        return callbackResult;
    }

    public void setCallbackResult(String callbackResult) {
        this.callbackResult = callbackResult;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", callbackTime=").append(callbackTime);
        sb.append(", callbackUrl=").append(callbackUrl);
        sb.append(", callbackResult=").append(callbackResult);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}