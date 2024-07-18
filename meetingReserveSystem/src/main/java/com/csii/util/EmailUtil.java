package com.csii.util;

import com.csii.entity.MeetingBookingRecord;
import com.csii.vo.AddBookRecordReqVO;
import com.csii.vo.MeetingBookingRecordVo;

import java.util.List;

/**
 * 发送邮件格式管理工具类
 * @createDate 2023-07-24
 */

public class EmailUtil {

    /**
     * 创建表格
     * @return 发送邮件内容
     */
    public static String createTable(String headerText, List<MeetingBookingRecord> recordList) {
        StringBuilder content = new StringBuilder("<html><head>" + headerText + "</head><body>");
        content.append("<table border=\"1\" style=\"width:1000px; height:150px;border:solid 1px #E8F2F9;font-size=14px;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><td rowspan=\"1\">会议室</td>" +
                "<td colspan=\"1\">会议预约开始时间</td>" +
                "<td colspan=\"1\">会议预约结束时间</td>" +
                "<td colspan=\"1\">预约人姓名</td>" +
                "<td colspan=\"1\">预约人邮箱</td>" +
                "<td colspan=\"1\">预约提交时间</td>" +
                "<td colspan=\"1\">取消预约时间</td>" +
                "</tr>");
        for (MeetingBookingRecord record : recordList) {
            content.append("<tr>" +
                    "<td><span>" + record.getMeetingRoom() + "</span></td>" +
                    "<td><span>" + record.getStartTime() + "</span></td>" +
                    "<td><span>" + record.getEndTime() + "</span></td>" +
                    "<td><span>" + record.getBookerName() + "</span></td>" +
                    "<td><span>" + record.getBookerEmail() + "</span></td>" +
                    "<td><span>" + record.getCommitTime() + "</span></td>" +
                    "<td><span>" + record.getCancelTime() + "</span></td>" +
                    "</tr>");
        }
        content.append("</table>");
        content.append("</body></html>");
        return String.valueOf(content);
    }

    public static String createTable2(String headerText, List<MeetingBookingRecordVo> recordList) {
        StringBuilder content = new StringBuilder("<html><head>" + headerText + "</head><body>");
        content.append("<table border=\"1\" style=\"width:1000px; height:150px;border:solid 1px #E8F2F9;font-size=14px;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><td rowspan=\"1\">会议室</td>" +
                "<td colspan=\"1\">会议预约开始时间</td>" +
                "<td colspan=\"1\">会议预约结束时间</td>" +
                "<td colspan=\"1\">预约人姓名</td>" +
                "<td colspan=\"1\">预约人邮箱</td>" +
                "<td colspan=\"1\">预约提交时间</td>" +
                "<td colspan=\"1\">取消预约时间</td>" +
                "</tr>");
        for (MeetingBookingRecordVo record : recordList) {
            content.append("<tr>" +
                    "<td><span>" + record.getMeetingRoom() + "</span></td>" +
                    "<td><span>" + record.getStartTime() + "</span></td>" +
                    "<td><span>" + record.getEndTime() + "</span></td>" +
                    "<td><span>" + record.getBookerName() + "</span></td>" +
                    "<td><span>" + record.getBookerEmail() + "</span></td>" +
                    "<td><span>" + record.getCommitTime() + "</span></td>" +
                    "<td><span>" + record.getCancelTime() + "</span></td>" +
                    "</tr>");
        }
        content.append("</table>");
        content.append("</body></html>");
        return String.valueOf(content);
    }

    public static String createTable3(String headerText, List<AddBookRecordReqVO> recordList) {
        StringBuilder content = new StringBuilder("<html><head>" + headerText + "</head><body>");
        content.append("<table border=\"1\" style=\"width:1000px; height:150px;border:solid 1px #E8F2F9;font-size=14px;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><td rowspan=\"1\">会议室</td>" +
                "<td colspan=\"1\">会议预约开始时间</td>" +
                "<td colspan=\"1\">会议预约结束时间</td>" +
                "<td colspan=\"1\">预约人姓名</td>" +
                "<td colspan=\"1\">预约人邮箱</td>" +
                "<td colspan=\"1\">随机验证码</td>" +
                "</tr>");
        for (AddBookRecordReqVO record : recordList) {
            content.append("<tr>" +
                    "<td><span>" + record.getMeetingRoom() + "</span></td>" +
                    "<td><span>" + record.getStartTime() + "</span></td>" +
                    "<td><span>" + record.getEndTime() + "</span></td>" +
                    "<td><span>" + record.getBookerName() + "</span></td>" +
                    "<td><span>" + record.getBookerEmail() + "</span></td>" +
                    "<td><span>" + record.getRandomString() + "</span></td>" +
                    "</tr>");
        }
        content.append("</table>");
        content.append("</body></html>");
        return String.valueOf(content);
    }
}

