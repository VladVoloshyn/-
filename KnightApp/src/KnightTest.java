import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private Knight knight;

    @BeforeEach
    public void setUp() {
        knight = new Knight();
    }

    @Test
    public void testCalculateTotalCost() {
        knight.equip(new Helmet("Steel Helmet", 2.5, 50.0));
        knight.equip(new Armor("Steel Armor", 15.0, 200.0));

        double totalCost = knight.calculateTotalCost();

        assertEquals(250.0, totalCost, 0.01);
    }

    @Test
    public void testSortEquipmentByWeight() {
        knight.equip(new Helmet("Light Helmet", 1.5, 30.0));
        knight.equip(new Armor("Heavy Armor", 20.0, 250.0));

        knight.sortEquipmentByWeight();
        List<KnightEquipment> sortedEquipment = knight.getEquipment();

        assertEquals("Light Helmet", sortedEquipment.get(0).getName());
        assertEquals("Heavy Armor", sortedEquipment.get(1).getName());
    }

    @Test
    public void testFindEquipmentByPriceRange() {
        knight.equip(new Helmet("Steel Helmet", 2.5, 50.0));
        knight.equip(new Armor("Steel Armor", 15.0, 200.0));
        knight.equip(new Armor("Silver Armor", 12.0, 150.0));

        double minPrice = 100.0;
        double maxPrice = 200.0;
        List<KnightEquipment> affordableEquipment = knight.findEquipmentByPriceRange(minPrice, maxPrice);

        assertEquals(2, affordableEquipment.size());
        assertEquals("Steel Armor", affordableEquipment.get(0).getName());
        assertEquals("Silver Armor", affordableEquipment.get(1).getName());
    }
}
