package com.csii.vo;

import java.sql.Timestamp;

/**
 * 查询预约记录请求报文
 * @createDate 2023-07-24
 */
public class SearchBookRecordsReqVO {
    /**
     * 预约起始时间
     */
    private Timestamp startTime;
    /**
     * 预约结束时间
     */
    private Timestamp endTime;
    /**
     * 会议室名称
     */
    private String meetingRoom;

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

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
}
