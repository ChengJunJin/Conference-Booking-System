package com.csii.vo;

import java.sql.Timestamp;

/**
 * 会议预约记录
 * @createDate 2023-07-23
 */
public class ReturnMeetingBookingRecordVo {
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
     * 会议预约开始日期
     */
    private String date;
    /**
     * 会议预约时间
     */
    private String startEndTime;
    /**
     * 预约人姓名
     */
    private String bookerName;
    /**
     * 重要参会人员
     */
    private String memberPro;
    /**
     * 预约人邮箱
     */
    private String bookerEmail;
    /**
     * 预约提交时间
     */
    private Timestamp commitTime;
    /**
     * 取消预约时间
     */
    private Timestamp cancelTime;
    /**
     * 预约状态
     * 0：已预约；1：取消预约待审批
     */
    private int bookingStatus;

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

    public String getMeetingTheme() {
        return meetingTheme;
    }

    public void setMeetingTheme(String meetingTheme) {
        this.meetingTheme = meetingTheme;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(String startEndTime) {
        this.startEndTime = startEndTime;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public String getMemberPro() {
        return memberPro;
    }

    public void setMemberPro(String memberPro) {
        this.memberPro = memberPro;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(int bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "ReturnMeetingBookingRecordVo{" +
                "recordId=" + recordId +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingTheme='" + meetingTheme + '\'' +
                ", date='" + date + '\'' +
                ", startEndTime='" + startEndTime + '\'' +
                ", bookerName='" + bookerName + '\'' +
                ", memberPro='" + memberPro + '\'' +
                ", bookerEmail='" + bookerEmail + '\'' +
                ", commitTime=" + commitTime +
                ", cancelTime=" + cancelTime +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}
