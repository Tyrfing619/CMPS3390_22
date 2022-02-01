package a1.scarlos;

public class PotionItem extends Consumable{
    private int duration;

    public PotionItem() {
        super();
        this.duration = 0;
    }

    public PotionItem(String name, double price, int qty, int usesLeft, int duration) {
        super(name, price, qty, usesLeft);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString(){
        return String.format("%s %10s %-5d sec |", super.toString(),"", this.duration);
    }
}
