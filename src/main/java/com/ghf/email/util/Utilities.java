package com.ghf.email.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghf.email.dao.NotificationDao;
import com.ghf.email.model.EmailInfo;
import com.ghf.email.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.core.Message;

import java.util.Calendar;
import java.util.Date;


@Slf4j
public class Utilities {

    public static Notification mapEmailNotification(Message message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new String(message.getBody()), Notification.class);
        } catch (Exception e) {
            log.error("Unable to map object with entity : {} ", e);
        }
        return null;
    }
    public static <T> String convertObjectToRequestString(T data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("Unable to convert object to string : {} ", e);
        }
        return null;
    }
    public static EmailInfo mapEmailInfo(Notification notification){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(notification, EmailInfo.class);
        } catch (Exception e) {
            log.error("Unable to map object with entity : {} ", e);
        }
        return null;
    }

    public static void mapEmailData(EmailInfo emailInfo, NotificationDao notificationDao){
        emailInfo.setCreatedDateTime(new Date());
        emailInfo.setUpdatedDateTime(new Date());
        long count = notificationDao.count();
        count += 1;
        emailInfo.setEmailId(count);
    }
    public static Date minusHalfAnHour() {
        DateTime dateTime= new DateTime(new Date());
        DateTime date2= dateTime.minusMinutes(30);
        Date date=date2.toDate();
        return date;
    }

    public static Date startDateTime(){
        Calendar calendar = getCalendarForNow();
        setTimeToBeginningOfDay(calendar);
        Date beginning = calendar.getTime();
        return beginning;
    }

    public static Date endDateTime(){
        Calendar calendar = getCalendarForNow();
        setTimeToEndofDay(calendar);
        Date end = calendar.getTime();
        return end;
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
