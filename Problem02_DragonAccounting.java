import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem02_DragonAccounting {
    private static BigDecimal capital;
    private static final BigDecimal WORKDAYS_MONTHLY = new BigDecimal("30");
    private static final BigDecimal RAISE = new BigDecimal("1.006");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        capital = new BigDecimal(sc.nextLine());
        ArrayList<Employee> hiredEmployees = new ArrayList<>();
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
            for (long i = 0; i < hired; i++) {
                Employee worker = new Employee(salary, calculateDailySalary(salary));
                hiredEmployees.add(worker);
            }

            // Step 2 - Check for Raise
            for (Employee employee : hiredEmployees) {
                if (employee.totalWorkdays % 365 == 0) {
                    employee.monthSalary = employee.monthSalary.multiply(RAISE);
                    employee.dailySalary = calculateDailySalary(employee.monthSalary);
                }
            }

            // Step 3 - Give salaries
            if (totalWorkdays % 30 == 0) {
                for (Employee employee : hiredEmployees) {
                    BigDecimal workDays = new BigDecimal(employee.workdaysThisMonth);
                    employee.workdaysThisMonth = 0;
                    BigDecimal employeeSalary = workDays.multiply(employee.dailySalary);
                    capital = capital.subtract(employeeSalary);
                }
            }

            // Step 4 - Fire employees
            for (long i = 0; i < fired && hiredEmployees.size() != 0; i++) {
                hiredEmployees.remove(0);
            }

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
            for (Employee employee : hiredEmployees) {
                employee.totalWorkdays++;
                employee.workdaysThisMonth++;
            }
        }

        if (hasBankrupted) {
            System.out.printf("BANKRUPTCY: %s%n", capital.abs().setScale(4, BigDecimal.ROUND_DOWN));
        } else {
            System.out.printf("%d %s%n", hiredEmployees.size(), capital.setScale(4, BigDecimal.ROUND_DOWN));
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

class Employee {
    int workdaysThisMonth;
    int totalWorkdays;
    BigDecimal monthSalary;
    BigDecimal dailySalary;

    public Employee (BigDecimal salary, BigDecimal dailySalary) {
        this.workdaysThisMonth = 1;
        this.totalWorkdays = 1;
        this.monthSalary = salary;
        this.dailySalary = dailySalary;
    }
}
