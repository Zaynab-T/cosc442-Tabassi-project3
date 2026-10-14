import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Characterization tests: these pin down what the CURRENT code does in
 * situations the Javadocs do not specify (exact exception messages, null
 * codes, invalid codes passed to makePurchase).
 *
 * They are kept separate from the specification-based tests on purpose. If the
 * implementation changes in a way that still satisfies the Javadocs, these
 * tests may fail even though nothing documented was broken.
 */
@DisplayName("VendingMachine / VendingMachineItem - undocumented behavior")
public class VendingMachineUndocumentedBehaviorTest {

	private VendingMachine machine;
	private VendingMachineItem chips;
	private VendingMachineItem soda;

	@BeforeEach
	void setUp() {
		machine = new VendingMachine();
		chips = new VendingMachineItem("Chips", 1.50);
		soda = new VendingMachineItem("Soda", 2.00);
	}

	// ---------- Exception messages (private constants, not in the Javadocs) ----------

	@Test
	@DisplayName("Occupied-slot exception message names the slot")
	void addItem_occupiedSlot_exceptionMessageNamesSlot() {
		// Arrange
		machine.addItem(chips, "B");

		// Act
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> machine.addItem(soda, "B"));

		// Assert
		assertEquals("Slot B already occupied", ex.getMessage());
	}

	@Test
	@DisplayName("Invalid-code exception has the current message")
	void addItem_invalidCode_exceptionHasCurrentMessage() {
		// Arrange
		String invalidCode = "Z";

		// Act
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> machine.addItem(chips, invalidCode));

		// Assert
		assertEquals("Invalid code for vending machine item", ex.getMessage());
	}

	@Test
	@DisplayName("Empty-slot exception message names the slot")
	void removeItem_emptySlot_exceptionMessageNamesSlot() {
		// Arrange (slot C is empty)

		// Act
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> machine.removeItem("C"));

		// Assert
		assertEquals("Slot C is empty -- cannot remove item", ex.getMessage());
	}

	@Test
	@DisplayName("Negative-amount exception has the current message")
	void insertMoney_negativeAmount_exceptionHasCurrentMessage() {
		// Arrange
		double amount = -3.00;

		// Act
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> machine.insertMoney(amount));

		// Assert
		assertEquals("Invalid amount.  Amount must be >= 0", ex.getMessage());
	}

	@Test
	@DisplayName("Negative-price exception has the current message")
	void itemConstructor_negativePrice_exceptionHasCurrentMessage() {
		// Arrange
		double price = -5.00;

		// Act
		VendingMachineException ex = assertThrows(VendingMachineException.class,
				() -> new VendingMachineItem("Candy", price));

		// Assert
		assertEquals("Price cannot be less than zero", ex.getMessage());
	}

	// ---------- Inputs the Javadocs never mention ----------

	@Test
	@DisplayName("A null code currently throws NullPointerException, not VendingMachineException")
	void addItem_nullCode_currentlyThrowsNullPointerException() {
		// Arrange
		String nullCode = null;

		// Act + Assert
		assertThrows(NullPointerException.class,
				() -> machine.addItem(chips, nullCode));
	}

	@Test
	@DisplayName("makePurchase with an invalid code currently throws instead of returning false")
	void makePurchase_invalidCode_currentlyThrowsVendingMachineException() {
		// Arrange
		machine.insertMoney(5.00);

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> machine.makePurchase("Q"));
	}
}
