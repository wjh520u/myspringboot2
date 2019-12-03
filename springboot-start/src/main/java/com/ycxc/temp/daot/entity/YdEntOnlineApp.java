package com.ycxc.temp.daot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 企业用户在线表
 * </p>
 *
 * @author 王俊辉
 * @since 2019-07-09
 */
public class YdEntOnlineApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ent_online_id", type = IdType.AUTO)
    private Integer entOnlineId;

    /**
     * 企业ID
     */
    @TableField("ent_id")
    private Integer entId;

    /**
     * 企业用户ID
     */
    @TableField("ent_user_id")
    private Integer entUserId;

    /**
     * 最新在线时间
     */
    @TableField("new_online_time")
    private Integer newOnlineTime;

    /**
     * 会话ID
     */
    @TableField("session_id")
    private String sessionId;

    /**
     * 最后登录时间
     */
    @TableField("login_date")
    private LocalDateTime loginDate;

    /**
     * 登出时间
     */
    @TableField("loginout_date")
    private LocalDateTime loginoutDate;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 创建人ID
     */
    @TableField("create_id")
    private Integer createId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人ID
     */
    @TableField("update_id")
    private Integer updateId;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除人ID
     */
    @TableField("delete_id")
    private Integer deleteId;

    /**
     * 删除时间
     */
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    public Integer getEntOnlineId() {
        return entOnlineId;
    }

    public void setEntOnlineId(Integer entOnlineId) {
        this.entOnlineId = entOnlineId;
    }
    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }
    public Integer getEntUserId() {
        return entUserId;
    }

    public void setEntUserId(Integer entUserId) {
        this.entUserId = entUserId;
    }
    public Integer getNewOnlineTime() {
        return newOnlineTime;
    }

    public void setNewOnlineTime(Integer newOnlineTime) {
        this.newOnlineTime = newOnlineTime;
    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public LocalDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }
    public LocalDateTime getLoginoutDate() {
        return loginoutDate;
    }

    public void setLoginoutDate(LocalDateTime loginoutDate) {
        this.loginoutDate = loginoutDate;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(Integer deleteId) {
        this.deleteId = deleteId;
    }
    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return "YdEntOnlineApp{" +
        "entOnlineId=" + entOnlineId +
        ", entId=" + entId +
        ", entUserId=" + entUserId +
        ", newOnlineTime=" + newOnlineTime +
        ", sessionId=" + sessionId +
        ", loginDate=" + loginDate +
        ", loginoutDate=" + loginoutDate +
        ", sort=" + sort +
        ", isDelete=" + isDelete +
        ", createId=" + createId +
        ", createTime=" + createTime +
        ", updateId=" + updateId +
        ", updateTime=" + updateTime +
        ", deleteId=" + deleteId +
        ", deleteTime=" + deleteTime +
        "}";
    }
}
