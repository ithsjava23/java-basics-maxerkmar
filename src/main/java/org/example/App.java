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
            if (input.equalsIgnoreCase("e")) break;
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
        for (int i = 0; i < prices.length; i++) prices[i] = Integer.parseInt(sc.nextLine());
        return prices;
    }
    public static int[] getMinPrice(int[] prices) {
        int[] minArray = new int[2];
        minArray[0] = prices[0];
        for (int i = 0; i < prices.length; i++)
            if (prices[i] < minArray[0]) {
                minArray[0] = prices[i]; //pris
                minArray[1] = i;        //index
            }
        return minArray;
    }
    public static int[] getMaxPrice(int[] prices) {
        int[] maxArray = new int[2];
        maxArray[0] = prices[0];
        for (int i = 0; i < prices.length; i++)
            if (prices[i] > maxArray[0]) {
                maxArray[0] = prices[i]; //pris
                maxArray[1] = i;         //index
            }
        return maxArray;
    }
    public static double getAverage(int[] prices) {
        int sum = 0;
        for (int price : prices) sum += price;
        return (double) sum / prices.length;
    }
    public static void getMinMaxMean(int[] prices) {
        int[] minPrice = getMinPrice(prices);
        int[] maxPrice = getMaxPrice(prices);
        double avgPrice = getAverage(prices);
        printMinMaxMean(minPrice[1],minPrice[0],maxPrice[1],maxPrice[0],avgPrice);
    }
    public static void printMinMaxMean(int minTime,int minPrice,int maxTime,int maxPrice, double avgPrice){
        String minMaxMean = String.format("""
            Lägsta pris: %02d-%02d, %d öre/kWh
            Högsta pris: %02d-%02d, %d öre/kWh
            Medelpris: %.2f öre/kWh
            """, minTime, minTime+1, minPrice, maxTime, maxTime+1, maxPrice, avgPrice);
        System.out.println(minMaxMean.replace('.',','));
    }
    public static void getSorted(int[] prices) {
        int[] sortedArray = Arrays.copyOf(prices, prices.length);
        Arrays.sort(sortedArray);
        //jämför index med orginal arrayen för att hitta rätt tid
        int[] originalIndex = new int[4]; //index
        for (int i = 0; i < originalIndex.length; i++) {
            int maxIndex = sortedArray[sortedArray.length-1-i];
            for (int j = 0; j < prices.length; j++)
                if (prices[j] == maxIndex) {
                    originalIndex[i] = j;
                    break;
                }
        }
        printSorted(originalIndex,sortedArray);
    }
    public static void printSorted(int[] time,int[] price){
        String sorted = String.format("""
            %02d-%02d %d öre
            %02d-%02d %d öre
            %02d-%02d %d öre
            %02d-%02d %d öre
            """, time[0],time[0]+1,price[price.length-1],time[1],time[1]+1,price[price.length-2],
                 time[2],time[2]+1,price[price.length-3],time[3],time[3]+1,price[price.length-4]);
        System.out.println(sorted.replace('.',','));
    }
    public static void getBestTime(int[] prices) {
        int[] sortedArray = Arrays.copyOf(prices, prices.length);
        Arrays.sort(sortedArray);
        int bestTime = 0;
        for (int i = 0; i < prices.length; i++)
            if (prices[i] == sortedArray[0]) bestTime = i;
        printBestTime(bestTime,getAverage(Arrays.copyOf(sortedArray,4)));
    }
    public static void printBestTime(int bestTime, double avgPrice){
        String sorted = String.format("""
            Påbörja laddning klockan %02d
            Medelpris 4h: %.1f öre/kWh
            """, bestTime,avgPrice);
        System.out.println(sorted.replace('.',','));
    }
    public static void getVisual(int[] prices){
        int[] priceArr = Arrays.copyOf(prices,prices.length);
        int[] minPrice = getMinPrice(priceArr);
        int[] maxPrice = getMaxPrice(priceArr);
        float diff = (maxPrice[0]-minPrice[0]) / 5f;
        printVisual(6,24,priceArr,minPrice[0],maxPrice[0],diff);
    }
    public static void printVisual(int rows, int columns, int[] prices, int minPrice, int maxPrice, float stapleDiff){
        for (int i = 0; i < rows; i++) {
            if (i == 0) System.out.printf("%3d|", maxPrice);
            else if (i == 5) System.out.printf("%3d|", minPrice);
            else System.out.printf("   |");
            for (int j = 0; j < columns; j++)
                if (prices[j] >= maxPrice) System.out.printf("  x");
                else if (prices[j] >= maxPrice - Math.ceil(stapleDiff * i)) System.out.printf("  x");
                else System.out.printf("   ");
            System.out.printf("\n");
        }
        System.out.printf("   |------------------------------------------------------------------------"+"\n");
        System.out.printf("   | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23"+"\n");
    }
}
