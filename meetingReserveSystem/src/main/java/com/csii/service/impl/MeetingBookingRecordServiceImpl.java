package com.csii.service.impl;

import com.csii.entity.MeetingBookingRecord;
import com.csii.mapper.MeetingBookingRecordMapper;
import com.csii.service.AsyncService;
import com.csii.service.MeetingBookingRecordService;

import com.csii.util.RandomStringGeneratorUtil;
import com.csii.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会议室预定的service实现类
 * @author yhx
 * @version 1.0
 * createTime 2023/7/27
 */

@Service
public class MeetingBookingRecordServiceImpl implements MeetingBookingRecordService {

    @Autowired
    MeetingBookingRecordMapper meetingBookingRecordMapper;

    @Autowired
    AsyncService asyncService = new AsyncServiceImpl();

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 追加多条会议室预约记录
     * @param reqVO 预约会议室的信息对象
     * @return >0:增加成功
     */
    @Override
    @Transactional
    public String addBookRecords(AddBookRecordReqVO reqVO) {

        //判断时间是否交叉
        String timeErr = null;
        Calendar calendar = Calendar.getInstance();
        // 将时间设置为00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        java.util.Date date = calendar.getTime();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MeetingBookingRecordVo> meetingBookingRecordVoList = meetingBookingRecordMapper.selectAllRecords(timestamp);
        List<AddBookRecordReqVO> addBookRecordReqVOList = new ArrayList<>();

        //预约成功后的验证码
        String randomString = RandomStringGeneratorUtil.generateRandom();
        reqVO.setRandomString(randomString);
        //保留第一天时间
        Timestamp startDay = reqVO.getStartTime();
        //计算最后一天
        Timestamp endDay = reqVO.getEndTime();
        //多条插入
        while (reqVO.getStartTime().getTime() <= endDay.getTime()) {
            long reEndTime = reqVO.getStartTime().getTime() + (23 * 60 * 60 * 1000);
            long reStartTime = reqVO.getStartTime().getTime() + (24 * 60 * 60 * 1000);
            reqVO.setEndTime(new Timestamp(reEndTime));
            for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordVoList) {
                if (meetingBookingRecordVo.getMeetingRoom().equals(reqVO.getMeetingRoom())) {
                    try {
                        Date startDate = sdf.parse(meetingBookingRecordVo.getStartTime());
                        Timestamp startTimeStamp = new Timestamp(startDate.getTime());
                        Date endDate = sdf.parse(meetingBookingRecordVo.getEndTime());
                        Timestamp endTimeStamp = new Timestamp(endDate.getTime());
                        //判断预约时间是否冲突
                        if (reqVO.getStartTime().getTime() >= startTimeStamp.getTime() && reqVO.getStartTime().getTime() <= endTimeStamp.getTime()
                                || reqVO.getEndTime().getTime() >= startTimeStamp.getTime() && reqVO.getEndTime().getTime() <= endTimeStamp.getTime()
                                || reqVO.getStartTime().getTime() <= startTimeStamp.getTime() && reqVO.getEndTime().getTime() >= endTimeStamp.getTime()
                        ){
                            timeErr = "timeError";
                            return timeErr;
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            meetingBookingRecordMapper.insertRecord(reqVO);
            addBookRecordReqVOList.add(reqVO);
            reqVO.setStartTime(new Timestamp(reStartTime));
        }

        reqVO.setStartTime(new Timestamp(startDay.getTime()));
        reqVO.setEndTime(new Timestamp(endDay.getTime() + (23 * 60 * 60 * 1000)));
        asyncService.sendEmail1(reqVO);
        return randomString;
    }

    /**
     * 查询今天开始预约记录
     * @return 返回七天内的会议室预约记录集合
     */
    @Override
    @Transactional
    public ListInfoVO selectAllRecords(){
        ListInfoVO list = new ListInfoVO();
        List<List<ReturnMeetingBookingRecordVo>> listData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        // 将时间设置为00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        java.util.Date date = calendar.getTime();
        Timestamp timestamp = new Timestamp(date.getTime());
        List<MeetingBookingRecordVo> meetingBookingRecordVoList = meetingBookingRecordMapper.selectAllRecords(timestamp);
        List<ReturnMeetingBookingRecordVo> meetingRoom2 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom3 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom6 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom7 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom8 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom9 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> tempRoom1 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> tempRoom2 = new ArrayList<ReturnMeetingBookingRecordVo>();
        for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordVoList) {
            ReturnMeetingBookingRecordVo returnMeetingBookingRecordVo = new ReturnMeetingBookingRecordVo();
            returnMeetingBookingRecordVo.setRecordId(meetingBookingRecordVo.getRecordId());
            returnMeetingBookingRecordVo.setMeetingRoom(meetingBookingRecordVo.getMeetingRoom());
            returnMeetingBookingRecordVo.setMeetingTheme(meetingBookingRecordVo.getMeetingTheme());
            returnMeetingBookingRecordVo.setBookerName(meetingBookingRecordVo.getBookerName());
            returnMeetingBookingRecordVo.setBookerEmail(meetingBookingRecordVo.getBookerEmail());
            returnMeetingBookingRecordVo.setCommitTime(meetingBookingRecordVo.getCommitTime());
            returnMeetingBookingRecordVo.setCancelTime(meetingBookingRecordVo.getCancelTime());
            returnMeetingBookingRecordVo.setBookingStatus(meetingBookingRecordVo.getBookingStatus());
            returnMeetingBookingRecordVo.setMemberPro(meetingBookingRecordVo.getMemberPro());
            String[] start = meetingBookingRecordVo.getStartTime().split(" ");
            String[] end = meetingBookingRecordVo.getEndTime().split(" ");
            returnMeetingBookingRecordVo.setDate(start[0]);
            returnMeetingBookingRecordVo.setStartEndTime(start[1] + "-" + end[1]);
            if("会议室2".equals(meetingBookingRecordVo.getMeetingRoom())){
                meetingRoom2.add(returnMeetingBookingRecordVo);
            } else if ("会议室3".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom3.add(returnMeetingBookingRecordVo);
            }else if ("会议室6".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom6.add(returnMeetingBookingRecordVo);
            } else if ("会议室7".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom7.add(returnMeetingBookingRecordVo);
            } else if ("会议室8".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom8.add(returnMeetingBookingRecordVo);
            }else if ("会议室9".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom9.add(returnMeetingBookingRecordVo);
            }else if ("临时会议室1".equals(meetingBookingRecordVo.getMeetingRoom())) {
                tempRoom1.add(returnMeetingBookingRecordVo);
            }else {
                tempRoom2.add(returnMeetingBookingRecordVo);
            }
        }
        listData.add(meetingRoom2);
        listData.add(meetingRoom3);
        listData.add(meetingRoom6);
        listData.add(meetingRoom7);
        listData.add(meetingRoom8);
        listData.add(meetingRoom9);
        listData.add(tempRoom1);
        listData.add(tempRoom2);
        list.setDataList(Collections.singletonList(listData));
        return list;
    }

    /**
     * 默认查询七天内的会议室预约记录
     * @return 返回七天内的会议室预约记录集合
     */
    @Override
    @Transactional
    public ListInfoVO selectRecentRecords() {
        ListInfoVO list = new ListInfoVO();
        List<List<ReturnMeetingBookingRecordVo>> listData = new ArrayList<>();
        List<MeetingBookingRecordVo> meetingBookingRecordVoList = meetingBookingRecordMapper.selectRecentRecords();
        List<ReturnMeetingBookingRecordVo> meetingRoom2 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom3 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom6 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom7 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom8 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom9 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> tempRoom1 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> tempRoom2 = new ArrayList<ReturnMeetingBookingRecordVo>();
        for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordVoList) {
            ReturnMeetingBookingRecordVo returnMeetingBookingRecordVo = new ReturnMeetingBookingRecordVo();
            returnMeetingBookingRecordVo.setRecordId(meetingBookingRecordVo.getRecordId());
            returnMeetingBookingRecordVo.setMeetingRoom(meetingBookingRecordVo.getMeetingRoom());
            returnMeetingBookingRecordVo.setMeetingTheme(meetingBookingRecordVo.getMeetingTheme());
            returnMeetingBookingRecordVo.setBookerName(meetingBookingRecordVo.getBookerName());
            returnMeetingBookingRecordVo.setBookerEmail(meetingBookingRecordVo.getBookerEmail());
            returnMeetingBookingRecordVo.setCommitTime(meetingBookingRecordVo.getCommitTime());
            returnMeetingBookingRecordVo.setCancelTime(meetingBookingRecordVo.getCancelTime());
            returnMeetingBookingRecordVo.setBookingStatus(meetingBookingRecordVo.getBookingStatus());
            returnMeetingBookingRecordVo.setMemberPro(meetingBookingRecordVo.getMemberPro());
            String[] start = meetingBookingRecordVo.getStartTime().split(" ");
            String[] end = meetingBookingRecordVo.getEndTime().split(" ");
            returnMeetingBookingRecordVo.setDate(start[0]);
            returnMeetingBookingRecordVo.setStartEndTime(start[1] + "-" + end[1]);
            if("会议室2".equals(meetingBookingRecordVo.getMeetingRoom())){
                meetingRoom2.add(returnMeetingBookingRecordVo);
            } else if ("会议室3".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom3.add(returnMeetingBookingRecordVo);
            }else if ("会议室6".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom6.add(returnMeetingBookingRecordVo);
            }else if ("会议室7".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom7.add(returnMeetingBookingRecordVo);
            }else if ("会议室8".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom8.add(returnMeetingBookingRecordVo);
            }else if ("会议室9".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom9.add(returnMeetingBookingRecordVo);
            }else if ("临时会议室1".equals(meetingBookingRecordVo.getMeetingRoom())) {
                tempRoom1.add(returnMeetingBookingRecordVo);
            }else {
                tempRoom2.add(returnMeetingBookingRecordVo);
            }
        }
        listData.add(meetingRoom2);
        listData.add(meetingRoom3);
        listData.add(meetingRoom6);
        listData.add(meetingRoom7);
        listData.add(meetingRoom8);
        listData.add(meetingRoom9);
        listData.add(tempRoom1);
        listData.add(tempRoom2);
        list.setDataList(Collections.singletonList(listData));
        return list;
    }

    /**
     * 根据条件查询会议室预约记录
     * @param reqVO 查询预约记录请求报文
     * @return 返回查询到的会议室预约记录集合
     */
    @Override
    @Transactional
    public ListInfoVO selectRecords(SearchBookRecordsReqVO reqVO) {
        ListInfoVO list = new ListInfoVO();
        List<List<ReturnMeetingBookingRecordVo>> listData = new ArrayList<>();
        List<MeetingBookingRecordVo> meetingBookingRecordVoList = meetingBookingRecordMapper.selectRecords(reqVO);
        List<ReturnMeetingBookingRecordVo> meetingRoom2 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom3 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom6 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom7 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom8 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> meetingRoom9 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> tempRoom1 = new ArrayList<ReturnMeetingBookingRecordVo>();
        List<ReturnMeetingBookingRecordVo> tempRoom2 = new ArrayList<ReturnMeetingBookingRecordVo>();
        for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordVoList) {
            ReturnMeetingBookingRecordVo returnMeetingBookingRecordVo = new ReturnMeetingBookingRecordVo();
            returnMeetingBookingRecordVo.setRecordId(meetingBookingRecordVo.getRecordId());
            returnMeetingBookingRecordVo.setMeetingRoom(meetingBookingRecordVo.getMeetingRoom());
            returnMeetingBookingRecordVo.setMeetingTheme(meetingBookingRecordVo.getMeetingTheme());
            returnMeetingBookingRecordVo.setBookerName(meetingBookingRecordVo.getBookerName());
            returnMeetingBookingRecordVo.setBookerEmail(meetingBookingRecordVo.getBookerEmail());
            returnMeetingBookingRecordVo.setCommitTime(meetingBookingRecordVo.getCommitTime());
            returnMeetingBookingRecordVo.setCancelTime(meetingBookingRecordVo.getCancelTime());
            returnMeetingBookingRecordVo.setBookingStatus(meetingBookingRecordVo.getBookingStatus());
            returnMeetingBookingRecordVo.setMemberPro(meetingBookingRecordVo.getMemberPro());
            String[] start = meetingBookingRecordVo.getStartTime().split(" ");
            String[] end = meetingBookingRecordVo.getEndTime().split(" ");
            returnMeetingBookingRecordVo.setDate(start[0]);
            returnMeetingBookingRecordVo.setStartEndTime(start[1] + "-" + end[1]);
            if("会议室2".equals(meetingBookingRecordVo.getMeetingRoom())){
                meetingRoom2.add(returnMeetingBookingRecordVo);
            }else if ("会议室3".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom3.add(returnMeetingBookingRecordVo);
            }else if ("会议室6".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom6.add(returnMeetingBookingRecordVo);
            }else if ("会议室7".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom7.add(returnMeetingBookingRecordVo);
            }else if ("会议室8".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom8.add(returnMeetingBookingRecordVo);
            }else if ("会议室9".equals(meetingBookingRecordVo.getMeetingRoom())) {
                meetingRoom9.add(returnMeetingBookingRecordVo);
            }else if ("临时会议室1".equals(meetingBookingRecordVo.getMeetingRoom())) {
                tempRoom1.add(returnMeetingBookingRecordVo);
            }else {
                tempRoom2.add(returnMeetingBookingRecordVo);
            }
        }
        listData.add(meetingRoom2);
        listData.add(meetingRoom3);
        listData.add(meetingRoom6);
        listData.add(meetingRoom7);
        listData.add(meetingRoom8);
        listData.add(meetingRoom9);
        listData.add(tempRoom1);
        listData.add(tempRoom2);
        list.setDataList(Collections.singletonList(listData));
        return list;
    }

    /**
     * 追加一条会议室预约记录
     * @param reqVO 预约会议室的信息对象
     * @return >0:增加成功
     */
    @Override
    @Transactional
    public String addBookRecord(AddBookRecordReqVO reqVO) {
        String randomString = RandomStringGeneratorUtil.generateRandom();
        reqVO.setRandomString(randomString);
        //判断时间是否交叉
        String timeErr = null;
        Calendar calendar = Calendar.getInstance();
        // 将时间设置为00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        java.util.Date date = calendar.getTime();
        Timestamp timestamp = new Timestamp(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MeetingBookingRecordVo> meetingBookingRecordVoList = meetingBookingRecordMapper.selectAllRecords(timestamp);
        for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordVoList) {
            if (meetingBookingRecordVo.getMeetingRoom().equals(reqVO.getMeetingRoom())) {
                try {
                    Date startDate = sdf.parse(meetingBookingRecordVo.getStartTime());
                    Timestamp startTimeStamp = new Timestamp(startDate.getTime());
                    Date endDate = sdf.parse(meetingBookingRecordVo.getEndTime());
                    Timestamp endTimeStamp = new Timestamp(endDate.getTime());
                    //判断预约时间是否冲突
                    if (reqVO.getStartTime().getTime() >= startTimeStamp.getTime() && reqVO.getStartTime().getTime() <= endTimeStamp.getTime()
                    || reqVO.getEndTime().getTime() >= startTimeStamp.getTime() && reqVO.getEndTime().getTime() <= endTimeStamp.getTime()
                    || reqVO.getStartTime().getTime() <= startTimeStamp.getTime() && reqVO.getEndTime().getTime() >= endTimeStamp.getTime()
                    ){
                        timeErr = "timeError";
                        return timeErr;
                    }
                    //判断是否紧邻上一个会议
                    long diff = reqVO.getStartTime().getTime() - endTimeStamp.getTime();
                    if (diff <= 60000 && diff > 0) {
                        asyncService.sendEmail5(meetingBookingRecordVo);
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(meetingBookingRecordMapper.insertRecord(reqVO) > 0){
            asyncService.sendEmail1(reqVO);
            return randomString;
        }
        return null;
    }

    /**
     * 根据recordId取消预约
     * @return >0:取消中，待审批
     */
    @Override
    @Transactional
    public boolean cancelBookRecord(IdAndRandomVO reqVO) {
        //根据id查询取消预约信息
        MeetingBookingRecord meetingRecord = meetingBookingRecordMapper.selectCancelInfoFromRecordId(reqVO.getRecordId());

        //判断验证码是否正确
        if (meetingRecord.getRandomString().equals(reqVO.getRandomString())) {
            if (meetingBookingRecordMapper.cancel(reqVO.getRecordId()) > 0) {
                asyncService.sendEmail2(reqVO);
                return true;
            }
            return false;
        }return false;
    }

    /**
     * 查询全部取消会议室预约记录
     * @return 返回全部的取消会议室信息列表
     */
    @Override
    @Transactional
    public ListInfoVO selectAllCancelRecords() {
        ListInfoVO list = new ListInfoVO();
        List<MeetingBookingRecordVo> meetingBookingRecordList = meetingBookingRecordMapper.selectAllCancelRecords();
        List<ReturnCancelRecordVo> returnCancelRecordList = new ArrayList<ReturnCancelRecordVo>();
        for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordList){
            ReturnCancelRecordVo returnCancelRecordVo = new ReturnCancelRecordVo();
            returnCancelRecordVo.setRecordId(meetingBookingRecordVo.getRecordId());
            returnCancelRecordVo.setBookingStatus(meetingBookingRecordVo.getBookingStatus());
            returnCancelRecordVo.setBookerName(meetingBookingRecordVo.getBookerName());
            returnCancelRecordVo.setBookerEmail(meetingBookingRecordVo.getBookerEmail());
            returnCancelRecordVo.setMeetingRoom(meetingBookingRecordVo.getMeetingRoom());
            returnCancelRecordVo.setMeetingTheme(meetingBookingRecordVo.getMeetingTheme());
            returnCancelRecordVo.setCommitTime(meetingBookingRecordVo.getCommitTime());
            returnCancelRecordVo.setCancelTime(meetingBookingRecordVo.getCancelTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                long start = sdf.parse(meetingBookingRecordVo.getStartTime()).getTime();
                long end = sdf.parse(meetingBookingRecordVo.getEndTime()).getTime();
                returnCancelRecordVo.setDuringTime(String.valueOf((end - start)/(1000*60)) + "分钟");
                String[] strs = meetingBookingRecordVo.getEndTime().split(" ");
                returnCancelRecordVo.setMeetingTime(meetingBookingRecordVo.getStartTime()+"-"+strs[1]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            returnCancelRecordList.add(returnCancelRecordVo);
        }
        list.setDataList(Collections.singletonList(returnCancelRecordList));
        return list;
    }

    /**
     * 根据条件查询取消会议室预约记录
     * @param reqVO 请求报文
     * @return 根据条件查询的取消预约会议室信息列表
     */
    @Override
    @Transactional
    public ListInfoVO selectCancelRecords(CancelRecordsReqVO reqVO) {
        ListInfoVO list = new ListInfoVO();
        List<MeetingBookingRecordVo> meetingBookingRecordList = meetingBookingRecordMapper.selectCancelRecords(reqVO);
        List<ReturnCancelRecordVo> returnCancelRecordList = new ArrayList<ReturnCancelRecordVo>();
        for (MeetingBookingRecordVo meetingBookingRecordVo: meetingBookingRecordList){
            ReturnCancelRecordVo returnCancelRecordVo = new ReturnCancelRecordVo();
            returnCancelRecordVo.setRecordId(meetingBookingRecordVo.getRecordId());
            returnCancelRecordVo.setBookingStatus(meetingBookingRecordVo.getBookingStatus());
            returnCancelRecordVo.setBookerName(meetingBookingRecordVo.getBookerName());
            returnCancelRecordVo.setBookerEmail(meetingBookingRecordVo.getBookerEmail());
            returnCancelRecordVo.setMeetingRoom(meetingBookingRecordVo.getMeetingRoom());
            returnCancelRecordVo.setMeetingTheme(meetingBookingRecordVo.getMeetingTheme());
            returnCancelRecordVo.setCommitTime(meetingBookingRecordVo.getCommitTime());
            returnCancelRecordVo.setCancelTime(meetingBookingRecordVo.getCancelTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                long start = sdf.parse(meetingBookingRecordVo.getStartTime()).getTime();
                long end = sdf.parse(meetingBookingRecordVo.getEndTime()).getTime();
                returnCancelRecordVo.setDuringTime(String.valueOf((end - start)/(1000*60)) + "分钟");
                String[] strs = meetingBookingRecordVo.getEndTime().split(" ");
                returnCancelRecordVo.setMeetingTime(meetingBookingRecordVo.getStartTime()+"-"+strs[1]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            returnCancelRecordList.add(returnCancelRecordVo);
        }
        list.setDataList(Collections.singletonList(returnCancelRecordList));
        return list;
    }

    /**
     * 删除全部取消预约记录
     * @return >0:删除成功
     */
    @Override
    @Transactional
    public boolean deleteAllCancelRecords() {
        List<MeetingBookingRecordVo> meetingBookingRecordList = meetingBookingRecordMapper.selectAllCancelRecords();
        asyncService.sendEmail3(meetingBookingRecordList);
        return (meetingBookingRecordMapper.deleteAllCancelRecords() != 0);
    }

    /**
     * 根据条件删除预约记录
     * @param reqVO 删除条件请求报文
     * @return >0:删除成功
     */
    @Override
    @Transactional
    public boolean deleteCancelRecords(CancelRecordsReqVO reqVO) {
        List<MeetingBookingRecordVo> meetingBookingRecordList = meetingBookingRecordMapper.selectCancelRecords(reqVO);
        asyncService.sendEmail4(meetingBookingRecordList);
        return (meetingBookingRecordMapper.deleteCancelRecords(reqVO) != 0);
    }

    /**
     * 修改邮箱并发送新邮件
     * @return 修改成功/失败
     */
    @Override
    @Transactional
    public boolean updateEmailAndRandom(MeetingBookingRecord meetingBookingRecord){
        System.out.println(1);
        meetingBookingRecord.setRandomString(RandomStringGeneratorUtil.generateRandom());
        System.out.println(meetingBookingRecordMapper.updateEmailAndRandom(meetingBookingRecord));
        MeetingBookingRecord meet = meetingBookingRecordMapper.selectCancelInfoFromRecordId(meetingBookingRecord.getRecordId());
        AddBookRecordReqVO addBookRecordReqVO = new AddBookRecordReqVO();
        addBookRecordReqVO.setRandomString(meet.getRandomString());
        addBookRecordReqVO.setMeetingRoom(meet.getMeetingRoom());
        addBookRecordReqVO.setBookerEmail(meet.getBookerEmail());
        addBookRecordReqVO.setBookerName(meet.getBookerName());
        addBookRecordReqVO.setEndTime(meet.getEndTime());
        addBookRecordReqVO.setStartTime(meet.getStartTime());
        asyncService.sendEmail1(addBookRecordReqVO);
        System.out.println(meet.getBookerEmail());
        return true;
    }
}
