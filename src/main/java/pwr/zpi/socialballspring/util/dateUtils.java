package pwr.zpi.socialballspring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dateUtils {
    public static LocalDateTime convertFromString(String date){
        if(date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, formatter);
        }
        return null;
    }
}
