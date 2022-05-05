package versions;

public class JDK13 {
    public static void main(String[] args) {
        //1.switch
        switch1();
    }

    private static void switch1() {
        int number = 1;
        String result = switch (number) {
            case 1, 2 -> "one or two";
            case 3 -> "three";
            case 4, 5, 6 -> "four or five or six";
            default -> "unknown";
        };
    }
}
