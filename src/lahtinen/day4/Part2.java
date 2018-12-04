package lahtinen.day4;

import lahtinen.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Part2 {
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

        sleepMinutesPerGuard.entrySet().stream()
                .map(e -> mostSleptMinute(e.getKey(), e.getValue()))
                .max(comparing(e -> e.numSleeps))
                .ifPresent(e -> System.out.println("Result: " + (e.id * e.minute)));
    }

    private static Result mostSleptMinute(Integer guardId, List<Integer> sleepMinutes) {
        var counted = sleepMinutes.stream()
                .collect(groupingBy(identity(), counting()));
        Map.Entry<Integer, Long> maxEntry = null;
        for (var count : counted.entrySet()) {
            if (maxEntry == null || count.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = count;
            }
        }
        if (maxEntry == null) { //one guard never sleeps, good job!
            return new Result(guardId, 0, 0L);
        }
        return new Result(guardId, maxEntry.getKey(), maxEntry.getValue());
    }

    static class Result {
        final int id;
        final int minute;
        final long numSleeps;

        Result(int id, int minute, long numSleeps) {
            this.id = id;
            this.minute = minute;
            this.numSleeps = numSleeps;
        }
    }
}
