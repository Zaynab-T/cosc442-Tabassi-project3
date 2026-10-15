# JoshHack Coverage – Inventory.java

## Baseline Coverage
JoshHack didn't come with any tests, so the baseline coverage for Inventory.java was 0%.

| File | Statement/Line | Branch |
|---|---|---|
| Inventory.java | 0% | 0% |

## Final Coverage
| File | Statement/Line | Branch |
|---|---|---|
| Inventory.java | 100% | 100% (18/18) |

## Tests Added
I added InventoryTest.java in the game package with 14 tests:

- **Constructor:** a new inventory has the right number of empty slots, and an inventory of size 0 counts as full and won't take items.
- **add:** the first item goes in slot 0, the next item skips the filled slot and goes in the next open one, adding to a full inventory does nothing, and after a remove the new item fills the gap.
- **remove:** removing an item empties its slot, if the same item is in there twice only the first copy gets removed, and removing something that isn't in the inventory doesn't change anything.
- **isFull:** false when empty, false when partly filled, true when every slot is filled.
- **contains:** true when the item is there, false when it isn't.

These hit both sides of every if statement and loop, including the cases where a loop goes all the way through without finding anything (adding to a full inventory, removing an item that isn't there, and contains returning false).

## Remaining Uncovered Behavior
Nothing in Inventory.java is left uncovered.

One thing I noticed but didn't write a test for: contains(null) returns true whenever the inventory has an empty slot, since empty slots are stored as null. There's no documentation saying if that's intended, so I left it out instead of writing a test that just locks in whatever the code does.

Screenshot: testresults/coverage.png