package lahtinen;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {

    public static String[] fileToStringArray(String path) throws Exception {
        return fileToString(path).split("\r\n");
    }

    public static String fileToString(String path) throws Exception {
        return new String(
                Files.readAllBytes(Paths.get(path)),
                Charset.defaultCharset()
        );
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
