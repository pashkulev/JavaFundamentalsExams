import java.util.*;

public class Problem04_ChampionLeague {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HashMap<String, Integer> teamsAndWins = new HashMap<>();
        HashMap<String, TreeSet<String>> teamsAndOponents = new HashMap<String, TreeSet<String>>();
        int maxWins = 0;

        while (true) {
            String inputLine = input.nextLine();
            if (inputLine.equals("stop")) {
                break;
            }

            args = inputLine.split("[|]");
            int team1GoalsHome = 0;
            int team1GoalsAway = 0;
            int team2GoalsHome = 0;
            int team2GoalsAway = 0;

            String[] results = args[2].trim().split(":");
            team1GoalsHome += Integer.parseInt(results[0]);
            team2GoalsAway += Integer.parseInt(results[1]);

            results = args[3].trim().split(":");
            team2GoalsHome += Integer.parseInt(results[0]);
            team1GoalsAway += Integer.parseInt(results[1]);

            String winningTeam;
            String loosingTeam;

            if (team1GoalsHome + team1GoalsAway == team2GoalsHome + team2GoalsAway) {
                if (team1GoalsAway > team2GoalsAway) {
                    winningTeam = args[0].trim();
                    loosingTeam = args[1].trim();
                } else {
                    winningTeam = args[1].trim();
                    loosingTeam = args[0].trim();
                }
            } else if (team1GoalsHome + team1GoalsAway > team2GoalsHome + team2GoalsAway) {
                winningTeam = args[0].trim();
                loosingTeam = args[1].trim();
            } else {
                winningTeam = args[1].trim();
                loosingTeam = args[0].trim();
            }

            if (!teamsAndWins.containsKey(winningTeam)) {
                teamsAndWins.put(winningTeam, 1);
            } else {
                teamsAndWins.put(winningTeam, teamsAndWins.get(winningTeam) + 1);
            }

            if (!teamsAndWins.containsKey(loosingTeam)) {
                teamsAndWins.put(loosingTeam, 0);
            }

            if (!teamsAndOponents.containsKey(winningTeam)) {
                teamsAndOponents.put(winningTeam, new TreeSet<String>());
            }
            teamsAndOponents.get(winningTeam).add(loosingTeam);

            if (!teamsAndOponents.containsKey(loosingTeam)) {
                teamsAndOponents.put(loosingTeam, new TreeSet<String>());
            }
            teamsAndOponents.get(loosingTeam).add(winningTeam);

            if (teamsAndWins.get(winningTeam) > maxWins) {
                maxWins = teamsAndWins.get(winningTeam);
            }
        }

        List<Integer> wins = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : teamsAndWins.entrySet()) {
            if (!wins.contains(entry.getValue())) {
                wins.add(entry.getValue());
            }
        }

        TreeMap<Integer, TreeMap<String, TreeSet<String>>> results = new TreeMap<Integer, TreeMap<String, TreeSet<String>>>();
        for (int i = 0; i < wins.size(); i++) {
            results.put(wins.get(i), new TreeMap<String, TreeSet<String>>());
            for (Map.Entry<String, Integer> entry : teamsAndWins.entrySet()) {
                if (wins.get(i) == entry.getValue()) {
                    results.get(wins.get(i)).put(entry.getKey(), teamsAndOponents.get(entry.getKey()));
                }
            }
        }

        List<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < wins.size(); i++) {
            resultList.add(new ArrayList<String>());
        }

        for (Map.Entry<Integer, TreeMap<String, TreeSet<String>>> entry : results.entrySet()) {
            for (Map.Entry<String, TreeSet<String>> setEntry : results.get(entry.getKey()).entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(setEntry.getKey() + '\n');
                sb.append(String.format("- Wins: %d%n", entry.getKey()));
                sb.append(String.format("- Opponents: %s", setEntry.getValue().toString().replaceAll("[\\[\\]]", "")));
                resultList.get(entry.getKey()).add(sb.toString());
            }
        }

        for (int i = resultList.size() - 1; i >= 0; i--) {
            for (int j = 0; j < resultList.get(i).size(); j++) {
                System.out.println(resultList.get(i).get(j));
            }
        }
    }
}
