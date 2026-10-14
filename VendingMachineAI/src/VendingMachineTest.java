import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for VendingMachine.
 *
 * Note: getItem(String) is protected, so this test class must be in the same
 * package as VendingMachine (here, the default package).
 */
@DisplayName("VendingMachine")
public class VendingMachineTest {

	private static final double DELTA = 0.0001;

	private VendingMachine machine;
	private VendingMachineItem chips;
	private VendingMachineItem soda;

	@BeforeEach
	void setUp() {
		machine = new VendingMachine();
		chips = new VendingMachineItem("Chips", 1.50);
		soda = new VendingMachineItem("Soda", 2.00);
	}

	// =====================================================================
	// Constructor
	// =====================================================================

	@Test
	@DisplayName("New machine starts with a balance of 0")
	void constructor_newMachine_balanceIsZero() {
		// Arrange (done in setUp)

		// Act
		double balance = machine.getBalance();

		// Assert
		assertEquals(0.0, balance, DELTA);
	}

	@Test
	@DisplayName("New machine starts with all four slots empty")
	void constructor_newMachine_allSlotsEmpty() {
		// Arrange (done in setUp)

		// Act + Assert
		assertNull(machine.getItem(VendingMachine.A_CODE));
		assertNull(machine.getItem(VendingMachine.B_CODE));
		assertNull(machine.getItem(VendingMachine.C_CODE));
		assertNull(machine.getItem(VendingMachine.D_CODE));
	}

	@Test
	@DisplayName("Public constants match the documented values")
	void constants_haveDocumentedValues() {
		// Arrange / Act (static constants)

		// Assert
		assertEquals(4, VendingMachine.NUM_SLOTS);
		assertEquals("A", VendingMachine.A_CODE);
		assertEquals("B", VendingMachine.B_CODE);
		assertEquals("C", VendingMachine.C_CODE);
		assertEquals("D", VendingMachine.D_CODE);
	}

	// =====================================================================
	// addItem
	// =====================================================================

	@Test
	@DisplayName("addItem puts the item in the requested empty slot")
	void addItem_emptySlot_itemIsStoredInThatSlot() {
		// Arrange
		String code = VendingMachine.A_CODE;

		// Act
		machine.addItem(chips, code);

		// Assert
		assertSame(chips, machine.getItem(code));
	}

	@Test
	@DisplayName("addItem works for every valid code A-D")
	void addItem_allFourValidCodes_eachSlotHoldsItsItem() {
		// Arrange
		VendingMachineItem a = new VendingMachineItem("A-item", 1.00);
		VendingMachineItem b = new VendingMachineItem("B-item", 1.00);
		VendingMachineItem c = new VendingMachineItem("C-item", 1.00);
		VendingMachineItem d = new VendingMachineItem("D-item", 1.00);

		// Act
		machine.addItem(a, "A");
		machine.addItem(b, "B");
		machine.addItem(c, "C");
		machine.addItem(d, "D");

		// Assert
		assertSame(a, machine.getItem("A"));
		assertSame(b, machine.getItem("B"));
		assertSame(c, machine.getItem("C"));
		assertSame(d, machine.getItem("D"));
	}

	@Test
	@DisplayName("addItem to one slot does not affect the other slots")
	void addItem_oneSlot_otherSlotsStayEmpty() {
		// Arrange
		String code = VendingMachine.C_CODE;

		// Act
		machine.addItem(chips, code);

		// Assert
		assertNull(machine.getItem("A"));
		assertNull(machine.getItem("B"));
		assertNull(machine.getItem("D"));
	}

	@Test
	@DisplayName("addItem to an occupied slot throws VendingMachineException")
	void addItem_occupiedSlot_throwsVendingMachineException() {
		// Arrange
		machine.addItem(chips, "A");

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.addItem(soda, "A"));
	}

	@Test
	@DisplayName("addItem to an occupied slot leaves the original item in place")
	void addItem_occupiedSlot_originalItemNotReplaced() {
		// Arrange
		machine.addItem(chips, "A");

		// Act
		assertThrows(VendingMachineException.class,
				() -> machine.addItem(soda, "A"));

		// Assert
		assertSame(chips, machine.getItem("A"));
	}

	@Test
	@DisplayName("addItem with a code outside A-D (\"E\") throws")
	void addItem_codeE_throwsVendingMachineException() {
		// Arrange
		String invalidCode = "E";

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.addItem(chips, invalidCode));
	}

	@Test
	@DisplayName("addItem with a lowercase code (\"a\") throws - codes are case-sensitive")
	void addItem_lowercaseCode_throwsVendingMachineException() {
		// Arrange
		String invalidCode = "a";

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.addItem(chips, invalidCode));
	}

	@Test
	@DisplayName("addItem with an empty-string code throws")
	void addItem_emptyCode_throwsVendingMachineException() {
		// Arrange
		String invalidCode = "";

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.addItem(chips, invalidCode));
	}

	// =====================================================================
	// getItem
	// =====================================================================

	@Test
	@DisplayName("getItem returns the item stored in the slot")
	void getItem_occupiedSlot_returnsItem() {
		// Arrange
		machine.addItem(soda, "D");

		// Act
		VendingMachineItem result = machine.getItem("D");

		// Assert
		assertSame(soda, result);
	}

	@Test
	@DisplayName("getItem does not remove the item from the slot")
	void getItem_occupiedSlot_itemStaysInSlot() {
		// Arrange
		machine.addItem(soda, "D");

		// Act
		machine.getItem("D");

		// Assert
		assertSame(soda, machine.getItem("D"));
	}

	@Test
	@DisplayName("getItem on an empty slot returns null")
	void getItem_emptySlot_returnsNull() {
		// Arrange (machine is empty)

		// Act
		VendingMachineItem result = machine.getItem("B");

		// Assert
		assertNull(result);
	}

	@Test
	@DisplayName("getItem with an invalid code throws VendingMachineException")
	void getItem_invalidCode_throwsVendingMachineException() {
		// Arrange
		String invalidCode = "AA";

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.getItem(invalidCode));
	}

	// =====================================================================
	// removeItem
	// =====================================================================

	@Test
	@DisplayName("removeItem returns the item that was in the slot")
	void removeItem_occupiedSlot_returnsItem() {
		// Arrange
		machine.addItem(chips, "A");

		// Act
		VendingMachineItem removed = machine.removeItem("A");

		// Assert
		assertSame(chips, removed);
	}

	@Test
	@DisplayName("removeItem leaves the slot empty afterward")
	void removeItem_occupiedSlot_slotBecomesEmpty() {
		// Arrange
		machine.addItem(chips, "A");

		// Act
		machine.removeItem("A");

		// Assert
		assertNull(machine.getItem("A"));
	}

	@Test
	@DisplayName("A slot can be refilled after its item is removed")
	void removeItem_thenAddItem_slotAcceptsNewItem() {
		// Arrange
		machine.addItem(chips, "A");
		machine.removeItem("A");

		// Act
		machine.addItem(soda, "A");

		// Assert
		assertSame(soda, machine.getItem("A"));
	}

	@Test
	@DisplayName("removeItem does not change the balance")
	void removeItem_withMoneyInserted_balanceUnchanged() {
		// Arrange
		machine.addItem(chips, "A");
		machine.insertMoney(3.00);

		// Act
		machine.removeItem("A");

		// Assert
		assertEquals(3.00, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("removeItem on an empty slot throws VendingMachineException")
	void removeItem_emptySlot_throwsVendingMachineException() {
		// Arrange (slot C is empty)

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.removeItem("C"));
	}

	@Test
	@DisplayName("Removing the same item twice throws on the second removal")
	void removeItem_calledTwice_secondCallThrows() {
		// Arrange
		machine.addItem(chips, "B");
		machine.removeItem("B");

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.removeItem("B"));
	}

	@Test
	@DisplayName("removeItem with an invalid code throws VendingMachineException")
	void removeItem_invalidCode_throwsVendingMachineException() {
		// Arrange
		String invalidCode = "X";

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.removeItem(invalidCode));
	}

	// =====================================================================
	// insertMoney / getBalance
	// =====================================================================

	@Test
	@DisplayName("insertMoney increases the balance by the amount")
	void insertMoney_positiveAmount_balanceIncreases() {
		// Arrange
		double amount = 1.75;

		// Act
		machine.insertMoney(amount);

		// Assert
		assertEquals(1.75, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Multiple insertions accumulate in the balance")
	void insertMoney_multipleAmounts_balanceIsSum() {
		// Arrange
		double first = 1.00;
		double second = 0.25;
		double third = 0.10;

		// Act
		machine.insertMoney(first);
		machine.insertMoney(second);
		machine.insertMoney(third);

		// Assert
		assertEquals(1.35, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Inserting exactly 0 is allowed and leaves the balance unchanged")
	void insertMoney_zero_balanceUnchanged() {
		// Arrange
		machine.insertMoney(2.00);

		// Act
		machine.insertMoney(0.0);

		// Assert
		assertEquals(2.00, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Inserting 0 does not throw")
	void insertMoney_zero_doesNotThrow() {
		// Arrange
		double amount = 0.0;

		// Act + Assert
		assertDoesNotThrow(() -> machine.insertMoney(amount));
	}

	@Test
	@DisplayName("Inserting a negative amount throws VendingMachineException")
	void insertMoney_negativeAmount_throwsVendingMachineException() {
		// Arrange
		double amount = -1.00;

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.insertMoney(amount));
	}

	@Test
	@DisplayName("Inserting just below 0 (-0.01) throws VendingMachineException")
	void insertMoney_justBelowZero_throwsVendingMachineException() {
		// Arrange
		double amount = -0.01;

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.insertMoney(amount));
	}

	@Test
	@DisplayName("A rejected negative insertion leaves the balance unchanged")
	void insertMoney_negativeAmount_balanceUnchanged() {
		// Arrange
		machine.insertMoney(5.00);

		// Act
		assertThrows(VendingMachineException.class,
				() -> machine.insertMoney(-2.00));

		// Assert
		assertEquals(5.00, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("getBalance does not change the balance when called repeatedly")
	void getBalance_calledTwice_returnsSameValue() {
		// Arrange
		machine.insertMoney(4.00);

		// Act
		double first = machine.getBalance();
		double second = machine.getBalance();

		// Assert
		assertEquals(first, second, DELTA);
		assertEquals(4.00, second, DELTA);
	}

	// =====================================================================
	// makePurchase
	// =====================================================================

	@Test
	@DisplayName("Purchase with more than enough money returns true")
	void makePurchase_balanceAbovePrice_returnsTrue() {
		// Arrange
		machine.addItem(chips, "A"); // 1.50
		machine.insertMoney(2.00);

		// Act
		boolean result = machine.makePurchase("A");

		// Assert
		assertTrue(result);
	}

	@Test
	@DisplayName("Successful purchase subtracts the price from the balance")
	void makePurchase_balanceAbovePrice_balanceReducedByPrice() {
		// Arrange
		machine.addItem(chips, "A"); // 1.50
		machine.insertMoney(2.00);

		// Act
		machine.makePurchase("A");

		// Assert
		assertEquals(0.50, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Successful purchase empties the slot")
	void makePurchase_success_slotBecomesEmpty() {
		// Arrange
		machine.addItem(chips, "A");
		machine.insertMoney(2.00);

		// Act
		machine.makePurchase("A");

		// Assert
		assertNull(machine.getItem("A"));
	}

	@Test
	@DisplayName("A slot emptied by a purchase can be refilled (addItem no longer sees it as occupied)")
	void makePurchase_success_slotCanBeRefilled() {
		// Arrange
		machine.addItem(chips, "A");
		machine.insertMoney(2.00);
		machine.makePurchase("A");

		// Act + Assert
		assertDoesNotThrow(() -> machine.addItem(soda, "A"));
	}

	@Test
	@DisplayName("Removing from a slot emptied by a purchase throws (slot is empty)")
	void makePurchase_success_removeItemOnSameSlotThrows() {
		// Arrange
		machine.addItem(chips, "A");
		machine.insertMoney(2.00);
		machine.makePurchase("A");

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.removeItem("A"));
	}

	@Test
	@DisplayName("Purchase with balance exactly equal to price succeeds")
	void makePurchase_balanceEqualsPrice_returnsTrueAndBalanceIsZero() {
		// Arrange
		machine.addItem(soda, "B"); // 2.00
		machine.insertMoney(2.00);

		// Act
		boolean result = machine.makePurchase("B");

		// Assert
		assertTrue(result);
		assertEquals(0.0, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchase with balance one cent short fails")
	void makePurchase_balanceOneCentShort_returnsFalse() {
		// Arrange
		machine.addItem(soda, "B"); // 2.00
		machine.insertMoney(1.99);

		// Act
		boolean result = machine.makePurchase("B");

		// Assert
		assertFalse(result);
	}

	@Test
	@DisplayName("Failed purchase (not enough money) keeps the item and the balance")
	void makePurchase_insufficientFunds_itemAndBalanceUnchanged() {
		// Arrange
		machine.addItem(soda, "B"); // 2.00
		machine.insertMoney(1.00);

		// Act
		machine.makePurchase("B");

		// Assert
		assertSame(soda, machine.getItem("B"));
		assertEquals(1.00, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchase with zero balance fails for a priced item")
	void makePurchase_zeroBalance_returnsFalse() {
		// Arrange
		machine.addItem(chips, "A");

		// Act
		boolean result = machine.makePurchase("A");

		// Assert
		assertFalse(result);
	}

	@Test
	@DisplayName("A free (price 0) item can be bought with zero balance")
	void makePurchase_freeItemWithZeroBalance_returnsTrue() {
		// Arrange
		VendingMachineItem freebie = new VendingMachineItem("Sample", 0.0);
		machine.addItem(freebie, "C");

		// Act
		boolean result = machine.makePurchase("C");

		// Assert
		assertTrue(result);
		assertNull(machine.getItem("C"));
		assertEquals(0.0, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchase from an empty slot returns false")
	void makePurchase_emptySlot_returnsFalse() {
		// Arrange
		machine.insertMoney(5.00);

		// Act
		boolean result = machine.makePurchase("D");

		// Assert
		assertFalse(result);
	}

	@Test
	@DisplayName("Purchase from an empty slot does not change the balance")
	void makePurchase_emptySlot_balanceUnchanged() {
		// Arrange
		machine.insertMoney(5.00);

		// Act
		machine.makePurchase("D");

		// Assert
		assertEquals(5.00, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("The same item cannot be bought twice")
	void makePurchase_sameSlotTwice_secondPurchaseFails() {
		// Arrange
		machine.addItem(chips, "A"); // 1.50
		machine.insertMoney(5.00);
		machine.makePurchase("A");

		// Act
		boolean second = machine.makePurchase("A");

		// Assert
		assertFalse(second);
		assertEquals(3.50, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Buying from two slots subtracts both prices")
	void makePurchase_twoDifferentSlots_bothPricesSubtracted() {
		// Arrange
		machine.addItem(chips, "A"); // 1.50
		machine.addItem(soda, "B"); // 2.00
		machine.insertMoney(5.00);

		// Act
		boolean first = machine.makePurchase("A");
		boolean second = machine.makePurchase("B");

		// Assert
		assertTrue(first);
		assertTrue(second);
		assertEquals(1.50, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("Purchase does not affect items in other slots")
	void makePurchase_oneSlot_otherSlotsUntouched() {
		// Arrange
		machine.addItem(chips, "A");
		machine.addItem(soda, "B");
		machine.insertMoney(5.00);

		// Act
		machine.makePurchase("A");

		// Assert
		assertSame(soda, machine.getItem("B"));
	}

	// =====================================================================
	// returnChange
	// =====================================================================

	@Test
	@DisplayName("returnChange returns the current balance")
	void returnChange_withBalance_returnsBalance() {
		// Arrange
		machine.insertMoney(3.25);

		// Act
		double change = machine.returnChange();

		// Assert
		assertEquals(3.25, change, DELTA);
	}

	@Test
	@DisplayName("returnChange sets the balance to 0")
	void returnChange_withBalance_balanceBecomesZero() {
		// Arrange
		machine.insertMoney(3.25);

		// Act
		machine.returnChange();

		// Assert
		assertEquals(0.0, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("returnChange on a new machine returns 0")
	void returnChange_zeroBalance_returnsZero() {
		// Arrange (new machine)

		// Act
		double change = machine.returnChange();

		// Assert
		assertEquals(0.0, change, DELTA);
	}

	@Test
	@DisplayName("Calling returnChange twice returns 0 the second time")
	void returnChange_calledTwice_secondCallReturnsZero() {
		// Arrange
		machine.insertMoney(2.00);
		machine.returnChange();

		// Act
		double second = machine.returnChange();

		// Assert
		assertEquals(0.0, second, DELTA);
	}

	@Test
	@DisplayName("returnChange after a purchase returns the leftover amount")
	void returnChange_afterPurchase_returnsRemainingBalance() {
		// Arrange
		machine.addItem(chips, "A"); // 1.50
		machine.insertMoney(2.00);
		machine.makePurchase("A");

		// Act
		double change = machine.returnChange();

		// Assert
		assertEquals(0.50, change, DELTA);
		assertEquals(0.0, machine.getBalance(), DELTA);
	}

	@Test
	@DisplayName("returnChange does not remove items from the machine")
	void returnChange_withItems_itemsStayInMachine() {
		// Arrange
		machine.addItem(chips, "A");
		machine.insertMoney(1.00);

		// Act
		machine.returnChange();

		// Assert
		assertSame(chips, machine.getItem("A"));
	}
}
