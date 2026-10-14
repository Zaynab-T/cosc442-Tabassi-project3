import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for VendingMachineItem.
 */
@DisplayName("VendingMachineItem")
public class VendingMachineItemTest {

	private static final double DELTA = 0.0001;

	// ---------- Normal cases ----------

	@Test
	@DisplayName("Constructor stores the name passed in")
	void constructor_validNameAndPrice_getNameReturnsName() {
		// Arrange
		String name = "Chips";
		double price = 1.25;

		// Act
		VendingMachineItem item = new VendingMachineItem(name, price);

		// Assert
		assertEquals("Chips", item.getName());
	}

	@Test
	@DisplayName("Constructor stores the price passed in")
	void constructor_validNameAndPrice_getPriceReturnsPrice() {
		// Arrange
		String name = "Soda";
		double price = 2.50;

		// Act
		VendingMachineItem item = new VendingMachineItem(name, price);

		// Assert
		assertEquals(2.50, item.getPrice(), DELTA);
	}

	// ---------- Boundary cases ----------

	@Test
	@DisplayName("A price of exactly 0 is allowed")
	void constructor_zeroPrice_createsItemWithZeroPrice() {
		// Arrange
		double price = 0.0;

		// Act
		VendingMachineItem item = new VendingMachineItem("Free Sample", price);

		// Assert
		assertEquals(0.0, item.getPrice(), DELTA);
	}

	@Test
	@DisplayName("A very small positive price (one cent) is allowed")
	void constructor_oneCentPrice_createsItem() {
		// Arrange
		double price = 0.01;

		// Act
		VendingMachineItem item = new VendingMachineItem("Gum", price);

		// Assert
		assertEquals(0.01, item.getPrice(), DELTA);
	}

	@Test
	@DisplayName("A very large price is allowed")
	void constructor_largePrice_createsItem() {
		// Arrange
		double price = 1_000_000.00;

		// Act
		VendingMachineItem item = new VendingMachineItem("Gold Bar", price);

		// Assert
		assertEquals(1_000_000.00, item.getPrice(), DELTA);
	}

	@Test
	@DisplayName("An empty string name is stored as-is")
	void constructor_emptyName_getNameReturnsEmptyString() {
		// Arrange
		String name = "";

		// Act
		VendingMachineItem item = new VendingMachineItem(name, 1.00);

		// Assert
		assertEquals("", item.getName());
	}

	@Test
	@DisplayName("A null name is stored as-is (no validation on name)")
	void constructor_nullName_getNameReturnsNull() {
		// Arrange
		String name = null;

		// Act
		VendingMachineItem item = new VendingMachineItem(name, 1.00);

		// Assert
		assertNull(item.getName());
	}

	// ---------- Invalid input / exception cases ----------

	@Test
	@DisplayName("A negative price throws VendingMachineException")
	void constructor_negativePrice_throwsVendingMachineException() {
		// Arrange
		double price = -1.00;

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> new VendingMachineItem("Candy", price));
	}

	@Test
	@DisplayName("A price just below 0 (-0.01) throws VendingMachineException")
	void constructor_priceJustBelowZero_throwsVendingMachineException() {
		// Arrange
		double price = -0.01;

		// Act + Assert
		assertThrows(VendingMachineException.class,
				() -> new VendingMachineItem("Candy", price));
	}

	@Test
	@DisplayName("A price of 0 does not throw")
	void constructor_zeroPrice_doesNotThrow() {
		// Arrange
		double price = 0.0;

		// Act + Assert
		assertDoesNotThrow(() -> new VendingMachineItem("Water", price));
	}
}
