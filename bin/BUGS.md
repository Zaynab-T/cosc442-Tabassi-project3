# BUGS

## Bug 1: VendingMachine constructor loop goes past the end of the array

**Observed failure:** new VendingMachine() threw an ArrayIndexOutOfBoundsException so every test that created a machine failed on that line.

**Test that exposed it:** testConstructor_balanceIsZero and testConstructor_allSlotsEmpty in VendingMachineTest.

**Source-code fault:** In the VendingMachine() constructor the loop condition was i <= NUM_SLOTS. With NUM_SLOTS = 4, i reached 4, but the array only has indexes 0 to 3.

**How I diagnosed it:** I read the failure message, which pointed at the constructor line, then compared the loop's range of i values (0 to 4) to the array's valid indexes (0 to 3).

**Correction:** Changed the condition to i < NUM_SLOTS.

## Bug 2: insertMoney rejects valid amounts below 1

**Observed failure:** insertMoney threw a VendingMachineException for amounts like 0.0, 0.01, 0.25, and 0.99, even though the Javadoc only allows rejecting amounts less than 0.

**Test that exposed it:** testInsertMoney_validAmount (failed for the first four values: 0.0, 0.01, 0.25, 0.99) and testInsertMoney_twoInserts in VendingMachineTest.

**Source-code fault:** In insertMoney, the validation condition was if (amount < 1). The Javadoc precondition is amount >= 0, so the boundary should be 0, not 1.

**How I diagnosed it:** I compared which parameterized values passed (1.0, 5.0) and failed (0.0, 0.01, 0.25, 0.99), which showed the code was rejecting everything below 1. I then compared the condition in the source with the Javadoc's precondition and @throws line.

**Correction:** Changed the condition to if (amount < 0).