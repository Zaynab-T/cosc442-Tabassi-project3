package game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Color;

public class InventoryTest {
    
    @Test
    public void testConstructor_slotsStartEmpty() {

        Inventory inv = new Inventory(3);

        Item[] items = inv.getItems();

        assertEquals(3, items.length);
        assertNull(items[0]);
        assertNull(items[1]);
        assertNull(items[2]);
    }

    @Test 
    public void testConstructor_sizeZero() {

        Inventory inv = new Inventory(0);
        Item sword = new Item(')',Color.WHITE, "Sword", null);

        inv.add(sword);

        assertTrue(inv.isFull());
        assertFalse(inv.contains(sword));
    }

    @Test 
    public void testAdd_emptyInventory() {

        Inventory inv = new Inventory(3);
        Item sword = new Item(')',Color.WHITE, "Sword", null);

        inv.add(sword);

        assertSame(sword, inv.get(0));
    }

    @Test 
    public void testAdd_secondItem() {

        Inventory inv = new Inventory(3);
        Item sword = new Item(')',Color.WHITE, "Sword", null);
        Item shield = new Item(']',Color.WHITE, "Shield", null);

        inv.add(sword);
        inv.add(shield);

        assertSame(sword, inv.get(0));
        assertSame(shield, inv.get(1));
        assertNull(inv.get(2));
    }

    @Test 
    public void testAdd_fullInventory() {

        Inventory inv = new Inventory(1);
        Item sword = new Item(')',Color.WHITE, "Sword", null);
        Item shield = new Item(']',Color.WHITE, "Shield", null);

        inv.add(sword);
        inv.add(shield);

        assertSame(sword, inv.get(0));
        assertFalse(inv.contains(shield));
    }

    @Test 
    public void testAdd_afterRemove() {

        Inventory inv = new Inventory(3);
        Item sword = new Item(')',Color.WHITE, "Sword", null);
        Item shield = new Item(']',Color.WHITE, "Shield", null);
        Item potion = new Item('!',Color.WHITE, "Potion", null);
        Item rock = new Item('^',Color.WHITE, "Rock", null);

        inv.add(sword);
        inv.add(shield);
        inv.add(potion);
        inv.remove(sword);
        inv.add(rock);

        assertSame(rock, inv.get(1));
    }

    @Test 
    public void testRemove_itemInInventory() {

        Inventory inv = new Inventory(3);
        Item sword = new Item(')',Color.WHITE, "Sword", null);

        inv.add(sword);
        inv.remove(sword);

        assertNull(inv.get(0));
        assertFalse(inv.contains(sword));
    }

    @Test 
    public void testRemove_sameItemTwice() {

        Inventory inv = new Inventory(3);
        Item sword = new Item(')',Color.WHITE, "Sword", null);

        inv.add(sword);
        inv.add(sword);
        inv.remove(sword);

        assertNull(inv.get(0));
        assertSame(sword, inv.get(1));
        assertTrue(inv.contains(sword));
    }

    @Test 
    public void testRemove_itemNotInInventory() {

        Inventory inv = new Inventory(2);
        Item sword = new Item(')',Color.WHITE, "Sword", null);
        Item shield = new Item(']',Color.WHITE, "Shield", null);

        inv.add(sword);
        inv.remove(shield);

        assertSame(sword, inv.get(0));
        assertNull(inv.get(1));
    }

    @Test 
    public void testIsFull_empty() {

        Inventory inv = new Inventory(2);

        boolean result = inv.isFull();

        assertFalse(result);
    }

    @Test 
    public void testIsFull_partlyFilled() {

        Inventory inv = new Inventory(2);
        inv.add(new Item(')',Color.WHITE, "Sword", null));

        boolean result = inv.isFull();

        assertFalse(result);
    }

    @Test 
    public void testIsFull_allSlotsFilled() {

        Inventory inv = new Inventory(2);
        inv.add(new Item(')',Color.WHITE, "Sword", null));
        inv.add(new Item(']',Color.WHITE, "Shield", null));

        boolean result = inv.isFull();

        assertTrue(result);
    }

    @Test 
    public void testContains_itemInInventory() {

        Inventory inv = new Inventory(3);
        Item sword = new Item(')',Color.WHITE, "Sword", null);
        inv.add(new Item(']',Color.WHITE, "Shield", null));

        inv.add(sword);

        boolean result = inv.contains(sword);

        assertTrue(result);
    }

    @Test 
    public void testContains_itemNotInInventory() {
        
        Inventory inv = new Inventory(2);
        inv.add(new Item(')',Color.WHITE, "Sword", null));
        inv.add(new Item(']',Color.WHITE, "Shield", null));

        boolean result = inv.contains(new Item('!',Color.WHITE, "Potion", null));

        assertFalse(result);
    }
}