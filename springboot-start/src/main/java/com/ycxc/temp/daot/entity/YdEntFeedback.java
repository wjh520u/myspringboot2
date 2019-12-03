package com.ycxc.temp.daot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 评价反馈表
 * </p>
 *
 * @author 王俊辉
 * @since 2019-07-08
 */
public class YdEntFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("feekback_id")
    private String feekbackId;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 企业id
     */
    @TableField("enterprise_id")
    private String enterpriseId;

    /**
     * 反馈人
     */
    @TableField("feekback_user")
    private String feekbackUser;

    /**
     * 反馈人联系方式
     */
    @TableField("feekback_contract")
    private String feekbackContract;

    /**
     * 反馈内容
     */
    @TableField("feekback_content")
    private String feekbackContent;

    /**
     * 评价日期
     */
    @TableField("feedback_time")
    private LocalDateTime feedbackTime;

    /**
     * 评价等级：优、良、中、差
     */
    @TableField("feekback_grade")
    private String feekbackGrade;

    /**
     * 跟踪人
     */
    @TableField("tracker")
    private String tracker;

    /**
     * 跟踪日期
     */
    @TableField("track_time")
    private LocalDateTime trackTime;

    /**
     * 处理结果
     */
    @TableField("deal_result")
    private String dealResult;

    /**
     * 状态：1已跟踪处理0未跟踪处理
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    public String getFeekbackId() {
        return feekbackId;
    }

    public void setFeekbackId(String feekbackId) {
        this.feekbackId = feekbackId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    public String getFeekbackUser() {
        return feekbackUser;
    }

    public void setFeekbackUser(String feekbackUser) {
        this.feekbackUser = feekbackUser;
    }
    public String getFeekbackContract() {
        return feekbackContract;
    }

    public void setFeekbackContract(String feekbackContract) {
        this.feekbackContract = feekbackContract;
    }
    public String getFeekbackContent() {
        return feekbackContent;
    }

    public void setFeekbackContent(String feekbackContent) {
        this.feekbackContent = feekbackContent;
    }
    public LocalDateTime getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(LocalDateTime feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
    public String getFeekbackGrade() {
        return feekbackGrade;
    }

    public void setFeekbackGrade(String feekbackGrade) {
        this.feekbackGrade = feekbackGrade;
    }
    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }
    public LocalDateTime getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(LocalDateTime trackTime) {
        this.trackTime = trackTime;
    }
    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "YdEntFeedback{" +
        "feekbackId=" + feekbackId +
        ", productName=" + productName +
        ", enterpriseId=" + enterpriseId +
        ", feekbackUser=" + feekbackUser +
        ", feekbackContract=" + feekbackContract +
        ", feekbackContent=" + feekbackContent +
        ", feedbackTime=" + feedbackTime +
        ", feekbackGrade=" + feekbackGrade +
        ", tracker=" + tracker +
        ", trackTime=" + trackTime +
        ", dealResult=" + dealResult +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
