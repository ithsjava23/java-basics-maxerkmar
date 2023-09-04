package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        boolean exit = false;
        int[] priceStorage = new int[24];
        String input = "";
        while (!exit) {
            String menu = """
                    Elpriser
                    ========
                    1. Inmatnin
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;
            System.out.println(menu);
            Scanner scanner = new Scanner(System.in);
            boolean inputNext = scanner.hasNext();
            if (inputNext) input = scanner.next();
            //int[] temp = {100, 10, 1, 10, 10, 10, 210, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

            switch (input) {
                case "1" -> priceStorage = set_prices();
                case "2" -> {
                    int[] minPrice = min_price(priceStorage);
                    int[] maxPrice = max_price(priceStorage);
                    double avgPrice = avg_price(priceStorage);
                    int minTime = minPrice[1];
                    int maxTime = maxPrice[1];
                    String response = String.format("""
                            Lägsta pris: %02d-%02d, %d öre/kWh
                            Högsta pris: %02d-%02d, %d öre/kWh
                            Medelpris: %.2f öre/kWh
                            """, minTime, minTime + 1, minPrice[0], maxTime, maxTime + 1, maxPrice[0], avgPrice);
                    System.out.println(response);
                }
                case "3" -> System.out.println("Sortera: ");
                case "4" -> System.out.println("Bästa Laddningstid: ");
                case "e", "E" -> {
                    System.out.println("Avslutar");
                    exit = true;
                }
                default -> System.out.println("fel");
            }

        }
    }
    public static int[] set_prices() {
        System.out.println("Mata in värden: ");
        Scanner scanner = new Scanner(System.in);
        int[] prices = new int[24];

        try {
            for (int i = 0; i < prices.length; i++) {
                System.out.println(i + ": ");
                int inputPrice = scanner.nextInt();
                prices[i] = inputPrice;
            }
        } catch (NoSuchElementException elementException) {
            System.out.println("Felaktigt tecken! ");
        }
        return prices;
    }

    private static int[] min_price(int[] prices) {
        int[] minArray = new int[2];
        minArray[0] = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minArray[0]){
                minArray[0] = prices[i]; //pris
                minArray[1] = i;        //index
            }
        }
        return minArray;
    }

    private static int[] max_price(int[] prices) {
        int[] maxArray = new int[2];
        maxArray[0] = prices[0];
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > maxArray[0]) {
                maxArray[0] = prices[i]; //pris
                maxArray[1] = i;         //index
            }
        }
        return maxArray;
    }
    private static double avg_price(int[] prices) {
        int sum = 0;
        for (int i = 0; i < prices.length; i++) sum += prices[i];
        return (double) sum / prices.length;
    }
}
