package cn.javgo.drools.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class MetaEntity implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "实体名称(中文名称)")
    private String name;

    @ApiModelProperty(value = "实体标识(实体类名)")
    private String identify;

    @ApiModelProperty(value = "JSON格式的实体属性及其数据类型")
    private String attributes;

    @ApiModelProperty(value = "实体描述")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
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
        sb.append(", name=").append(name);
        sb.append(", identify=").append(identify);
        sb.append(", attributes=").append(attributes);
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