public class Armor extends KnightEquipment {
    public Armor(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public void use() {
        System.out.println("Wearing the armor");
    }
}
