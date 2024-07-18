package com.csii.vo;

import java.sql.Timestamp;

/**
 * 取消预约信息
 * @createDate 2023-07-23
 */
public class CancelInfoVO {
    /**
     * 预约id
     */
    private int recordId;
    /**
     * 会议室
     */
    private String meetingRoom;
    /**
     * 会议主题
     */
    private String meetingTheme;
    /**
     * 会议预约开始时间
     */
    private Timestamp startTime;
    /**
     * 会议预约结束时间
     */
    private Timestamp endTime;
    /**
     * 预约人姓名
     */
    private String bookerName;
    /**
     * 预约人邮箱
     */
    private String bookerEmail;
    /**
     * 取消预约时间
     */
    private Timestamp cancelTime;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getMeetingTheme() {
        return meetingTheme;
    }

    public void setMeetingTheme(String meetingTheme) {
        this.meetingTheme = meetingTheme;
    }
}
