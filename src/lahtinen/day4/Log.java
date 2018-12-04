package lahtinen.day4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Log {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    final Type type;
    final LocalDateTime date;
    final int id;

    Log(String sleep) {
        var sleepParts = sleep.split(" ");
        var timeString = (sleepParts[0] + " " + sleepParts[1]).replace("[", "").replace("]", "");

        date = LocalDateTime.parse(timeString, TIME_FORMATTER);
        if (sleep.contains("wakes up")) {
            type = Type.AWAKE;
            id = 0;
        } else if (sleep.contains("falls asleep")) {
            type = Type.SLEEP;
            id = 0;
        } else {
            type = Type.NEW_GUARD;
            id = Integer.parseInt(sleepParts[3].replace("#", ""));
        }
    }

    enum Type {
        SLEEP, AWAKE, NEW_GUARD
    }
}
