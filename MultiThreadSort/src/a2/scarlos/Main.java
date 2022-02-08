package a2.scarlos;

import a1.scarlos.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        Random ran = new Random();

        System.out.print("Do you want a [S]ingle Sort, a [D]ual Sort, or a [Q]uad Sort? ");
        char selection = scan.next().charAt(0);

        System.out.print("How many items do you want to sort? ");
        int count = scan.nextInt();

        Item[] items = new Item[count];
        for (int i = 0; i < count; i++) {
            int t = ran.nextInt(4);
            switch (t) {
                case 0 -> items[i] = a1.scarlos.Main.genFood();
                case 1 -> items[i] = a1.scarlos.Main.genTool();
                case 2 -> items[i] = a1.scarlos.Main.genPotion();
                case 3 -> items[i] = a1.scarlos.Main.genTome();
            }
        }
        switch (selection) {
            case 's', 'S' -> SingleSort(items);
            case 'd', 'D' -> DualSort(items);
            case 'q', 'Q' -> QuadSort(items);
        }
    }

    private static void QuadSort(Item [] items) throws InterruptedException {
        int mid = Math.round(items.length / 4f);
        ThreadSort t1 = new ThreadSort(items, 0, mid);
        ThreadSort t2 = new ThreadSort(items, mid, mid*2);
        ThreadSort t3 = new ThreadSort(items, mid*2, mid*3);
        ThreadSort t4 = new ThreadSort(items, mid*3, items.length);

        long startTime = System.nanoTime();
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        MergeSort m1 = new MergeSort(t1.gettItems(), t2.gettItems());
        MergeSort m2 = new MergeSort(t3.gettItems(), t4.gettItems());
        m1.start();
        m2.start();
        m1.join();
        m2.join();

        MergeSort m3 = new MergeSort(m1.getSortedItems(), m2.getSortedItems());
        m3.start();
        m3.join();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        for (Item i : m3.getSortedItems()) {
            System.out.println(i);
        }
        System.out.println("Quad sort took: " + duration + "ms");
    }

    private static void DualSort(Item[] items) throws InterruptedException {
        int mid = Math.round(items.length / 2f);
        ThreadSort t1 = new ThreadSort(items, 0, mid);
        ThreadSort t2 = new ThreadSort(items, mid, items.length);

        long startTime = System.nanoTime();
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        MergeSort m1 = new MergeSort(t1.gettItems(), t2.gettItems());
        m1.start();
        m1.join();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        for (Item i : m1.getSortedItems()) {
            System.out.println(i);
        }
        System.out.println("Dual sort took: " + duration + "ms");
    }

    private static void SingleSort(Item[] items) {
        // Sort before print
        ThreadSort single = new ThreadSort(items, 0, items.length);
        long startTime = System.nanoTime();
        single.start();
        try {
            single.join();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            Item[] sortedItems = single.gettItems();
            for(Item i : sortedItems) {
                System.out.println(i);
            }
            System.out.println("Single sort took: " + duration + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
