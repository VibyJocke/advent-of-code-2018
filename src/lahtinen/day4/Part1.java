package lahtinen.day4;

import lahtinen.Utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Part1 {
    public static void main(String... args) throws Exception {
        var logs = Stream.of(Utils.fileToStringArray("src/lahtinen/day4/input.txt"))
                .map(Log::new)
                .sorted(comparing(s -> s.date))
                .collect(toList());

        var sleepMinutesPerGuard = logs.stream()
                .filter(s -> s.type == Log.Type.NEW_GUARD)
                .filter(Utils.distinctByKey(s -> s.id))
                .collect(toMap(s -> s.id, s -> new ArrayList<Integer>()));
        int currentGuardId = logs.get(0).id;
        int sleepTime = 0;
        for (Log log : logs) {
            if (log.type == Log.Type.SLEEP) {
                sleepTime = log.date.getMinute();
            } else if (log.type == Log.Type.AWAKE) {
                sleepMinutesPerGuard.get(currentGuardId).addAll(
                        IntStream.range(sleepTime, log.date.getMinute())
                                .boxed().collect(toList())
                );
            } else {
                currentGuardId = log.id;
            }
        }

        int mostSleepingGuardId = 0;
        int mostMinutes = 0;
        for (var sleepMinutes : sleepMinutesPerGuard.entrySet()) {
            int id = sleepMinutes.getKey();
            int minutes = sleepMinutes.getValue().size();
            if (minutes > mostMinutes) {
                mostMinutes = minutes;
                mostSleepingGuardId = id;
            }
        }

        var counted = sleepMinutesPerGuard.get(mostSleepingGuardId).stream()
                .collect(groupingBy(identity(), counting()));
        Map.Entry<Integer, Long> maxEntry = null;
        for (var count : counted.entrySet()) {
            if (maxEntry == null || count.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = count;
            }
        }

        System.out.println("Result: " + mostSleepingGuardId * maxEntry.getKey());
    }
}
