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
 * @since 2019-07-19
 */
public class Fix implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fix_id", type = IdType.AUTO)
    private Integer fixId;

    @TableField("vehicle_id")
    private Integer vehicleId;

    @TableField("ent_id")
    private Integer entId;

    @TableField("work_station_id")
    private Integer workStationId;

    @TableField("fix_rec_id")
    private Integer fixRecId;

    @TableField("order_id")
    private Integer orderId;

    /**
     * YYYMMDD+企业ID+序号
     */
    @TableField("fix_no")
    private String fixNo;

    @TableField("start_date")
    private LocalDateTime startDate;

    @TableField("end_date")
    private LocalDateTime endDate;

    @TableField("engine_no")
    private String engineNo;

    @TableField("underpan_no")
    private String underpanNo;

    @TableField("deliver_name")
    private String deliverName;

    @TableField("old_fix_no")
    private String oldFixNo;

    @TableField("old_fix_id")
    private String oldFixId;

    @TableField("fix_amt")
    private BigDecimal fixAmt;

    @TableField("inspect_ent_id")
    private Integer inspectEntId;

    @TableField("fix_charge_name")
    private String fixChargeName;

    @TableField("inspector_name")
    private String inspectorName;

    /**
     * [TWO:2-二级维护]
     */
    @TableField("fix_type")
    private Integer fixType;

    @TableField("fix_finish_no")
    private String fixFinishNo;

    @TableField("mileage")
    private Integer mileage;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_user")
    private Integer createUser;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("is_visa")
    private Integer isVisa;

    @TableField("inspect_no")
    private String inspectNo;

    /**
     * [NOT_RECORD:10-未备案; STOP:15-作业中断; RECORD_NOT_INSPECT:30-已备案未检测; INSPECTED:50-已检测]
     */
    @TableField("status")
    private Integer status;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("inspect_unlock")
    private Integer inspectUnlock;

    @TableField("finished_step")
    private Integer finishedStep;

    /**
     * [AUTO:1-自动; HAND:2-手动]
     */
    @TableField("photo_type")
    private Integer photoType;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("is_from_order")
    private Integer isFromOrder;

    @TableField("fix_info_no")
    private String fixInfoNo;

    @TableField("file_flag")
    private Integer fileFlag;

    /**
     * [NO:0-??; YES:1-??]
     */
    @TableField("sync_ys_new_flag")
    private Integer syncYsNewFlag;

    /**
     * [NO:0-??; YES:1-??]
     */
    @TableField("sync_ys_edit_flag")
    private Integer syncYsEditFlag;

    /**
     * [NO:0-否; YES:1-是]
     */
    @TableField("sync_ys_del_flag")
    private Integer syncYsDelFlag;

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

    public Integer getFixId() {
        return fixId;
    }

    public void setFixId(Integer fixId) {
        this.fixId = fixId;
    }
    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }
    public Integer getWorkStationId() {
        return workStationId;
    }

    public void setWorkStationId(Integer workStationId) {
        this.workStationId = workStationId;
    }
    public Integer getFixRecId() {
        return fixRecId;
    }

    public void setFixRecId(Integer fixRecId) {
        this.fixRecId = fixRecId;
    }
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getFixNo() {
        return fixNo;
    }

    public void setFixNo(String fixNo) {
        this.fixNo = fixNo;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }
    public String getUnderpanNo() {
        return underpanNo;
    }

    public void setUnderpanNo(String underpanNo) {
        this.underpanNo = underpanNo;
    }
    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }
    public String getOldFixNo() {
        return oldFixNo;
    }

    public void setOldFixNo(String oldFixNo) {
        this.oldFixNo = oldFixNo;
    }
    public String getOldFixId() {
        return oldFixId;
    }

    public void setOldFixId(String oldFixId) {
        this.oldFixId = oldFixId;
    }
    public BigDecimal getFixAmt() {
        return fixAmt;
    }

    public void setFixAmt(BigDecimal fixAmt) {
        this.fixAmt = fixAmt;
    }
    public Integer getInspectEntId() {
        return inspectEntId;
    }

    public void setInspectEntId(Integer inspectEntId) {
        this.inspectEntId = inspectEntId;
    }
    public String getFixChargeName() {
        return fixChargeName;
    }

    public void setFixChargeName(String fixChargeName) {
        this.fixChargeName = fixChargeName;
    }
    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }
    public Integer getFixType() {
        return fixType;
    }

    public void setFixType(Integer fixType) {
        this.fixType = fixType;
    }
    public String getFixFinishNo() {
        return fixFinishNo;
    }

    public void setFixFinishNo(String fixFinishNo) {
        this.fixFinishNo = fixFinishNo;
    }
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
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
    public Integer getIsVisa() {
        return isVisa;
    }

    public void setIsVisa(Integer isVisa) {
        this.isVisa = isVisa;
    }
    public String getInspectNo() {
        return inspectNo;
    }

    public void setInspectNo(String inspectNo) {
        this.inspectNo = inspectNo;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getInspectUnlock() {
        return inspectUnlock;
    }

    public void setInspectUnlock(Integer inspectUnlock) {
        this.inspectUnlock = inspectUnlock;
    }
    public Integer getFinishedStep() {
        return finishedStep;
    }

    public void setFinishedStep(Integer finishedStep) {
        this.finishedStep = finishedStep;
    }
    public Integer getPhotoType() {
        return photoType;
    }

    public void setPhotoType(Integer photoType) {
        this.photoType = photoType;
    }
    public Integer getIsFromOrder() {
        return isFromOrder;
    }

    public void setIsFromOrder(Integer isFromOrder) {
        this.isFromOrder = isFromOrder;
    }
    public String getFixInfoNo() {
        return fixInfoNo;
    }

    public void setFixInfoNo(String fixInfoNo) {
        this.fixInfoNo = fixInfoNo;
    }
    public Integer getFileFlag() {
        return fileFlag;
    }

    public void setFileFlag(Integer fileFlag) {
        this.fileFlag = fileFlag;
    }
    public Integer getSyncYsNewFlag() {
        return syncYsNewFlag;
    }

    public void setSyncYsNewFlag(Integer syncYsNewFlag) {
        this.syncYsNewFlag = syncYsNewFlag;
    }
    public Integer getSyncYsEditFlag() {
        return syncYsEditFlag;
    }

    public void setSyncYsEditFlag(Integer syncYsEditFlag) {
        this.syncYsEditFlag = syncYsEditFlag;
    }
    public Integer getSyncYsDelFlag() {
        return syncYsDelFlag;
    }

    public void setSyncYsDelFlag(Integer syncYsDelFlag) {
        this.syncYsDelFlag = syncYsDelFlag;
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

    @Override
    public String toString() {
        return "Fix{" +
        "fixId=" + fixId +
        ", vehicleId=" + vehicleId +
        ", entId=" + entId +
        ", workStationId=" + workStationId +
        ", fixRecId=" + fixRecId +
        ", orderId=" + orderId +
        ", fixNo=" + fixNo +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", engineNo=" + engineNo +
        ", underpanNo=" + underpanNo +
        ", deliverName=" + deliverName +
        ", oldFixNo=" + oldFixNo +
        ", oldFixId=" + oldFixId +
        ", fixAmt=" + fixAmt +
        ", inspectEntId=" + inspectEntId +
        ", fixChargeName=" + fixChargeName +
        ", inspectorName=" + inspectorName +
        ", fixType=" + fixType +
        ", fixFinishNo=" + fixFinishNo +
        ", mileage=" + mileage +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", isVisa=" + isVisa +
        ", inspectNo=" + inspectNo +
        ", status=" + status +
        ", inspectUnlock=" + inspectUnlock +
        ", finishedStep=" + finishedStep +
        ", photoType=" + photoType +
        ", isFromOrder=" + isFromOrder +
        ", fixInfoNo=" + fixInfoNo +
        ", fileFlag=" + fileFlag +
        ", syncYsNewFlag=" + syncYsNewFlag +
        ", syncYsEditFlag=" + syncYsEditFlag +
        ", syncYsDelFlag=" + syncYsDelFlag +
        ", syncPlatEditFlag=" + syncPlatEditFlag +
        ", syncPlatDelFlag=" + syncPlatDelFlag +
        ", syncPlatNewFlag=" + syncPlatNewFlag +
        "}";
    }
}
