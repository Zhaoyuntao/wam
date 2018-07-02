package com.wam.zgame.jff.warriorandmonster.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by zhaoyuntao on 2018/1/26.
 * 日期类, 用来格式化, 或者比较日期计算天数差
 */

public class ZDateTool {
    public static String format(String dateSrc, String format_src, String format_des) {
        if (S.isEmpty(dateSrc) || S.isEmpty(format_src) || S.isEmpty(format_des)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat_src = new SimpleDateFormat(format_src);
        SimpleDateFormat simpleDateFormat_des = new SimpleDateFormat(format_des);
        try {
            Date date_src = simpleDateFormat_src.parse(dateSrc);
            return simpleDateFormat_des.format(date_src);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * date2比date1多的天数
     *
     * @return
     */
    public static long differentDays(String day_small, String day_big) {
        if (day_small == null || day_big == null || !Pattern.compile("\\d{8}").matcher(day_small).matches() || !Pattern.compile("\\d{8}").matcher(day_big).matches()) {
            return -1;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        long count = 0;
        try {
            Date date_before = simpleDateFormat.parse(day_small);
            Date date_after = simpleDateFormat.parse(day_big);
            count = (date_after.getTime() - date_before.getTime()) / 86400000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return count;
    }
}
