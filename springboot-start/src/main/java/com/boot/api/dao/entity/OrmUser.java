package com.boot.api.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * �û�ע����ҵʱ�����˻��������˺ŵ��û���һ������ҵƴ����
������˺��ǣ���ʽ�� ���˺�:���˺����¼�Ǳ�
 * </p>
 *
 * @author 王俊辉
 * @since 2019-05-29
 */
public class OrmUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    /**
     * [ADMIN:1-����Ա; MASTER:2-����;  SERVICE:3-�ͷ�;  BUYER:4-�ɹ�Ա]
     */
    @TableField("salt")
    private String salt;

    @TableField("email")
    private String email;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("status")
    private String status;

    @TableField("create_time")
    private Date createTime;

    @TableField("last_login_time")
    private Date lastLoginTime;

    @TableField("last_update_time")
    private Date lastUpdateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
