# AI Test Review

**AI used: Claude** 
**Link: https://claude.ai/share/39596b2b-0a5e-4a20-8612-4a95d7ac352a **

**Changes made to compile: None**

## Coverage Results
| Suite | # Tests | Line | Branch |
|---|---|---|---|
| Manual (Task 1) | 41 | 100% | 100% |
| Initial AI | 66 | 100% | 100% |
| AI after follow-up | 68 | 100% | 100% |

## Reflection
Overall the AI's tests were better than I expected. They followed Arrange-Act-Assert, had descriptive names, and covered a lot of the same boundary cases I tested in
Project 2. They also checked side effects, like making sure the item and balance don't change after a failed purchase. The downside was that it made 66 tests compared to my 41, and a lot of them were repetitive. The four invalid code tests ("E", "a", "", "Z") and the negative amount tests could have each been one parameterized test, and a few tests, like checking the constants or calling getBalance() twice, don't really test anything useful.

The biggest problem I noticed was that the AI wrote some tests based on what the code does instead of what the Javadocs say. For example, addItem_nullCode_throwsNullPointerException expects a NullPointerException, and its display name even says "code.equals on null," so it clearly got that from reading the implementation. The exception message tests also copied the exact strings from the source. I noticed this by comparing the tests to the Javadocs instead of the code. Since I gave it the fixed code from Project 2, its insertMoney tests with 0.0 and 0.25 would have caught my bug 2, but because it bases expected results on the implementation, I don't think it would have caught it
if I'd given it the buggy version. When I asked it in the follow up to compare against the Javadocs, it did notice this and moved those tests into a separate file labeled as
undocumented behavior. But it only caught it after I pointed it at the Javadocs. The first version just treated the code as the spec.

Both the AI suite and my manual suite got 100% line and branch coverage right away, so the coverage guided prompt couldn't really point it at anything uncovered. When I asked it to compare its tests against the Javadocs instead, it moved 7 tests that relied on
undocumented behavior into their own file, and added 2 tests checking that a slot is empty after a purchase using only documented methods. That was the one useful improvement, but it still kept the duplicate tests like the two zero insert tests and the constants test, and never switched to parameterized tests. Coverage stayed at 100% the whole time, which showed me that coverage only tells you the code ran, not whether
the tests check the right thing. AI is useful for quickly generating a lot of tests, but you still have to check every test against the spec yourself, or you end up with
tests that just agree with whatever the code already does.