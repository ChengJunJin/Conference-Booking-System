package com.csii.service;

import com.csii.vo.AddBookRecordReqVO;
import com.csii.vo.CancelRecordsReqVO;
import com.csii.vo.IdAndRandomVO;
import com.csii.vo.MeetingBookingRecordVo;

import java.util.List;

/**
 * @author yhx
 * @version 1.0
 * createTime 2023/9/8
 */

public interface AsyncService {

    void sendEmail1(AddBookRecordReqVO reqVO);
    void sendEmail2(IdAndRandomVO reqVO);
    void sendEmail3(List<MeetingBookingRecordVo> meetingBookingRecordList);
    void sendEmail4(List<MeetingBookingRecordVo> meetingBookingRecordList);
    void sendEmail5(MeetingBookingRecordVo meetingBookingRecord);
    void sendEmail6(List<AddBookRecordReqVO> reqVO);
}
