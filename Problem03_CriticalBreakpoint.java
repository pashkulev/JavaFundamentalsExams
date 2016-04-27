import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem03_CriticalBreakpoint {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //I create an ArrayList of Strings with the lines that will be printed in case a critical breakpoint is found
        ArrayList<String> lines = new ArrayList<>();

        //In this ArrayList I store the critical ratios for each line. Because of the constraints we'll use Long values
        ArrayList<Long> criticalRatios = new ArrayList<>();

        //Here I initialize the actual critical ratio
        Long actualCriticalRatio = 0L;

        //A line counter
        int linesCount = 0;

        //I initialize a boolean variable that tracks if we've got a breakpoint. We start with true.
        //Later if the conditions are not matched, it will be changed to false
        boolean foundBreakpoint = true;

        //Start to take input
        while (true) {
            String input = sc.nextLine();
            if (input.equals("Break it.")) break; // check for end loop condition

            linesCount++; // Increment linesCount

            // Here I directly build the result strings for the final output by using the replaceAll method
            // and then add them to the lines ArrayList
            // They will be printed only if a breakpoint is found
            lines.add("Line: [" + input.replaceAll(" ", ", ") + "]");

            //Here I use the args array from our main method declaration to store the coordinates
            args = input.split(" ");

            //Now I parse each of the coordinates
            Long x1 = Long.parseLong(args[0]);
            Long y1 = Long.parseLong(args[1]);
            Long x2 = Long.parseLong(args[2]);
            Long y2 = Long.parseLong(args[3]);

            //Calculate critical ratio
            Long criticalRatio = Math.abs((x2 + y2) - (x1 + y1));

            //Add the critical ratio to the criticalRatios ArrayList
            criticalRatios.add(criticalRatio);
        }

        //In this loop I check for NON-ZERO actual ratio.
        //If there is such, I update the value of our actual critical ratio and break;
        for (int i = 0; i < criticalRatios.size(); i++) {
            if (criticalRatios.get(i) != 0L) {
                actualCriticalRatio = criticalRatios.get(i);
                break;
            }
        }

        //In this loop I check if all critical ratios are equal to the actual critical rattio or zero
        //If there is just one ratio that doesn't meat the condition, there is no breakpoint and we break;
        for (int i = 0; i < criticalRatios.size(); i++) {
            if (criticalRatios.get(i) != 0L && !criticalRatios.get(i).equals(actualCriticalRatio)) {
                foundBreakpoint = false;
                break;
            }
        }

        if (foundBreakpoint) {
            //If a breakpoint is found we have to calculate it.
            //And since we have to use a power method for Integer MaxValue or even bigger values because of the constraints,
            //we enter the world of BigInteger
            //In the next line I'm doing 2 operations. I'm declaring a BigInteger variable "result" that takes the value of the
            //actual critical ratio and hen I power it by the linesCount
            BigInteger result = BigInteger.valueOf(actualCriticalRatio).pow(linesCount);

            //Now I just take the remainder of the division between the result and the BigInteger value of linesCount
            //And this produces our final result
            result = result.remainder(BigInteger.valueOf(linesCount));

            //Now I print the lines that I stored in the lines ArrayList
            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i));
            }
            System.out.println("Critical Breakpoint: " + result);
        } else {
            System.out.println("Critical breakpoint does not exist.");
        }
    }
}
