package com.csii.vo;

import java.sql.Timestamp;
import java.util.List;

/**
 * 取消预约请求头信息
 */
public class CancelRecordsReqVO extends PageInfoReqVO{
    /**
     * 预约起始时间
     */
    private Timestamp startTime;

    /**
     * 预约结束时间
     */
    private Timestamp endTime;

    /**
     * 会议室
     */
    private String meetingRoom;
    /**
     * 预约id列表
     */
    private List<Integer> recordIdList;

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

    public List<Integer> getRecordIdList() {
        return recordIdList;
    }

    public void setRecordIdList(List<Integer> recordIdList) {
        this.recordIdList = recordIdList;
    }

    @Override
    public String toString() {
        return "CancelRecordsReqVO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", meetingRoom='" + meetingRoom + '\'' +
                ", recordIdList=" + recordIdList +
                '}';
    }
}
