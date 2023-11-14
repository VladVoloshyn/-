import java.util.ArrayList;
import java.util.List;

public class Knight {
    private List<KnightEquipment> equipment = new ArrayList<>();

    public void equip(KnightEquipment item) {
        equipment.add(item);
    }

    public List<KnightEquipment> getEquipment(){
        return equipment;
    }
    public double calculateTotalCost() {
        double totalCost = 0;
        for (KnightEquipment item : equipment) {
            totalCost += item.getPrice();
        }
        return totalCost;
    }

    public void sortEquipmentByWeight() {
        equipment.sort((item1, item2) -> Double.compare(item1.getWeight(), item2.getWeight()));
    }

    public List<KnightEquipment> findEquipmentByPriceRange(double minPrice, double maxPrice) {
        List<KnightEquipment> result = new ArrayList<>();
        for (KnightEquipment item : equipment) {
            if (item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
                result.add(item);
            }
        }
        return result;
    }
}
