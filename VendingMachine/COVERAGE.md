# Coverage Report – VendingMachine

## Initial Coverage
| File | Statement/Line | Branch |
|---|---|---|
| VendingMachine.java | 100% | 20/20 |
| VendingMachineItem.java | 100% | 2/2 |

## Final Coverage
Same as initial. My Project 2 tests already hit 100% statement and branch coverage,
so I didn't add any tests just to raise the numbers.

## Condition-Coverage Check
Decision in makePurchase(): (item != null) && (this.balance >= item.getPrice())

(item != null) is true in the makePurchase test cases of balanceAbovePrice, balanceEqualToPrice, and balanceBelowPrice. It is false in emptySlot.

(balance >= price) is true in the makePurchase test cases of balanceAbovePrice and balanceEqualToPrice. It is false in balanceBelowPrice. The condition is not reached in emptySlot since (item != null) is false so the entire decision is skipped after that.

## Test-Sensitivity Experiment
I changed >= to > in makePurchase(). testMakePurchase_balanceEqualToPrice
failed because it uses a balance exactly equal to the price, so the purchase was
wrongly rejected. I committed the faulty version, then removed the fault, reran the
tests, and they all passed again.

Screenshot: testResults/CoverageExperiment.png (injected fault), testResults/FinalCoverage.png (final coverage)