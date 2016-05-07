import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem02_DragonAccounting {
    private static final BigDecimal WORKDAYS_MONTHLY = new BigDecimal("30");
    private static final BigDecimal RAISE = new BigDecimal("1.006");
    private static BigDecimal capital;
    private static ArrayList<WorkersGroup> hiredEmployees = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        capital = new BigDecimal(sc.nextLine());
        boolean hasBankrupted = false;
        int totalWorkdays = 1;

        while (true) {
            // Process input
            String input = sc.nextLine();
            if (input.equals("END")) break;;

            args = input.split(";");
            long hired = Long.parseLong(args[0]);
            long fired = Long.parseLong(args[1]);
            BigDecimal salary = new BigDecimal(args[2]);

            // Step 1 - Hire employees
            WorkersGroup workers = new WorkersGroup(salary, calculateDailySalary(salary), BigDecimal.valueOf(hired));
            hiredEmployees.add(workers);

            // Step 2 - Check for Raise
            for (WorkersGroup workersGroup : hiredEmployees) {
                if (workersGroup.totalWorkdays % 365 == 0) {
                    workersGroup.monthSalary = workersGroup.monthSalary.multiply(RAISE);
                    workersGroup.dailySalary = calculateDailySalary(workersGroup.monthSalary);
                }
            }

            // Step 3 - Give salaries
            if (totalWorkdays % 30 == 0) {
                for (WorkersGroup workersGroup : hiredEmployees) {
                    BigDecimal workDays = new BigDecimal(workersGroup.workdaysThisMonth);
                    workersGroup.workdaysThisMonth = 0;
                    BigDecimal groupSalary = workDays.multiply(workersGroup.dailySalary).multiply(workersGroup.count);
                    capital = capital.subtract(groupSalary);
                }
            }

            // Step 4 - Fire employees
            fireWorkers(fired);

            // Step 5 - Check for additional income/expense
            for (int i = 3; i < args.length; i++) {
                String[] event = args[i].split(":");
                BigDecimal value = new BigDecimal(event[1]);
                processEvent(event[0], value);
            }

            // Step 6 - Check for Bankruptcy
            if (capital.compareTo(BigDecimal.ZERO) <= 0) {
                hasBankrupted = true;
                break;
            }

            // Update variables
            totalWorkdays++;
            for (WorkersGroup employee : hiredEmployees) {
                employee.totalWorkdays++;
                employee.workdaysThisMonth++;
            }
        }

        if (hasBankrupted) {
            System.out.printf("BANKRUPTCY: %s%n", capital.abs().setScale(4, BigDecimal.ROUND_DOWN));
        } else {
            BigDecimal totalWorkers = BigDecimal.ZERO;
            for (WorkersGroup workersGroup : hiredEmployees) {
                totalWorkers = totalWorkers.add(workersGroup.count);
            }
            System.out.printf("%s %s%n", totalWorkers.toString(), capital.setScale(4, BigDecimal.ROUND_DOWN));
        }
    }

    public static void fireWorkers(long firedWorkers) {
        while (firedWorkers > 0) {
            if (hiredEmployees.get(0).count.longValue() >= firedWorkers) {
                BigDecimal newValue = hiredEmployees.get(0).count.subtract(BigDecimal.valueOf(firedWorkers));
                hiredEmployees.get(0).count = newValue;
                if (hiredEmployees.get(0).count.equals(BigDecimal.ZERO)) {
                    hiredEmployees.remove(0);
                }
                firedWorkers = 0;
            } else {
                firedWorkers -= hiredEmployees.get(0).count.longValue();
                hiredEmployees.remove(0);
            }
        }
    }

    public static void processEvent(String event, BigDecimal value) {
        switch (event) {
            case "Previous years deficit":
            case "Machines":
            case "Taxes":
                capital = capital.subtract(value);
                break;
            default:
                capital = capital.add(value);
                break;
        }
    }

    public static BigDecimal calculateDailySalary(BigDecimal salary) {
        return salary.divide(WORKDAYS_MONTHLY, 9, BigDecimal.ROUND_UP)
                .setScale(7, BigDecimal.ROUND_DOWN);
    }
}

class WorkersGroup {
    int workdaysThisMonth;
    int totalWorkdays;
    BigDecimal monthSalary;
    BigDecimal dailySalary;
    BigDecimal count;

    public WorkersGroup(BigDecimal salary, BigDecimal dailySalary, BigDecimal count) {
        this.workdaysThisMonth = 1;
        this.totalWorkdays = 1;
        this.monthSalary = salary;
        this.dailySalary = dailySalary;
        this.count = count;
    }
}
