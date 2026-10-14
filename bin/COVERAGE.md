# Coverage Report – VendingMachine

## Initial Coverage
| File | Statement/Line | Branch |
|---|---|---|
| VendingMachine.java | 100% | 20/20 |
| VendingMachineItem.java | 100% | [2/2] |

## Final Coverage
Same as initial. My Project 2 tests already hit 100% statement and branch coverage,
so I didn't add any tests just to raise the numbers.

## Condition-Coverage Check
[paste the short version from earlier]

## Test-Sensitivity Experiment
I changed >= to > in makePurchase(). testMakePurchase_balanceEqualToPrice
failed because it uses a balance exactly equal to the price, so the purchase was
wrongly rejected. I committed the faulty version, then removed the fault, reran the
tests, and they all passed again.

Screenshot: testResults/CoverageExperiment.png