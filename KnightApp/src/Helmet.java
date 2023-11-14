public class Helmet extends KnightEquipment {
    public Helmet(String name, double weight, double price) {
        super(name, weight, price);
    }

    @Override
    public void use() {
        System.out.println("Wearing the helmet");
    }
}
