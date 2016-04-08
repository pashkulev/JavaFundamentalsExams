import java.util.*;

public class Problem01_ArrangeNumbers {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] strNums = input.nextLine().split("\\W+");

        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        for (String strNum : strNums) {
            list.add(new AbstractMap.SimpleEntry<String, Integer>(numToString(strNum), Integer.parseInt(strNum)));
        }
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> s1, Map.Entry<String, Integer> s2) {
                return s1.getKey().compareTo(s2.getKey());
            }
        });

        System.out.print(list.get(0).getValue());
        for (int i = 1; i < list.size(); i++) {
            System.out.printf(", %d", list.get(i).getValue());
        }
        System.out.println();
    }

    public static String numToString(String num) {
        String[] digitsAsWord = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        StringBuilder result = new StringBuilder();

        int digit = Integer.parseInt(num.charAt(0) + "");
        result.append(digitsAsWord[digit]);
        for (int i = 1; i < num.length(); i++) {
            digit = Integer.parseInt(num.charAt(i) + "");
            result.append("-" + digitsAsWord[digit]);
        }

        return result.toString();
    }
}
