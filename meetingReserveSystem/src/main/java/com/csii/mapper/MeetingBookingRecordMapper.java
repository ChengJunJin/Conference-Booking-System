package com.csii.mapper;

import com.csii.entity.MeetingBookingRecord;
import com.csii.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 会议室预约信息mapper接口
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

@Mapper
public interface MeetingBookingRecordMapper {

    //查询今天之后的预约记录
    List<MeetingBookingRecordVo> selectAllRecords(@Param("dayTime") Timestamp dayTime);
    /**
     * 默认查询最近的会议预定记录
     * @return 返回七日内的预约记录集合
     */
    List<MeetingBookingRecordVo> selectRecentRecords();

    /**
     * 根据条件查询会议室预约记录
     * @param reqVO 查询预约记录请求报文
     * @return 返回查询到的会议室预约记录集合
     */
    List<MeetingBookingRecordVo> selectRecords(@Param("reqVO") SearchBookRecordsReqVO reqVO);

    /**
     * 添加一条预约记录
     * @param reqVO 预约信息
     * @return 执行sql条数
     */
    int insertRecord(@Param("record") AddBookRecordReqVO reqVO);

    /**
     * 根据recordId取消预约
     * @param recordId 会议室预约记录id
     * @return 执行sql条数
     */
    int cancel(@Param("recordId") Integer recordId);

    /**
     * 查询取消预约的数据总条数
     * @return 取消预约的数据总条数
     */
    int selectCancelRecordCount();

    /**
     * 查询全部取消会议室预约记录
     * @return 返回全部的取消会议室信息列表
     */
    List<MeetingBookingRecordVo> selectAllCancelRecords();

    /**
     * 根据条件查询取消会议室预约记录
     * @param reqVO 请求报文
     * @return 根据条件查询的取消预约会议室信息列表
     */
    List<MeetingBookingRecordVo> selectCancelRecords(@Param("reqVO") CancelRecordsReqVO reqVO);

    /**
     * 删除所有取消预约记录
     * @return 执行sql条数
     */
    int deleteAllCancelRecords();

    /**
     * 根据条件删除取消预约的记录
     * @return 执行sql条数
     */
    int deleteCancelRecords(@Param("reqVO") CancelRecordsReqVO reqVO);

    /**
     * 根据取消预约的会议室预约id查询取消信息
     * @param recordId 预约记录id
     * @return 根据recordId查询出的取消预约会议室信息集合
     */
    MeetingBookingRecord selectCancelInfoFromRecordId(@Param("recordId") Integer recordId);

    /**
     * 查询所有管理员的邮箱
     * @return 管理员的邮箱集合
     */
    List<String> selectManagerEmail();

    /**
     * 修改邮箱并发送新邮件
     * @return 修改成功/失败
     */
    int updateEmailAndRandom(@Param("reqVO") MeetingBookingRecord reqVO);
}
