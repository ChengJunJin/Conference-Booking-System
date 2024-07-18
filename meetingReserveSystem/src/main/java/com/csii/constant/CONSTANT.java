package com.csii.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量信息
 * @createDate 2023-07-23
 */
public class CONSTANT {
    /**
     * 现有的会议室列表
     */
    public static List<String> MEETING_ROOM = new ArrayList<>();

    static {
        MEETING_ROOM.add("会议室二");
        MEETING_ROOM.add("会议室三");
        MEETING_ROOM.add("会议室六");
        MEETING_ROOM.add("会议室九");
    }
}
