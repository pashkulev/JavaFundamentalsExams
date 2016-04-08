import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem03_BasicMarkupLanguage {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int lineNumber = 0;

        while (true) {
            String input = console.nextLine().replaceAll("[\\s+]+", "");
            if (input.equals("<stop/>")) {
                break;
            }

            String regexInverseReverse = "<(inverse|reverse)+content=\"(\\w+)+\"";
            String regexRepeat = "<(repeat)value=\"(\\d+)\"content=\"(\\w+)+\"";
            Pattern pattern = Pattern.compile(regexInverseReverse);
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String command = matcher.group(1);
                String content = matcher.group(2);
                if (content.length() < 1) {
                    continue;
                }

                if (command.equals("inverse")) {
                    StringBuilder inversed = new StringBuilder();
                    for (int i = 0; i < content.length(); i++) {
                        if (Character.isLowerCase(content.charAt(i))) {
                            inversed.append((content.charAt(i) + "").toUpperCase());
                        } else {
                            inversed.append((content.charAt(i) + "").toLowerCase());
                        }
                    }
                    System.out.printf("%d. %s%n", ++lineNumber, inversed.toString());
                } else {
                    StringBuilder reversed = new StringBuilder();
                    for (int i = content.length() - 1; i >= 0; i--) {
                        reversed.append(content.charAt(i) + "");
                    }
                    System.out.printf("%d. %s%n", ++lineNumber, reversed.toString());
                }

            } else {
                pattern = Pattern.compile(regexRepeat);
                matcher = pattern.matcher(input);
                if (matcher.find()) {
                    int value = Integer.parseInt(matcher.group(2));
                    String content = matcher.group(3);
                    if (content.length() < 1) {
                        continue;
                    }

                    for (int i = 0; i < value; i++) {
                        System.out.printf("%d. %s%n", ++lineNumber, content);
                    }
                }
            }
        }
    }
}
