import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem04_GUnit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String regex = "^([A-Z][a-zA-Z0-9]+)\\s\\|\\s([A-Z][a-zA-Z0-9]+)\\s\\|\\s([A-Z][a-zA-Z0-9]+)$";
        Pattern pattern = Pattern.compile(regex);
        HashMap<String, HashMap<String, ArrayList<String>>> gUnit = new HashMap<>();

        while (true) {
            String input = sc.nextLine();
            if (input.equals("It's testing time!")) break;

            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String strClass = matcher.group(1);
                String method = matcher.group(2);
                String unitTest = matcher.group(3);

                if (!gUnit.containsKey(strClass)) {
                    gUnit.put(strClass, new HashMap<String, ArrayList<String>>());
                }

                if (!gUnit.get(strClass).containsKey(method)) {
                    gUnit.get(strClass).put(method, new ArrayList<String>());
                }

                if (!gUnit.get(strClass).get(method).contains(unitTest)) {
                    gUnit.get(strClass).get(method).add(unitTest);
                }
            }
        }

        ArrayList<Map.Entry<String, Integer>> classesByUnitTests = new ArrayList<>();
        buildClassesByUnitTets(gUnit, classesByUnitTests);
        Collections.sort(classesByUnitTests, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        sortClassesByMethodsCount(gUnit, classesByUnitTests);
        sortAlphabetically(classesByUnitTests);

        for (int i = 0; i < classesByUnitTests.size(); i++) {
            String key = classesByUnitTests.get(i).getKey();
            System.out.println(key + ":");

            ArrayList<Map.Entry<String, Integer>> sortedMethods = new ArrayList<>();
            buildSortedMethods(gUnit, sortedMethods, key);
            Collections.sort(sortedMethods, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            sortAlphabetically(sortedMethods);

            for (int j = 0; j < sortedMethods.size(); j++) {
                String methodKey = sortedMethods.get(j).getKey();
                System.out.println("##" + methodKey);

                ArrayList<Map.Entry<String, Integer>> sortedTests = new ArrayList<>();
                buildSortedTests(gUnit, sortedTests, key, methodKey);
                Collections.sort(sortedTests, (e1, e2) -> e1.getValue().compareTo(e2.getValue()));
                sortAlphabetically(sortedTests);
                for (Map.Entry<String, Integer> test : sortedTests) {
                    System.out.println("####" + test.getKey());
                }
            }
        }
    }

    static void buildClassesByUnitTets(HashMap<String, HashMap<String, ArrayList<String>>> gUnit, ArrayList<Map.Entry<String, Integer>> unitTests) {

        for (String key : gUnit.keySet()) {
            int tests = 0;
            for (Map.Entry<String, ArrayList<String>> entry : gUnit.get(key).entrySet()) {
                tests += entry.getValue().size();
            }

            unitTests.add(new AbstractMap.SimpleEntry<String, Integer>(key, tests));
        }
    }

    static void buildSortedMethods(HashMap<String, HashMap<String, ArrayList<String>>> gUnit, ArrayList<Map.Entry<String, Integer>> sortedMethods, String key) {
        HashMap<String, ArrayList<String>> methodsMap = gUnit.get(key);
        for (String method : methodsMap.keySet()) {
            sortedMethods.add(new AbstractMap.SimpleEntry<String, Integer>(method, methodsMap.get(method).size()));
        }
    }

    static void buildSortedTests(HashMap<String, HashMap<String, ArrayList<String>>> gUnit, ArrayList<Map.Entry<String, Integer>> sortedTests, String key, String methodKey) {
        ArrayList<String> tests = gUnit.get(key).get(methodKey);
        for (int i = 0; i < tests.size(); i++) {
            sortedTests.add(new AbstractMap.SimpleEntry<String, Integer>(tests.get(i), tests.get(i).length()));
        }
    }

    static void sortClassesByMethodsCount(HashMap<String, HashMap<String, ArrayList<String>>> gUnit, ArrayList<Map.Entry<String, Integer>> unitTests) {
        boolean hasSwapped = true;
        while (hasSwapped) {
            hasSwapped = false;

            for (int i = 1; i < unitTests.size(); i++) {
                if (unitTests.get(i).getValue() == unitTests.get(i - 1).getValue()) {
                    String currentKey = unitTests.get(i).getKey();
                    String prevKey = unitTests.get(i - 1).getKey();
                    int currentKeyMethods = gUnit.get(currentKey).size();
                    int prevKeyMethods = gUnit.get(prevKey).size();
                    if (prevKeyMethods > currentKeyMethods) {
                        Map.Entry<String, Integer> temp = unitTests.get(i);
                        unitTests.set(i, unitTests.get(i - 1));
                        unitTests.set(i - 1, temp);
                        hasSwapped = true;
                    }
                }
            }
        }
    }

    static void sortAlphabetically(ArrayList<Map.Entry<String, Integer>> list) {
        boolean hasSwapped = true;
        while (hasSwapped) {
            hasSwapped = false;

            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getValue() == list.get(i - 1).getValue()) {
                    String currentKey = list.get(i).getKey();
                    String prevKey = list.get(i - 1).getKey();
                    if (currentKey.compareTo(prevKey) < 0) {
                        Map.Entry<String, Integer> temp = list.get(i);
                        list.set(i, list.get(i - 1));
                        list.set(i - 1, temp);
                        hasSwapped = true;
                    }
                }
            }
        }
    }
}
