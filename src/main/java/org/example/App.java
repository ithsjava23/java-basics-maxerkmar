package org.example;


import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner scanner = new Scanner(System.in);
        int[] priceStorage = new int[24];
        while (true) {
            printMenu();
            String input = scanner.nextLine();
            if (input.equals("e") || input.equals("E")) break;
            else if (input.equals("1")) priceStorage = getPrices(scanner);
            else if (input.equals("2")) getMinMaxMean(priceStorage);
            else if (input.equals("3")) getSorted(priceStorage);
            else if (input.equals("4")) getBestTime(priceStorage);
            else System.out.println("Felaktig inmatning!");
            scanner.useDelimiter("\n");
        }
    }
    private static void printMenu(){
        String menu = """
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;
        System.out.println(menu);
    }
    public static int[] getPrices(Scanner sc) {
        System.out.println("Mata in värden: ");
        int[] prices = new int[24];
        for (int i = 0; i < prices.length; i++) {
            String input = sc.nextLine();
            sc.useDelimiter("\n");
            prices[i] = Integer.parseInt(input);
        }
        return prices;
    }
    private static int[] getMinPrice(int[] prices) {
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
    private static int[] getMaxPrice(int[] prices) {
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
    private static double getAverage(int[] prices) {
        int sum = 0;
        for (int price : prices) {
            sum = sum + price;
        }
        return (double) sum / prices.length;
    }
    private static void getMinMaxMean(int[] prices) {
        int[] minPrice = getMinPrice(prices);
        int[] maxPrice = getMaxPrice(prices);
        double avgPrice = getAverage(prices);
        String minMaxMean = String.format("""
            Lägsta pris: %02d-%02d, %d öre/kWh
            Högsta pris: %02d-%02d, %d öre/kWh
            Medelpris: %.2f öre/kWh
            """, minPrice[1], minPrice[1]+1, minPrice[0], maxPrice[1], maxPrice[1]+1, maxPrice[0], avgPrice);
        System.out.println(minMaxMean);
    }
    private static void getSorted(int[] prices) {
        int[] sortedArray = Arrays.copyOf(prices, prices.length);
        Arrays.sort(sortedArray);

        //jämför index med orginal arrayen för att hitta rätt tid
        int[] ogIndx = new int[4]; //index
        int l = prices.length-1;
        for (int i = 0; i < 4; i++) {
            int maxIndex = sortedArray[l-i];
            for (int j = 0; j < prices.length; j++) {
                if (prices[j] == maxIndex) {
                    ogIndx[i] = j;
                    break;
                }
            }
        }
        String sorted = String.format("""
            %02d-%02d %d öre
            %02d-%02d %d öre
            %02d-%02d %d öre
            %02d-%02d %d öre
            """, ogIndx[0],ogIndx[0]+1,sortedArray[l],ogIndx[1],ogIndx[1]+1,sortedArray[l-1],ogIndx[2],ogIndx[2]+1,sortedArray[l-2],ogIndx[3],ogIndx[3]+1,sortedArray[l-3]);
        System.out.println(sorted);
    }
    private static void getBestTime(int[] prices) {
        int[] sortedArray = Arrays.copyOf(prices, prices.length);
        Arrays.sort(sortedArray);

        int bestTime = 0;
        for (int i = 0; i < prices.length; i++) {
            if(prices[i] == sortedArray[0]){
                bestTime = i;
            }
        }
        double avgPrice = getAverage(Arrays.copyOf(sortedArray,4));
        String sorted = String.format("""
            Påbörja laddning klockan %02d
            Medelpris 4h: %.1f öre/kWh
            """, bestTime,avgPrice);
        System.out.println(sorted);
    }
}
