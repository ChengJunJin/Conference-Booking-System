package com.csii.service.impl;

import com.csii.entity.MeetingBookingRecord;
import com.csii.mapper.MeetingBookingRecordMapper;
import com.csii.service.AsyncService;
import com.csii.util.EmailUtil;
import com.csii.vo.AddBookRecordReqVO;
import com.csii.vo.CancelRecordsReqVO;
import com.csii.vo.IdAndRandomVO;
import com.csii.vo.MeetingBookingRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yhx
 * @version 1.0
 * createTime 2023/9/8
 */

@Service
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    MeetingBookingRecordMapper meetingBookingRecordMapper;
    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    JavaMailSender javaMailSender;

    @Async
    public void sendEmail1(AddBookRecordReqVO reqVO){
        //给申请人发送邮件
        // mailSender是通过spring注入的JavaMailSender实例
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessage mi = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("hys@csii.com.cn");
            messageHelper.setTo(reqVO.getBookerEmail());
            messageHelper.setSubject("会议室预约成功通知");
            String headerText = "您的会议室预约信息如下";
            // htmlContent为上面生成的html内容字符串
            List<AddBookRecordReqVO> list = new ArrayList<AddBookRecordReqVO>();
            list.add(reqVO);
            messageHelper.setText(EmailUtil.createTable3(headerText, list), true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendEmail2(IdAndRandomVO reqVO){
        //根据id查询取消预约信息
        MeetingBookingRecord meetingRecord = meetingBookingRecordMapper.selectCancelInfoFromRecordId(reqVO.getRecordId());
        //获取所有管理员的邮箱
        List<String> managers = meetingBookingRecordMapper.selectManagerEmail();
        for (int i = 0; i < managers.size(); i++) {
            // mailSender是通过spring注入的JavaMailSender实例
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("hys@csii.com.cn");
                messageHelper.setTo(managers.get(i));
                messageHelper.setSubject("申请取消会议预约通知");
                String headerText = "申请取消会议预约列表如下";
                // htmlContent为上面生成的html内容字符串
                List<MeetingBookingRecord> list = new ArrayList<MeetingBookingRecord>();
                list.add(meetingRecord);
                messageHelper.setText(EmailUtil.createTable(headerText, list), true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mailSender.send(mimeMessage);
        }
    }

    @Async
    public void sendEmail3(List<MeetingBookingRecordVo> meetingBookingRecordList){
        //遍历集合，给每个会议室的预订人发送邮件
        for (MeetingBookingRecordVo meetingBookingRecord: meetingBookingRecordList) {
            // mailSender是通过spring注入的JavaMailSender实例
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("hys@csii.com.cn");
                messageHelper.setTo(meetingBookingRecord.getBookerEmail());
                messageHelper.setSubject("取消会议预约申请通过通知");
                String headerText = "您的取消会议预约申请已通过，对应的会议信息如下";
                // htmlContent为上面生成的html内容字符串
                List<MeetingBookingRecordVo> list = new ArrayList<MeetingBookingRecordVo>();
                list.add(meetingBookingRecord);
                messageHelper.setText(EmailUtil.createTable2(headerText, list), true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mailSender.send(mimeMessage);
        }
        //给管理员发送邮件
        //获取所有管理员的邮箱
        List<String> managers = meetingBookingRecordMapper.selectManagerEmail();
        for (int i = 0; i < managers.size(); i++){
            // mailSender是通过spring注入的JavaMailSender实例
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("hys@csii.com.cn");
                messageHelper.setTo(managers.get(i));
                messageHelper.setSubject("取消会议预约申请通过通知");
                String headerText = "取消预约申请通过的会议信息列表如下";
                // htmlContent为上面生成的html内容字符串
                messageHelper.setText(EmailUtil.createTable2(headerText, meetingBookingRecordList), true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mailSender.send(mimeMessage);
        }
    }

    @Async
    public void sendEmail4(List<MeetingBookingRecordVo> meetingBookingRecordList){
        //遍历集合，给每个会议室的预订人发送邮件
        for (MeetingBookingRecordVo meetingBookingRecord: meetingBookingRecordList) {
            // mailSender是通过spring注入的JavaMailSender实例
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("hys@csii.com.cn");
                messageHelper.setTo(meetingBookingRecord.getBookerEmail());
                messageHelper.setSubject("会议取消申请预约通过通知");
                String headerText = "您的会议取消预约申请已通过，对应的会议信息如下";
                // htmlContent为上面生成的html内容字符串
                List<MeetingBookingRecordVo> list = new ArrayList<MeetingBookingRecordVo>();
                list.add(meetingBookingRecord);
                messageHelper.setText(EmailUtil.createTable2(headerText, list), true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mailSender.send(mimeMessage);
        }

        //给管理员发送邮件
        //获取所有管理员的邮箱
        List<String> managers = meetingBookingRecordMapper.selectManagerEmail();
        for (int i = 0; i < managers.size(); i++){
            // mailSender是通过spring注入的JavaMailSender实例
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("hys@csii.com.cn");
                messageHelper.setTo(managers.get(i));
                messageHelper.setSubject("取消会议预约通过通知");
                String headerText = "取消预约申请通过的会议信息列表如下";
                // htmlContent为上面生成的html内容字符串
                messageHelper.setText(EmailUtil.createTable2(headerText, meetingBookingRecordList), true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            mailSender.send(mimeMessage);
        }
    }

    @Async
    public void sendEmail5(MeetingBookingRecordVo meetingBookingRecord) {
        // mailSender是通过spring注入的JavaMailSender实例
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("hys@csii.com.cn");
            messageHelper.setTo(meetingBookingRecord.getBookerEmail());
            messageHelper.setSubject("按时结束会议通知");
            messageHelper.setText("您预约的" + meetingBookingRecord.getStartTime()
                    + " - " + meetingBookingRecord.getEndTime()
                    + "的" + meetingBookingRecord.getMeetingTheme() + "会议在结束之后有新的会议,请不要超时！");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendEmail6(List<AddBookRecordReqVO> reqVO){
        //给申请人发送邮件
        // mailSender是通过spring注入的JavaMailSender实例
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("hys@csii.com.cn");
            messageHelper.setTo(reqVO.get(1).getBookerEmail());
            messageHelper.setSubject("会议室长期预约成功通知");
            String headerText = "您的会议室预约信息如下";
            // htmlContent为上面生成的html内容字符串
            messageHelper.setText(EmailUtil.createTable3(headerText, reqVO), true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(mimeMessage);
    }
}
