package day3;

public class Claim {
    public final String id;
    public final int xPos, yPos, width, height;

    public Claim(String claim) {
        var claimParts = claim.split(" ");
        var posParts = claimParts[2].replace(":", "").split(",");
        var sizeParts = claimParts[3].split("x");
        this.id = claimParts[0];
        this.xPos = Integer.parseInt(posParts[0]);
        this.yPos = Integer.parseInt(posParts[1]);
        this.width = Integer.parseInt(sizeParts[0]);
        this.height = Integer.parseInt(sizeParts[1]);
    }
}
