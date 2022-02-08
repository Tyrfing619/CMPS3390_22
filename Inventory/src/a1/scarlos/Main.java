package a1.scarlos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random ran = new Random();

    private static final FoodItems[] foodItems = FoodItems.values();
    private static final Tools[] tools = Tools.values();
    private static final ToolUses[] toolUses = ToolUses.values();
    private static final Potions[] potionTypes = Potions.values();
    private static final Tomes[] tomeTypes = Tomes.values();

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.print("How many items do you want: ");
        int itemCnt = Integer.parseInt(scan.nextLine());

        for (int i = 0; i < itemCnt; i++) {
            int type = ran.nextInt(4);
            switch (type) {
                case 0 -> items.add(genFood());
                case 1 -> items.add(genTool());
                case 2 -> items.add(genPotion());
                case 3 -> items.add(genTome());
            }
        }

        for (Item i : items) {
            System.out.println(i);
        }

    }

    public static Food genFood() {
        int foodIndex = ran.nextInt(foodItems.length);
        String foodName = foodItems[foodIndex].toString();
        float foodPrice = ran.nextFloat(10);
        int foodQty = ran.nextInt(30);
        int foodUses = ran.nextInt(6);
        float healthGain = ran.nextFloat(20);
        return new Food(foodName, foodPrice, foodQty, foodUses, healthGain);
    }
    public static Tool genTool() {
        int toolIndex = ran.nextInt(tools.length);
        String toolName = tools[toolIndex].toString();
        float toolPrice = ran.nextFloat(200);
        int toolQty = ran.nextInt(15);
        String use = toolUses[toolIndex].toString();
        return new Tool(toolName, toolPrice, toolQty, use);
    }
    public static PotionItem genPotion() {
        int potionIndex = ran.nextInt(potionTypes.length);
        String potionName = potionTypes[potionIndex].toString();
        float potionPrice = ran.nextFloat(10);
        int potionQty = ran.nextInt(30);
        int potionUses = 1;
        int potionDur = ran.nextInt(1200);
        return new PotionItem(potionName, potionPrice, potionQty, potionUses, potionDur);
    }
    public static TomeItem genTome() {
        int tomeIndex = ran.nextInt(tomeTypes.length);
        String tomeName = tomeTypes[tomeIndex].toString();
        float tomePrice = ran.nextFloat(1000);
        int tomeQty = ran.nextInt(4);
        int tomeMP = ran.nextInt(100);
        int tomeDmg = ran.nextInt(1000);
        return new TomeItem(tomeName, tomePrice, tomeQty, tomeMP, tomeDmg);
    }

}
