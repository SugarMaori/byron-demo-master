package com.git.byron.sitecount.site.util;

import java.time.LocalDate;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.sitecount.site.util
 * @ClassName: DateUtil
 * @Description: 当时间
 * @Date: 2019/8/7 9:23
 * @Version: 1.0
 */
public class DateUtil {

    public static String getToday() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        StringBuilder buf = new StringBuilder(8);
        return buf.append(year).append(month < 10 ? "0" : "").append(month).append(day < 10 ? "0" : "").append(day)
                .toString();
    }

}
