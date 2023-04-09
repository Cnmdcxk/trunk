package netplus.utils.date;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class NowDate {

    private Date now;
    private String dateStr; //20190101
    private String timeStr; //235959
    private String monthStr; //201906
    private String dateTimeStr;//20190101 235959


    private String dateStr2; //2019-01-01
    private String timeStr2; //23:59:59
    private String dateTimeStr2; //2019-01-01 23:59:59



    private String dateTimeStr3;//20190101235959




    public NowDate(){
        now = new Date();
        dateStr = DateUtil.format(now, "yyyyMMdd");
        timeStr = DateUtil.format(now, "HHmmss");
        monthStr = DateUtil.format(now, "yyyyMM");
        dateTimeStr = dateStr + " " + timeStr;
        dateStr2 = DateUtil.format(now, "yyyy-MM-dd");
        timeStr2 = DateUtil.format(now, "HH:mm:ss");
        dateTimeStr2 = dateStr2 + " " + timeStr2;
        dateTimeStr3 = dateStr + timeStr;
    }
}
