package org.example;


import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
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
            else if (input.equals("5")) getVisual(priceStorage);
        }
    }
    public static void printMenu(){
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
        int[] prices = new int[24];
        for (int i = 0; i < prices.length; i++) {
            String input = sc.nextLine();
            prices[i] = Integer.parseInt(input);
        }
        return prices;
    }
    public static int[] getMinPrice(int[] prices) {
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
    public static int[] getMaxPrice(int[] prices) {
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
    public static double getAverage(int[] prices) {
        int sum = 0;
        for (int price : prices) {
            sum = sum + price;
        }
        return (double) sum / prices.length;
    }
    public static void getMinMaxMean(int[] prices) {
        int[] minPrice = getMinPrice(prices);
        int[] maxPrice = getMaxPrice(prices);
        double avgPrice = getAverage(prices);
        String minMaxMean = String.format("""
            Lägsta pris: %02d-%02d, %d öre/kWh
            Högsta pris: %02d-%02d, %d öre/kWh
            Medelpris: %.2f öre/kWh
            """, minPrice[1], minPrice[1]+1, minPrice[0], maxPrice[1], maxPrice[1]+1, maxPrice[0], avgPrice);
        System.out.println(minMaxMean.replace('.',','));
    }
    public static void getSorted(int[] prices) {
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
        System.out.println(sorted.replace('.',','));
    }
    public static void getBestTime(int[] prices) {
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
        System.out.println(sorted.replace('.',','));
    }
    public static void getVisual(int[] prices){
        int[] minPrice = getMinPrice(prices);
        int[] maxPrice = getMaxPrice(prices);
        float diff = (maxPrice[0]-minPrice[0]) / 5f;
        for (int i = 0; i < 6; i++) {
            if (i == 0) System.out.printf("%3d|", maxPrice[0]);
            else if (i == 5) System.out.printf("%3d|", minPrice[0]);
            else System.out.printf("   |");
            for (int j = 0; j < 24; j++) {
                if (prices[j] >= maxPrice[0]) System.out.printf("  x");
                else if (prices[j] >= maxPrice[0]-Math.ceil(diff*i)) System.out.printf("  x");
                else System.out.printf("   ");
            }
            System.out.printf("\n");
        }
        System.out.printf("   |------------------------------------------------------------------------"+"\n");
        System.out.printf("   | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23"+"\n");
    }
}
