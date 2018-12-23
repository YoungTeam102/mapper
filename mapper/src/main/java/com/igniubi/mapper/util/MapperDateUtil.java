package com.igniubi.mapper.util;

import com.igniubi.common.utils.DateUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 类说明
 * <p>
 *
 * @author 徐擂
 * @version 1.0.0
 * @date 2018/12/23
 */
public class MapperDateUtil extends DateUtil {

    public static String formatDate(Timestamp timestamp, String format) {
        if (timestamp == null) {
            return null;
        }
        String formatDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format == null ? DATE_TIME_FORMAT : format);
            formatDate = dateFormat.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;
    }
}
