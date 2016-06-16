import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Problem02_JediGalaxy_NoMatrix {
    static long jediSum = 0L;
    static int rows;
    static int cols;
    private static HashSet<String> zeroPositions;


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] dimensions = reader.readLine().split(" ");
        rows = Integer.valueOf(dimensions[0]);
        cols = Integer.valueOf(dimensions[1]);

        zeroPositions = new HashSet<>();

        while (true) {
            String input = reader.readLine();
            if (input.equals("Let the Force be with you")) break;

            String[] coordinates = input.split(" ");
            int jediRow = Integer.valueOf(coordinates[0]);
            int jediCol = Integer.valueOf(coordinates[1]);

            coordinates = reader.readLine().split(" ");
            int evilRow = Integer.valueOf(coordinates[0]);
            int evilCol = Integer.valueOf(coordinates[1]);

            // process the dark path
            if (evilRow >= rows) {
                int shiftValue = evilRow - rows + 1;
                evilRow -= shiftValue;
                evilCol -= shiftValue;
            }

            if (evilCol >= cols) {
                int shiftValue = evilCol - cols + 1;
                evilRow -= shiftValue;
                evilCol -= shiftValue;
            }

            while (evilRow >= 0 && evilCol >= 0) {
                zeroPositions.add(evilRow + " " + evilCol);
                evilRow--;
                evilCol--;
            }

            //process the jedi path
            if (jediRow >= rows) {
                int shiftValue = jediRow - rows + 1;
                jediRow -= shiftValue;
                jediCol += shiftValue;
            }

            if (jediCol < 0) {
                int shiftValue = Math.abs(jediCol);
                jediRow -= shiftValue;
                jediCol += shiftValue;
            }

            while (jediRow >= 0 && jediCol < cols) {
                if (!zeroPositions.contains(jediRow + " " + jediCol)) {
                    jediSum += jediRow * cols + jediCol;
                }
                jediRow--;
                jediCol++;
            }
        }

        System.out.println(jediSum);
    }
}