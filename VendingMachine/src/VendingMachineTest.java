import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class VendingMachineTest {
    
    @Test
    public void testConstructor_balanceIsZero() {
        
        VendingMachine vm = new VendingMachine();

        assertEquals(0.0, vm.getBalance(), 0.001);
    }

    @Test
    public void testConstructor_allSlotsEmpty() {
        
        VendingMachine vm = new VendingMachine();

        assertNull(vm.getItem("A"));
        assertNull(vm.getItem("B"));
        assertNull(vm.getItem("C"));
        assertNull(vm.getItem("D"));
    }

    @Test 
    public void testAddItem_emptySlot() {
        
        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Chips", 1.50);

        vm.addItem(item, "A");

        assertSame(item, vm.getItem("A"));
    }

    @Test
    public void testAddItem_lastSlot() {

        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Gum", 0.75);

        vm.addItem(item, "D");

        assertSame(item, vm.getItem("D"));
    }

    @Test 
    public void testAddItem_occupiedSlot() {
        
        VendingMachine vm = new VendingMachine();
        vm.addItem(new VendingMachineItem("Chips", 1.50), "B");
        VendingMachineItem newItem = new VendingMachineItem("Soda", 1.25);

        assertThrows(VendingMachineException.class, () -> vm.addItem(newItem, "B"));
    }

    @Test 
    public void testAddItem_invalidCode() {
        
        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Candy", 1.00);

        assertThrows(VendingMachineException.class, () -> vm.addItem(item, "E"));
    }

    @Test 
    public void testAddItem_emptyStringCode() {
        
        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Candy", 1.00);

        assertThrows(VendingMachineException.class, () -> vm.addItem(item, ""));
    }

    @Test 
    public void testAddItem_lowercaseCode() {

        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Candy", 1.00);

        assertThrows(VendingMachineException.class, () -> vm.addItem(item, "a"));
    }

    @Test 
    public void testGetItem_occupiedSlot() {
        
        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Chips", 1.50);
        vm.addItem(item, "C");

        VendingMachineItem result = vm.getItem("C");

        assertSame(item, result);
    }

    @Test 
    public void testGetItem_invalidCode() {

        VendingMachine vm = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> vm.getItem("E"));
    }

    @Test 
    public void testRemoveItem_occupiedSlot() {
        
        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Chips", 1.50);
        vm.addItem(item, "A");

        VendingMachineItem removedItem = vm.removeItem("A");

        assertSame(item, removedItem);
        assertNull(vm.getItem("A"));
    }

    @Test 
    public void testRemoveItem_emptySlot() {

        VendingMachine vm = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> vm.removeItem("A"));
    }

    @Test 
    public void testRemoveItem_invalidCode() {

        VendingMachine vm = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> vm.removeItem("E"));
    }

    @Test 
    public void testRemoveItem_sameSlotTwice() {

        VendingMachine vm = new VendingMachine();
        vm.addItem(new VendingMachineItem("Chips", 1.50), "B");
        vm.removeItem("B");

        assertThrows(VendingMachineException.class, () -> vm.removeItem("B"));
    }

    @ParameterizedTest 
    @ValueSource(doubles = {0.0, 0.01, 0.25, 0.99, 1.0, 5.0})
    public void testInsertMoney_validAmount(double amount) {

        VendingMachine vm = new VendingMachine();

        vm.insertMoney(amount);

        assertEquals(amount, vm.getBalance(), 0.001);
    }

    @Test 
    public void testInsertMoney_negativeAmount() {

        VendingMachine vm = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> vm.insertMoney(-1.0));
    }

    @Test 
    public void testInsertMoney_justBelowZero() {

        VendingMachine vm = new VendingMachine();

        assertThrows(VendingMachineException.class, () -> vm.insertMoney(-0.01));
        assertEquals(0.0, vm.getBalance(), 0.001);
    }

    @Test 
    public void testInsertMoney_twoInserts() {

        VendingMachine vm = new VendingMachine();

        vm.insertMoney(1.25);
        vm.insertMoney(0.50);

        assertEquals(1.75, vm.getBalance(), 0.001);
    }

    @Test 
    public void testGetBalance_afterInserts() {

        VendingMachine vm = new VendingMachine();
        vm.insertMoney(2.50);

        double balance = vm.getBalance();

        assertEquals(2.50, balance, 0.001);
    }

    @Test 
    public void testGetBalance_calledTwice() {

        VendingMachine vm = new VendingMachine();
        vm.insertMoney(2.50);

        vm.getBalance();
        double second = vm.getBalance();

        assertEquals(2.50, second, 0.001);
    }

    @Test 
    public void testMakePurchase_balanceAbovePrice() {

        VendingMachine vm = new VendingMachine();
        vm.addItem(new VendingMachineItem("Chips", 1.50), "A");
        vm.insertMoney(2.00);

        boolean result = vm.makePurchase("A");

        assertTrue(result);
        assertEquals(0.50, vm.getBalance(), 0.001);
        assertNull(vm.getItem("A"));
    }

    @Test 
    public void testMakePurchase_balanceEqualToPrice() {

        VendingMachine vm = new VendingMachine();
        vm.addItem(new VendingMachineItem("Soda", 1.25), "B");
        vm.insertMoney(1.25);

        boolean result = vm.makePurchase("B");

        assertTrue(result);
        assertEquals(0.0, vm.getBalance(), 0.001);
    }

    @Test 
    public void testMakePurchase_balanceBelowPrice() {

        VendingMachine vm = new VendingMachine();
        VendingMachineItem item = new VendingMachineItem("Candy", 1.50);
        vm.addItem(item, "C");
        vm.insertMoney(1.49);

        boolean result = vm.makePurchase("C");

        assertFalse(result);
        assertEquals(1.49, vm.getBalance(), 0.001);
        assertSame(item, vm.getItem("C"));
    }

    @Test 
    public void testMakePurchase_emptySlot() {

        VendingMachine vm = new VendingMachine();
        vm.insertMoney(5.00);

        boolean result = vm.makePurchase("D");

        assertFalse(result);
        assertEquals(5.00, vm.getBalance(), 0.001);
    }

    @Test 
    public void testMakePurchase_invalidCode() {

        VendingMachine vm = new VendingMachine();
        vm.insertMoney(5.00);

        assertThrows(VendingMachineException.class, () -> vm.makePurchase("E"));
    }

    @Test 
    public void testReturnChange_positiveBalance() {

        VendingMachine vm = new VendingMachine();
        vm.insertMoney(3.25);

        double change = vm.returnChange();

        assertEquals(3.25, change, 0.001);
        assertEquals(0.0, vm.getBalance(), 0.001);
    }

    @Test 
    public void testReturnChange_zeroBalance() {

        VendingMachine vm = new VendingMachine();

        double change = vm.returnChange();

        assertEquals(0.0, change, 0.001);
    }

    @Test 
    public void testReturnChange_calledTwice() {

        VendingMachine vm = new VendingMachine();
        vm.insertMoney(3.25);
        vm.returnChange();

        double secondChange = vm.returnChange();

        assertEquals(0.0, secondChange, 0.001);
    }
}
