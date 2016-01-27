package hanjie.app.vpscontroller.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanjie on 2016/1/27.
 */
public class DateUtils {
    /**
     * 格式化时间戳成自定义的格式
     *
     * @param time   时间戳
     * @param format 格式化的格式:例如 "yyyy/MM/dd"
     * @return
     */
    public static String getCustomDate(long time, String format) {
        Date curDate = new Date(time);
        return new SimpleDateFormat(format).format(curDate);
    }

}
