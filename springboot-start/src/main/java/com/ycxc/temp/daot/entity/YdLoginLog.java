package com.ycxc.temp.daot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王俊辉
 * @since 2019-07-09
 */
public class YdLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "login_log_id", type = IdType.AUTO)
    private Integer loginLogId;

    /**
     * 门店ID
     */
    @TableField("ent_id")
    private Integer entId;

    /**
     * 用户ID
     */
    @TableField("ent_user_id")
    private Integer entUserId;

    /**
     * 登录IP地址
     */
    @TableField("loginip")
    private String loginip;

    /**
     * 登录时间
     */
    @TableField("logindate")
    private LocalDateTime logindate;

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

    @TableField("create_id")
    private Integer createId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_id")
    private Integer updateId;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("delete_id")
    private Integer deleteId;

    @TableField("delete_time")
    private LocalDateTime deleteTime;

    public Integer getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
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
    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }
    public LocalDateTime getLogindate() {
        return logindate;
    }

    public void setLogindate(LocalDateTime logindate) {
        this.logindate = logindate;
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
        return "YdLoginLog{" +
        "loginLogId=" + loginLogId +
        ", entId=" + entId +
        ", entUserId=" + entUserId +
        ", loginip=" + loginip +
        ", logindate=" + logindate +
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
