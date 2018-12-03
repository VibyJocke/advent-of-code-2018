package day3;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part1 {
    public static void main(String... args) throws Exception {
        var claimStrings = FileUtils.readFileToString(new File("claims.txt"), Charset.defaultCharset()).split("\r\n");
        var claims = Stream.of(claimStrings).map(Claim::new).collect(Collectors.toList());
        var maxWidth = claims.stream().mapToInt(claim1 -> claim1.xPos + claim1.width).max().getAsInt();
        var maxHeight = claims.stream().mapToInt(claim1 -> claim1.yPos + claim1.height).max().getAsInt();
        var suit = new int[maxWidth][maxHeight];
        var overlaps = 0;

        for (Claim claim : claims) {
            for (int i = claim.xPos; i < claim.xPos + claim.width; i++) {
                for (int j = claim.yPos; j < claim.yPos + claim.height; j++) {
                    if (suit[i][j] == 1) {
                        overlaps++;
                    }
                    suit[i][j]++;
                }
            }
        }

        System.out.println("Overlapping segments: " + overlaps);
    }
}
