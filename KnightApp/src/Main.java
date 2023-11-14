import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Створюємо лицаря
        Knight knight = new Knight();

        // Екіпіруємо лицаря
        knight.equip(new Helmet("Steel Helmet", 2.5, 50.0));
        knight.equip(new Armor("Steel Armor", 15.0, 200.0));

        // Обчислюємо загальну вартість амуніції
        double totalCost = knight.calculateTotalCost();
        System.out.println("Total cost of knight's equipment: $" + totalCost);

        // Сортуємо амуніцію за вагою
        knight.sortEquipmentByWeight();
        System.out.println("Knight's equipment sorted by weight:");

        for (KnightEquipment item : knight.getEquipment()) {
            System.out.println(item.getName() + " - Weight: " + item.getWeight() + "kg");
        }

        // Пошук амуніції у заданому діапазоні цін
        double minPrice = 40.0;
        double maxPrice = 100.0;
        List<KnightEquipment> affordableEquipment = knight.findEquipmentByPriceRange(minPrice, maxPrice);

        System.out.println("Knight's equipment within the price range of $" + minPrice + " to $" + maxPrice + ":");
        for (KnightEquipment item : affordableEquipment) {
            System.out.println(item.getName() + " - Price: $" + item.getPrice());
        }
    }
}
