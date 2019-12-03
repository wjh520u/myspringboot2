package com.ycxc.temp.daot.entity;

import java.math.BigDecimal;
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
 * @since 2019-08-16
 */
public class Ent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ent_id", type = IdType.AUTO)
    private Integer entId;

    /**
     * 6位城市代码+5位递增数字
     */
    @TableField("ent_no")
    private String entNo;

    /**
     * 旧系统的企业编号，用户系统并行是的数据同步
     */
    @TableField("ent_old_no")
    private String entOldNo;

    /**
     * [FIX:1-维修厂; INSPECT:2-检测站]
     */
    @TableField("ent_type")
    private Integer entType;

    @TableField("ent_name")
    private String entName;

    @TableField("short_name")
    private String shortName;

    @TableField("province")
    private String province;

    @TableField("city")
    private String city;

    @TableField("district")
    private String district;

    @TableField("address")
    private String address;

    @TableField("zip")
    private String zip;

    @TableField("contact_person")
    private String contactPerson;

    @TableField("phone")
    private String phone;

    @TableField("fax")
    private String fax;

    @TableField("email")
    private String email;

    @TableField("licence_no")
    private String licenceNo;

    @TableField("legal_person")
    private String legalPerson;

    @TableField("fund_amount")
    private String fundAmount;

    /**
     * [STATE:1-国有; GROUP:2-集体; PRIVATE:3-私营; INDIVIDUAL:4-个体; UNION:5-联营; STOCK:6-股份制; FOREIGN:7-外资]
     */
    @TableField("economy_kind")
    private Integer economyKind;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("is_agreement")
    private Integer isAgreement;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("ent_img")
    private String entImg;

    /**
     * 列表小图
     */
    @TableField("ent_logo")
    private String entLogo;

    @TableField("longitude")
    private BigDecimal longitude;

    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("is_open")
    private Integer isOpen;

    @TableField("close_reason")
    private String closeReason;

    /**
     * [OK:0-正常; CANCEL:1-注销]
     */
    @TableField("status")
    private Integer status;

    /**
     * [AUTO:1-自动; HAND:2-手动; ALL:3-手动&自动;NOT:4-不需要拍照]
     */
    @TableField("photo_type")
    private Integer photoType;

    /**
     * 车辆获取方式1
     */
    @TableField("is_reader")
    private Integer isReader;

    /**
     * 车辆获取方式2
     */
    @TableField("is_manual")
    private Integer isManual;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("is_open_read")
    private Integer isOpenRead;

    /**
     * [NO:0-关闭; YES:1-开启]
     */
    @TableField("tips_flag")
    private Integer tipsFlag;

    /**
     * 毫米
     */
    @TableField("print_x")
    private Integer printX;

    /**
     * 毫米
     */
    @TableField("print_y")
    private Integer printY;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_user")
    private Integer createUser;

    @TableField("bank_no")
    private String bankNo;

    @TableField("acct_no")
    private String acctNo;

    @TableField("score_value")
    private BigDecimal scoreValue;

    @TableField("total_score_num")
    private Integer totalScoreNum;

    @TableField("good_score_num")
    private Integer goodScoreNum;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("sync_flag")
    private Integer syncFlag;

    @TableField("max_station")
    private Integer maxStation;

    @TableField("back_print_x")
    private Integer backPrintX;

    @TableField("back_print_y")
    private Integer backPrintY;

    /**
     * 余额
     */
    @TableField("balance_amt")
    private BigDecimal balanceAmt;

    /**
     * [STYLUS_PRINTER:0-针式打印机;INKJET_PRINTER:1-喷墨打印机;LASER_PRINTER:3-激光打印机;OTHERS:4-其他]
     */
    @TableField("printer_type")
    private Integer printerType;

    /**
     * 企业余额不足时，紧急开通维护记录的数量
     */
    @TableField("permit_fix_num")
    private Integer permitFixNum;

    /**
     * [NO:0-关闭; YES:1-开启]
     */
    @TableField("pemit_fix_flag")
    private Integer pemitFixFlag;

    /**
     * 已使用紧急数量
     */
    @TableField("permit_fix_used")
    private Integer permitFixUsed;

    @TableField("ycxc_ent_id")
    private Integer ycxcEntId;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("sync_plat_edit_flag")
    private Integer syncPlatEditFlag;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("sync_plat_del_flag")
    private Integer syncPlatDelFlag;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("sync_plat_new_flag")
    private Integer syncPlatNewFlag;

    @TableField("is_fix_check")
    private Integer isFixCheck;

    /**
     * 检验检测机构唯一编码,检验检测机构唯一编码由行政区划代码（6位）和检验检测机构代码（3位）组成
     */
    @TableField("dsId")
    private String dsId;

    /**
     * 省网检测站编号
     */
    @TableField("dsId_province")
    private String dsidProvince;

    /**
     * 0未升级 1已升级
     */
    @TableField("is_upgrade")
    private Integer isUpgrade;

    /**
     * 0未备案 1已备案
     */
    @TableField("is_archives")
    private Integer isArchives;

    /**
     * 数据中心ID
     */
    @TableField("dc_id")
    private String dcId;

    /**
     * 合格证前缀
     */
    @TableField("cert_prefix")
    private String certPrefix;

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }
    public String getEntNo() {
        return entNo;
    }

    public void setEntNo(String entNo) {
        this.entNo = entNo;
    }
    public String getEntOldNo() {
        return entOldNo;
    }

    public void setEntOldNo(String entOldNo) {
        this.entOldNo = entOldNo;
    }
    public Integer getEntType() {
        return entType;
    }

    public void setEntType(Integer entType) {
        this.entType = entType;
    }
    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }
    public String getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(String fundAmount) {
        this.fundAmount = fundAmount;
    }
    public Integer getEconomyKind() {
        return economyKind;
    }

    public void setEconomyKind(Integer economyKind) {
        this.economyKind = economyKind;
    }
    public Integer getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(Integer isAgreement) {
        this.isAgreement = isAgreement;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public String getEntImg() {
        return entImg;
    }

    public void setEntImg(String entImg) {
        this.entImg = entImg;
    }
    public String getEntLogo() {
        return entLogo;
    }

    public void setEntLogo(String entLogo) {
        this.entLogo = entLogo;
    }
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getPhotoType() {
        return photoType;
    }

    public void setPhotoType(Integer photoType) {
        this.photoType = photoType;
    }
    public Integer getIsReader() {
        return isReader;
    }

    public void setIsReader(Integer isReader) {
        this.isReader = isReader;
    }
    public Integer getIsManual() {
        return isManual;
    }

    public void setIsManual(Integer isManual) {
        this.isManual = isManual;
    }
    public Integer getIsOpenRead() {
        return isOpenRead;
    }

    public void setIsOpenRead(Integer isOpenRead) {
        this.isOpenRead = isOpenRead;
    }
    public Integer getTipsFlag() {
        return tipsFlag;
    }

    public void setTipsFlag(Integer tipsFlag) {
        this.tipsFlag = tipsFlag;
    }
    public Integer getPrintX() {
        return printX;
    }

    public void setPrintX(Integer printX) {
        this.printX = printX;
    }
    public Integer getPrintY() {
        return printY;
    }

    public void setPrintY(Integer printY) {
        this.printY = printY;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }
    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }
    public BigDecimal getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(BigDecimal scoreValue) {
        this.scoreValue = scoreValue;
    }
    public Integer getTotalScoreNum() {
        return totalScoreNum;
    }

    public void setTotalScoreNum(Integer totalScoreNum) {
        this.totalScoreNum = totalScoreNum;
    }
    public Integer getGoodScoreNum() {
        return goodScoreNum;
    }

    public void setGoodScoreNum(Integer goodScoreNum) {
        this.goodScoreNum = goodScoreNum;
    }
    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }
    public Integer getMaxStation() {
        return maxStation;
    }

    public void setMaxStation(Integer maxStation) {
        this.maxStation = maxStation;
    }
    public Integer getBackPrintX() {
        return backPrintX;
    }

    public void setBackPrintX(Integer backPrintX) {
        this.backPrintX = backPrintX;
    }
    public Integer getBackPrintY() {
        return backPrintY;
    }

    public void setBackPrintY(Integer backPrintY) {
        this.backPrintY = backPrintY;
    }
    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }
    public Integer getPrinterType() {
        return printerType;
    }

    public void setPrinterType(Integer printerType) {
        this.printerType = printerType;
    }
    public Integer getPermitFixNum() {
        return permitFixNum;
    }

    public void setPermitFixNum(Integer permitFixNum) {
        this.permitFixNum = permitFixNum;
    }
    public Integer getPemitFixFlag() {
        return pemitFixFlag;
    }

    public void setPemitFixFlag(Integer pemitFixFlag) {
        this.pemitFixFlag = pemitFixFlag;
    }
    public Integer getPermitFixUsed() {
        return permitFixUsed;
    }

    public void setPermitFixUsed(Integer permitFixUsed) {
        this.permitFixUsed = permitFixUsed;
    }
    public Integer getYcxcEntId() {
        return ycxcEntId;
    }

    public void setYcxcEntId(Integer ycxcEntId) {
        this.ycxcEntId = ycxcEntId;
    }
    public Integer getSyncPlatEditFlag() {
        return syncPlatEditFlag;
    }

    public void setSyncPlatEditFlag(Integer syncPlatEditFlag) {
        this.syncPlatEditFlag = syncPlatEditFlag;
    }
    public Integer getSyncPlatDelFlag() {
        return syncPlatDelFlag;
    }

    public void setSyncPlatDelFlag(Integer syncPlatDelFlag) {
        this.syncPlatDelFlag = syncPlatDelFlag;
    }
    public Integer getSyncPlatNewFlag() {
        return syncPlatNewFlag;
    }

    public void setSyncPlatNewFlag(Integer syncPlatNewFlag) {
        this.syncPlatNewFlag = syncPlatNewFlag;
    }
    public Integer getIsFixCheck() {
        return isFixCheck;
    }

    public void setIsFixCheck(Integer isFixCheck) {
        this.isFixCheck = isFixCheck;
    }
    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }
    public String getDsidProvince() {
        return dsidProvince;
    }

    public void setDsidProvince(String dsidProvince) {
        this.dsidProvince = dsidProvince;
    }
    public Integer getIsUpgrade() {
        return isUpgrade;
    }

    public void setIsUpgrade(Integer isUpgrade) {
        this.isUpgrade = isUpgrade;
    }
    public Integer getIsArchives() {
        return isArchives;
    }

    public void setIsArchives(Integer isArchives) {
        this.isArchives = isArchives;
    }
    public String getDcId() {
        return dcId;
    }

    public void setDcId(String dcId) {
        this.dcId = dcId;
    }
    public String getCertPrefix() {
        return certPrefix;
    }

    public void setCertPrefix(String certPrefix) {
        this.certPrefix = certPrefix;
    }

    @Override
    public String toString() {
        return "Ent{" +
        "entId=" + entId +
        ", entNo=" + entNo +
        ", entOldNo=" + entOldNo +
        ", entType=" + entType +
        ", entName=" + entName +
        ", shortName=" + shortName +
        ", province=" + province +
        ", city=" + city +
        ", district=" + district +
        ", address=" + address +
        ", zip=" + zip +
        ", contactPerson=" + contactPerson +
        ", phone=" + phone +
        ", fax=" + fax +
        ", email=" + email +
        ", licenceNo=" + licenceNo +
        ", legalPerson=" + legalPerson +
        ", fundAmount=" + fundAmount +
        ", economyKind=" + economyKind +
        ", isAgreement=" + isAgreement +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", entImg=" + entImg +
        ", entLogo=" + entLogo +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", isOpen=" + isOpen +
        ", closeReason=" + closeReason +
        ", status=" + status +
        ", photoType=" + photoType +
        ", isReader=" + isReader +
        ", isManual=" + isManual +
        ", isOpenRead=" + isOpenRead +
        ", tipsFlag=" + tipsFlag +
        ", printX=" + printX +
        ", printY=" + printY +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", bankNo=" + bankNo +
        ", acctNo=" + acctNo +
        ", scoreValue=" + scoreValue +
        ", totalScoreNum=" + totalScoreNum +
        ", goodScoreNum=" + goodScoreNum +
        ", syncFlag=" + syncFlag +
        ", maxStation=" + maxStation +
        ", backPrintX=" + backPrintX +
        ", backPrintY=" + backPrintY +
        ", balanceAmt=" + balanceAmt +
        ", printerType=" + printerType +
        ", permitFixNum=" + permitFixNum +
        ", pemitFixFlag=" + pemitFixFlag +
        ", permitFixUsed=" + permitFixUsed +
        ", ycxcEntId=" + ycxcEntId +
        ", syncPlatEditFlag=" + syncPlatEditFlag +
        ", syncPlatDelFlag=" + syncPlatDelFlag +
        ", syncPlatNewFlag=" + syncPlatNewFlag +
        ", isFixCheck=" + isFixCheck +
        ", dsId=" + dsId +
        ", dsidProvince=" + dsidProvince +
        ", isUpgrade=" + isUpgrade +
        ", isArchives=" + isArchives +
        ", dcId=" + dcId +
        ", certPrefix=" + certPrefix +
        "}";
    }
}
