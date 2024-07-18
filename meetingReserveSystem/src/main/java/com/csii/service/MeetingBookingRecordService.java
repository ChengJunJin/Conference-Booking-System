package com.csii.service;

import com.csii.entity.MeetingBookingRecord;
import com.csii.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;


/**
 * 会议室预定的service接口
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

public interface MeetingBookingRecordService {

    /**
     * 增加多条预约记录
     * @return >0:增加成功
     */
    String addBookRecords(AddBookRecordReqVO reqVO);

    /**
     * 查询今天开始预约记录
     * @return 返回今天开始的会议室预约记录集合
     */
    ListInfoVO selectAllRecords();

    /**
     * 查询最近七天内的预约记录
     * @return 返回七天内的会议室预约记录集合
     */
    ListInfoVO selectRecentRecords();

    /**
     * 根据条件查询预约记录
     * @return 返回预约记录集合
     */
    ListInfoVO selectRecords(SearchBookRecordsReqVO reqVO);

    /**
     * 增加预约记录
     * @return >0:增加成功
     */
    String addBookRecord(AddBookRecordReqVO reqVO);

    /**
     * 取消预约
     * @return >0:取消中，待审批
     */
    boolean cancelBookRecord(IdAndRandomVO reqVO);

    /**
     * 查询全部取消预约记录
     * @return 返回全部取消预约记录集合
     */
    ListInfoVO selectAllCancelRecords();

    /**
     * 根据条件查询取消预约记录
     * @param reqVO 请求报文
     * @return 根据条件返回取消预约记录集合
     */
    ListInfoVO selectCancelRecords(CancelRecordsReqVO reqVO);

    /**
     * 删除全部取消预约记录
     * @return >0:删除成功
     */
    boolean deleteAllCancelRecords();

    /**
     * 根据条件删除预约记录
     * @param reqVO 删除条件
     * @return 删除成功/失败
     */
    boolean deleteCancelRecords(CancelRecordsReqVO reqVO);

    /**
     * 修改邮箱并发送新邮件
     * @return 修改成功/失败
     */
    boolean updateEmailAndRandom(MeetingBookingRecord meetingBookingRecord);
}
