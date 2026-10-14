import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VendingMachineItemTest {

    @Test
    public void testConstructor_validNameAndPrice() {
        String name = "Chips";
        double price = 1.50;

        VendingMachineItem item = new VendingMachineItem(name, price);

        assertEquals("Chips", item.getName());
        assertEquals(1.50, item.getPrice(), 0.001);
    }

    @Test
    public void testConstructor_priceLessThanZero() {
        String name = "Gum";
        double price = -0.01;

        assertThrows(VendingMachineException.class, () -> new VendingMachineItem(name, price));
    }

    @Test
    public void testConstructor_priceZero() {
        String name = "Candy";
        double price = 0.00;

        VendingMachineItem item = new VendingMachineItem(name, price);

        assertEquals("Candy", item.getName());
        assertEquals(0.00, item.getPrice(), 0.001);
    }

    @Test
    public void testConstructor_priceAboveZero() {
        String name = "Soda";
        double price = 0.01;

        VendingMachineItem item = new VendingMachineItem(name, price);

        assertEquals("Soda", item.getName());
        assertEquals(0.01, item.getPrice(), 0.001);
    }
}
