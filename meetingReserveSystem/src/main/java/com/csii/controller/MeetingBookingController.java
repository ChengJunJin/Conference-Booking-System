package com.csii.controller;

import com.csii.entity.MeetingBookingRecord;
import com.csii.service.MeetingBookingRecordService;
import com.csii.service.impl.MeetingBookingRecordServiceImpl;
import com.csii.vo.*;
import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

@CrossOrigin
@RestController
public class MeetingBookingController {

    /**
     * 追加多条会议室预约记录
     * @param reqVO 预约会议室信息
     * @return 追加成功/失败
     */
    @PostMapping("addBookRecords")
    public String addBookRecords(@Param("reqVO")AddBookRecordReqVO reqVO) {
        return meetingBookingRecordService.addBookRecords(reqVO);
    }

    /**
     * 更改接收参数的时间格式
     * @param binder binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    private MeetingBookingRecordService meetingBookingRecordService = new MeetingBookingRecordServiceImpl();

    /**
     * 默认查询今天开始的会议室预约记录
     * @return 返回今天开始的会议室预约记录集合
     */
    @PostMapping("getAllBookRecords")
    public ListInfoVO getAllBookRecords(){
        return meetingBookingRecordService.selectAllRecords();
    }

    /**
     * 默认查询七天内的会议室预约记录
     * @return 返回七天内的会议室预约记录集合
     */
    @PostMapping("getRecentBookRecords")
    public ListInfoVO getRecentBookRecords(){
        return meetingBookingRecordService.selectRecentRecords();
    }

    /**
     * 根据条件查询会议室预约记录
     * @param reqVO 查询预约记录请求报文
     * @return 返回查询到的会议室预约记录集合
     */
    @PostMapping("getBookRecords")
    public ListInfoVO getBookRecords(@Param("reqVO")SearchBookRecordsReqVO reqVO) {
        return meetingBookingRecordService.selectRecords(reqVO);
    }

    /**
     * 追加一条会议室预约记录
     * @param reqVO 预约会议室信息
     * @return 追加成功/失败
     */
    @PostMapping("addBookRecord")
    public String addBookRecord(@Param("reqVO")AddBookRecordReqVO reqVO) {
        return meetingBookingRecordService.addBookRecord(reqVO);
    }

    /**
     * 根据recordId和随机验证码取消预约
     * @return true:取消成功，待审核
     */
    @PostMapping("cancel")
    public boolean cancel(@Param("reqVO") IdAndRandomVO reqVO) {
        return meetingBookingRecordService.cancelBookRecord(reqVO);
    }

    /**
     * 查询全部取消会议室预约记录
     * @return 返回全部的取消会议室信息列表
     */
    @PostMapping("getAllCancelRecords")
    public ListInfoVO getAllCancelRecords() {
        return meetingBookingRecordService.selectAllCancelRecords();
    }

    /**
     * 根据条件查询取消会议室预约记录
     * @param reqVO 请求报文
     * @return 根据条件查询的取消预约会议室信息列表
     */
    @PostMapping("getCancelRecords")
    public ListInfoVO getCancelRecords(@Param("reqVO")CancelRecordsReqVO reqVO) {
        return meetingBookingRecordService.selectCancelRecords(reqVO);
    }

    /**
     * 一键通过所有取消预约
     * @return 通过成功/失败
     */
    @PostMapping("cancelAllRecords")
    public boolean cancelAllRecords() {
        return meetingBookingRecordService.deleteAllCancelRecords();
    }

    /**
     * 批量审批取消预约申请
     * @param dataList recordId列表
     */
    @PostMapping("cancelRecords")
    public boolean cancelRecords(@RequestBody List<Integer> dataList) {
        CancelRecordsReqVO cancelRecordsReqVO = new CancelRecordsReqVO();
        cancelRecordsReqVO.setRecordIdList(dataList);
        return meetingBookingRecordService.deleteCancelRecords(cancelRecordsReqVO);
    }

    /**
     * 修改邮箱并发送新邮件
     * @return 修改成功/失败
     */
    @PostMapping("updateEmail")
    public boolean updateEmailAndRandom(@Param("reqVO") MeetingBookingRecord meetingBookingRecord) {
        return meetingBookingRecordService.updateEmailAndRandom(meetingBookingRecord);
    }
}
