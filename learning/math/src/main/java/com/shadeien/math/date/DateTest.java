package com.shadeien.math.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class DateTest {
    public static void main(String[] args) {
        Date date = new Date();
        log.info("{}", date.getTime());

        TimeZone tz = TimeZone.getTimeZone("GMT+06:00");
        TimeZone.setDefault(tz);
        log.info("{}", tz);

        String[] ids = TimeZone.getAvailableIDs();
        log.info("{}", Arrays.toString(ids));

        Calendar instance = Calendar.getInstance(tz);
        log.info("{}", instance.getTime());
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
        log.info("{}", instance.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        log.info(simpleDateFormat.format(instance.getTime()));

        Clock clock = Clock.systemUTC();
        log.info("{}", clock);

        ZoneId defaultZone = ZoneId.systemDefault();
        log.info("{}", defaultZone);
        ZoneId america = ZoneId.of("America/New_York");
        log.info("上海当前时间为:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("america当前时间为:{}", LocalDateTime.now(america).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        ZonedDateTime americaZonedDateTime = ZonedDateTime.now(america);
        log.info("{}", americaZonedDateTime);

        String a = "abb";
        switch (a) {
            case "a":
                log.info(a);
                break;
            case "abb":
                log.info(a);
                break;
            default:
                log.info(a);

        }
    }

    public void a() {

    }

//    public String a() {
//        return "a";
//    }
}
