package a1.scarlos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random ran = new Random();
        List<Item> items = new ArrayList<>();
        FoodItems[] foodItems = FoodItems.values();
        Tools[] tools = Tools.values();
        ToolUses[] toolUses = ToolUses.values();
        Potions[] potionTypes = Potions.values();
        Tomes[] tomeTypes = Tomes.values();

        System.out.print("How many items do you want: ");
        int itemCnt = Integer.parseInt(scan.nextLine());

        for (int i = 0; i < itemCnt; i++) {
            int type = ran.nextInt(4);
            switch (type) {
                case 0 -> {
                    int foodIndex = ran.nextInt(foodItems.length);
                    String foodName = foodItems[foodIndex].toString();
                    float foodPrice = ran.nextFloat(10);
                    int foodQty = ran.nextInt(30);
                    int foodUses = ran.nextInt(6);
                    float healthGain = ran.nextFloat(20);
                    Food tmpFood = new Food(foodName, foodPrice, foodQty, foodUses, healthGain);
                    items.add(tmpFood);
                }
                case 1 -> {
                    int toolIndex = ran.nextInt(tools.length);
                    String toolName = tools[toolIndex].toString();
                    float toolPrice = ran.nextFloat(200);
                    int toolQty = ran.nextInt(15);
                    String use = toolUses[toolIndex].toString();
                    Tool tmpTool = new Tool(toolName, toolPrice, toolQty, use);
                    items.add(tmpTool);
                }
                case 2 -> {
                    int potionIndex = ran.nextInt(potionTypes.length);
                    String potionName = potionTypes[potionIndex].toString();
                    float potionPrice = ran.nextFloat(10);
                    int potionQty = ran.nextInt(30);
                    int potionUses = 1;
                    int potionDur = ran.nextInt(1200);
                    PotionItem tmpPotion = new PotionItem(potionName, potionPrice, potionQty, potionUses, potionDur);
                    items.add(tmpPotion);
                }
                case 3 -> {
                    int tomeIndex = ran.nextInt(tomeTypes.length);
                    String tomeName = tomeTypes[tomeIndex].toString();
                    float tomePrice = ran.nextFloat(1000);
                    int tomeQty = ran.nextInt(4);
                    int tomeMP = ran.nextInt(100);
                    int tomeDmg = ran.nextInt(1000);
                    TomeItem tmpTome = new TomeItem(tomeName, tomePrice, tomeQty, tomeMP, tomeDmg);
                    items.add(tmpTome);
                }
            }
        }

        for (Item i : items) {
            System.out.println(i);
        }

    }
}
