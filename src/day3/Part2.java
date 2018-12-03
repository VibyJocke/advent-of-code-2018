package day3;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part2 {
    public static void main(String... args) throws Exception {
        var claimStrings = FileUtils.readFileToString(new File("claims.txt"), Charset.defaultCharset()).split("\r\n");
        var claims = Stream.of(claimStrings).map(Claim::new).collect(Collectors.toList());
        var maxWidth = claims.stream().mapToInt(claim1 -> claim1.xPos + claim1.width).max().getAsInt();
        var maxHeight = claims.stream().mapToInt(claim1 -> claim1.yPos + claim1.height).max().getAsInt();
        var suit = new String[maxWidth][maxHeight];
        var collisions = new HashSet<String>();

        for (Claim claim : claims) {
            for (int i = claim.xPos; i < claim.xPos + claim.width; i++) {
                for (int j = claim.yPos; j < claim.yPos + claim.height; j++) {
                    if (suit[i][j] != null) {
                        collisions.add(claim.id);
                        collisions.add(suit[i][j]);
                    }
                    suit[i][j] = claim.id;
                }
            }
        }

        claims.stream()
                .filter(claim -> !collisions.contains(claim.id))
                .findFirst()
                .ifPresent(claim -> System.out.println(claim.id));
    }
}

