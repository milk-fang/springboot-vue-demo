package com.example.demo.util;

import java.util.Date;

public class VerCodeGenerateUtil {
    // 获取两个时间的间隔
    public static int getMinute(Date fromDate, Date toDate) {
        return (int) (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }

}
