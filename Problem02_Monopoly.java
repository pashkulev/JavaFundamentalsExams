import java.util.Scanner;

public class Problem02_Monopoly {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] dimensions = input.nextLine().split(" ");
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);

        char[][] monopoly = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            monopoly[i] = input.nextLine().toCharArray();
        }

        int turns = 0;
        int money = 50;
        int totalHotels = 0;
        String direction = "right";
        int row = 0;
        int col = 0;

        while (true) {
            if (monopoly[row][col] == 'H') {
                totalHotels++;
                System.out.printf("Bought a hotel for %d. Total hotels: %d.%n", money, totalHotels);
                money = 0;
            } else if (monopoly[row][col] == 'J'){
                System.out.printf("Gone to jail at turn %d.%n", turns);
                turns += 2;
                money += totalHotels * 20;
            } else if (monopoly[row][col] == 'S') {
                int product = (row + 1) * (col + 1);
                if (product >= money) {
                    System.out.printf("Spent %d money at the shop.%n", money);
                    money = 0;
                } else {
                    money -= product;
                    System.out.printf("Spent %d money at the shop.%n", product);
                }
            }

            if (direction == "right") {
                col++;
                if (col == cols) {
                    direction = "left";
                    row++;
                    col--;
                }
            } else {
                col--;
                if (col == -1) {
                    direction = "right";
                    row++;
                    col++;
                }
            }

            turns++;
            money += totalHotels * 10;

            if ((row == rows && col == cols - 1) || (row == rows && col == 0)) {
                break;
            }
        }

        System.out.println("Turns " + turns);
        System.out.println("Money " + money);
    }
}
