package a1.scarlos;

public class TomeItem extends Item {
    private int mpReq;
    private int damage;

    public TomeItem() {
        super();
        this.mpReq = 0;
        this.damage = 0;
    }

    public TomeItem(String name, double price, int qty, int mpReq, int damage) {
        super(name, price, qty);
        this.mpReq = mpReq;
        this.damage = damage;
    }

    public int getMpReq() {
        return mpReq;
    }
    public void setMpReq(int mpReq) {
        this.mpReq = mpReq;
    }

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return String.format("%s %3dMP | %17dDMG |", super.toString(), this.mpReq, this.damage);
    }
}
