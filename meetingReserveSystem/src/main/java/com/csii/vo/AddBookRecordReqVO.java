package com.csii.vo;

import java.sql.Timestamp;

/**
 * 预约请求报文
 * @createDate 2023-07-24
 */
public class AddBookRecordReqVO {
    /**
     * 预约起始时间
     */
    private Timestamp startTime;
    /**
     * 预约结束时间
     */
    private Timestamp endTime;
    /**
     * 预约人姓名
     */
    private String bookerName;
    /**
     * 主要参会人员
     */
    private String memberPro;
    /**
     * 会议室名称
     */
    private String meetingRoom;
    /**
     * 会议主题
     */
    private String meetingTheme;
    /**
     * 预约人邮箱
     */
    private String bookerEmail;
    /**
     * 随机验证码
     */
    private String randomString;

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

    public String getMemberPro() {
        return memberPro;
    }

    public void setMemberPro(String memberPro) {
        this.memberPro = memberPro;
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

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    @Override
    public String toString() {
        return "AddBookRecordReqVO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", bookerName='" + bookerName + '\'' +
                ", memberPro='" + memberPro + '\'' +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", meetingTheme='" + meetingTheme + '\'' +
                ", bookerEmail='" + bookerEmail + '\'' +
                ", randomString='" + randomString + '\'' +
                '}';
    }
}
