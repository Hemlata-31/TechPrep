package com.techprep.config;

import com.techprep.entity.CodingProblem;
import com.techprep.entity.CodingTestCase;
import com.techprep.entity.Difficulty;
import com.techprep.repository.CodingProblemRepository;
import com.techprep.repository.CodingTestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Order(10)
@RequiredArgsConstructor
public class CodingProblemSeeder implements CommandLineRunner {

    private final CodingProblemRepository problemRepository;
    private final CodingTestCaseRepository testCaseRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (problemRepository.count() > 0) {
            return;
        }
        seedTwoSum();
        seedValidParentheses();
        seedBestTimeToBuyAndSellStock();
        seedMaximumSubarray();
        seedBinarySearch();
        seedContainsDuplicate();
        seedValidAnagram();
        seedClimbingStairs();
        seedMoveZeroes();
        seedPalindromeNumber();
        System.out.println("Seeded 10 classic DSA coding problems.");
    }

    private void saveProblem(CodingProblem problem, List<CodingTestCase> cases) {
        CodingProblem saved = problemRepository.save(problem);
        cases.forEach(c -> c.setProblem(saved));
        testCaseRepository.saveAll(cases);
    }

    private CodingTestCase tc(String input, String expected, String displayInput, String explanation, boolean sample) {
        return CodingTestCase.builder()
                .input(input)
                .expectedOutput(expected)
                .displayInput(displayInput)
                .explanation(explanation)
                .sample(sample)
                .build();
    }

    private void seedTwoSum() {
        saveProblem(CodingProblem.builder()
                .title("1. Two Sum")
                .slug("two-sum")
                .difficulty(Difficulty.EASY)
                .categoryTag("Array")
                .description("""
                        Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.

                        You may assume that each input would have **exactly one solution**, and you may not use the same element twice.
                        Return the answer with the smaller index first.""")
                .constraintsText("2 <= nums.length <= 10^4\n-10^9 <= nums[i] <= 10^9\n-10^9 <= target <= 10^9")
                .starterCodeJava("""
                        class Solution {
                            public int[] twoSum(int[] nums, int target) {
                                // Write your code here
                                return new int[]{};
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def twoSum(self, nums: List[int], target: int) -> List[int]:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            vector<int> twoSum(vector<int>& nums, int target) {
                                // Write your code here
                                return {};
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                int[] nums = new int[n];
                                for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
                                int target = sc.nextInt();
                                int[] res = new Solution().twoSum(nums, target);
                                Arrays.sort(res);
                                System.out.println(res[0] + " " + res[1]);
                            }
                        }""")
                .driverCodePython("""
                        from typing import List
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            data = list(map(int, sys.stdin.read().split()))
                            n = data[0]
                            nums = data[1:1+n]
                            target = data[1+n]
                            res = sorted(Solution().twoSum(nums, target))
                            print(res[0], res[1])""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            vector<int> nums(n);
                            for (int i = 0; i < n; i++) cin >> nums[i];
                            int target; cin >> target;
                            vector<int> res = Solution().twoSum(nums, target);
                            sort(res.begin(), res.end());
                            cout << res[0] << " " << res[1] << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("4\n2 7 11 15\n9", "0 1", "nums = [2,7,11,15], target = 9", "Because nums[0] + nums[1] == 9, we return [0, 1].", true),
                        tc("3\n3 2 4\n6", "1 2", "nums = [3,2,4], target = 6", "nums[1] + nums[2] == 6.", true),
                        tc("2\n3 3\n6", "0 1", null, null, false),
                        tc("5\n-1 -2 -3 -4 -5\n-8", "2 4", null, null, false)
                ));
    }

    private void seedValidParentheses() {
        saveProblem(CodingProblem.builder()
                .title("2. Valid Parentheses")
                .slug("valid-parentheses")
                .difficulty(Difficulty.EASY)
                .categoryTag("Stack")
                .description("""
                        Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

                        An input string is valid if:
                        1. Open brackets must be closed by the same type of brackets.
                        2. Open brackets must be closed in the correct order.
                        3. Every close bracket has a corresponding open bracket of the same type.""")
                .constraintsText("1 <= s.length <= 10^4\ns consists of parentheses only '()[]{}'.")
                .starterCodeJava("""
                        class Solution {
                            public boolean isValid(String s) {
                                // Write your code here
                                return false;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def isValid(self, s: str) -> bool:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            bool isValid(string s) {
                                // Write your code here
                                return false;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                String s = sc.next();
                                System.out.println(new Solution().isValid(s));
                            }
                        }""")
                .driverCodePython("""
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            s = sys.stdin.read().strip()
                            print("true" if Solution().isValid(s) else "false")""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            string s; cin >> s;
                            cout << (Solution().isValid(s) ? "true" : "false") << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("()", "true", "s = \"()\"", "Matching pair of parentheses.", true),
                        tc("()[]{}", "true", "s = \"()[]{}\"", "All brackets match in order.", true),
                        tc("(]", "false", "s = \"(]\"", "Mismatched bracket types.", true),
                        tc("([)]", "false", null, null, false),
                        tc("{[]}", "true", null, null, false)
                ));
    }

    private void seedBestTimeToBuyAndSellStock() {
        saveProblem(CodingProblem.builder()
                .title("3. Best Time to Buy and Sell Stock")
                .slug("best-time-to-buy-and-sell-stock")
                .difficulty(Difficulty.EASY)
                .categoryTag("Array")
                .description("""
                        You are given an array `prices` where `prices[i]` is the price of a given stock on the `i`-th day.

                        You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.
                        Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return `0`.""")
                .constraintsText("1 <= prices.length <= 10^5\n0 <= prices[i] <= 10^4")
                .starterCodeJava("""
                        class Solution {
                            public int maxProfit(int[] prices) {
                                // Write your code here
                                return 0;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def maxProfit(self, prices: List[int]) -> int:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            int maxProfit(vector<int>& prices) {
                                // Write your code here
                                return 0;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                int[] prices = new int[n];
                                for (int i = 0; i < n; i++) prices[i] = sc.nextInt();
                                System.out.println(new Solution().maxProfit(prices));
                            }
                        }""")
                .driverCodePython("""
                        from typing import List
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            data = list(map(int, sys.stdin.read().split()))
                            prices = data[1:1+data[0]]
                            print(Solution().maxProfit(prices))""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            vector<int> prices(n);
                            for (int i = 0; i < n; i++) cin >> prices[i];
                            cout << Solution().maxProfit(prices) << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("6\n7 1 5 3 6 4", "5", "prices = [7,1,5,3,6,4]", "Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6 - 1 = 5.", true),
                        tc("5\n7 6 4 3 1", "0", "prices = [7,6,4,3,1]", "No profitable transaction possible.", true),
                        tc("2\n1 2", "1", null, null, false)
                ));
    }

    private void seedMaximumSubarray() {
        saveProblem(CodingProblem.builder()
                .title("4. Maximum Subarray")
                .slug("maximum-subarray")
                .difficulty(Difficulty.MEDIUM)
                .categoryTag("Dynamic Programming")
                .description("""
                        Given an integer array `nums`, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

                        A subarray is a contiguous part of an array.""")
                .constraintsText("1 <= nums.length <= 10^5\n-10^4 <= nums[i] <= 10^4")
                .starterCodeJava("""
                        class Solution {
                            public int maxSubArray(int[] nums) {
                                // Write your code here (Kadane's Algorithm)
                                return 0;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def maxSubArray(self, nums: List[int]) -> int:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            int maxSubArray(vector<int>& nums) {
                                // Write your code here
                                return 0;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                int[] nums = new int[n];
                                for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
                                System.out.println(new Solution().maxSubArray(nums));
                            }
                        }""")
                .driverCodePython("""
                        from typing import List
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            data = list(map(int, sys.stdin.read().split()))
                            nums = data[1:1+data[0]]
                            print(Solution().maxSubArray(nums))""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            vector<int> nums(n);
                            for (int i = 0; i < n; i++) cin >> nums[i];
                            cout << Solution().maxSubArray(nums) << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("9\n-2 1 -3 4 -1 2 1 -5 4", "6", "nums = [-2,1,-3,4,-1,2,1,-5,4]", "The subarray [4,-1,2,1] has the largest sum 6.", true),
                        tc("1\n1", "1", "nums = [1]", "Single element subarray.", true),
                        tc("5\n5 4 -1 7 8", "23", null, null, false),
                        tc("3\n-3 -2 -5", "-2", null, null, false)
                ));
    }

    private void seedBinarySearch() {
        saveProblem(CodingProblem.builder()
                .title("5. Binary Search")
                .slug("binary-search")
                .difficulty(Difficulty.EASY)
                .categoryTag("Binary Search")
                .description("""
                        Given an array of integers `nums` which is sorted in ascending order, and an integer `target`, write a function to search `target` in `nums`.
                        If `target` exists, then return its index. Otherwise, return `-1`.

                        You must write an algorithm with `O(log n)` runtime complexity.""")
                .constraintsText("1 <= nums.length <= 10^4\nAll integers in nums are unique and sorted in ascending order.")
                .starterCodeJava("""
                        class Solution {
                            public int search(int[] nums, int target) {
                                // Write your code here
                                return -1;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def search(self, nums: List[int], target: int) -> int:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            int search(vector<int>& nums, int target) {
                                // Write your code here
                                return -1;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                int[] nums = new int[n];
                                for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
                                int target = sc.nextInt();
                                System.out.println(new Solution().search(nums, target));
                            }
                        }""")
                .driverCodePython("""
                        from typing import List
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            data = list(map(int, sys.stdin.read().split()))
                            n = data[0]
                            nums = data[1:1+n]
                            target = data[1+n]
                            print(Solution().search(nums, target))""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            vector<int> nums(n);
                            for (int i = 0; i < n; i++) cin >> nums[i];
                            int target; cin >> target;
                            cout << Solution().search(nums, target) << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("6\n-1 0 3 5 9 12\n9", "4", "nums = [-1,0,3,5,9,12], target = 9", "9 exists in nums and its index is 4.", true),
                        tc("6\n-1 0 3 5 9 12\n2", "-1", "nums = [-1,0,3,5,9,12], target = 2", "2 does not exist in nums so return -1.", true),
                        tc("1\n5\n5", "0", null, null, false)
                ));
    }

    private void seedContainsDuplicate() {
        saveProblem(CodingProblem.builder()
                .title("6. Contains Duplicate")
                .slug("contains-duplicate")
                .difficulty(Difficulty.EASY)
                .categoryTag("Hash Table")
                .description("""
                        Given an integer array `nums`, return `true` if any value appears **at least twice** in the array, and return `false` if every element is distinct.""")
                .constraintsText("1 <= nums.length <= 10^5\n-10^9 <= nums[i] <= 10^9")
                .starterCodeJava("""
                        class Solution {
                            public boolean containsDuplicate(int[] nums) {
                                // Write your code here
                                return false;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def containsDuplicate(self, nums: List[int]) -> bool:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            bool containsDuplicate(vector<int>& nums) {
                                // Write your code here
                                return false;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                int[] nums = new int[n];
                                for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
                                System.out.println(new Solution().containsDuplicate(nums));
                            }
                        }""")
                .driverCodePython("""
                        from typing import List
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            data = list(map(int, sys.stdin.read().split()))
                            nums = data[1:1+data[0]]
                            print("true" if Solution().containsDuplicate(nums) else "false")""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            vector<int> nums(n);
                            for (int i = 0; i < n; i++) cin >> nums[i];
                            cout << (Solution().containsDuplicate(nums) ? "true" : "false") << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("4\n1 2 3 1", "true", "nums = [1,2,3,1]", "1 appears twice.", true),
                        tc("4\n1 2 3 4", "false", "nums = [1,2,3,4]", "All elements are distinct.", true),
                        tc("10\n1 1 1 3 3 4 3 2 4 2", "true", null, null, false)
                ));
    }

    private void seedValidAnagram() {
        saveProblem(CodingProblem.builder()
                .title("7. Valid Anagram")
                .slug("valid-anagram")
                .difficulty(Difficulty.EASY)
                .categoryTag("String")
                .description("""
                        Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

                        An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.""")
                .constraintsText("1 <= s.length, t.length <= 5 * 10^4\ns and t consist of lowercase English letters.")
                .starterCodeJava("""
                        class Solution {
                            public boolean isAnagram(String s, String t) {
                                // Write your code here
                                return false;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def isAnagram(self, s: str, t: str) -> bool:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            bool isAnagram(string s, string t) {
                                // Write your code here
                                return false;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                String s = sc.next();
                                String t = sc.next();
                                System.out.println(new Solution().isAnagram(s, t));
                            }
                        }""")
                .driverCodePython("""
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            parts = sys.stdin.read().split()
                            print("true" if Solution().isAnagram(parts[0], parts[1]) else "false")""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            string s, t; cin >> s >> t;
                            cout << (Solution().isAnagram(s, t) ? "true" : "false") << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("anagram\nnagaram", "true", "s = \"anagram\", t = \"nagaram\"", "Both have the exact same character counts.", true),
                        tc("rat\ncar", "false", "s = \"rat\", t = \"car\"", "Different characters.", true),
                        tc("a\nab", "false", null, null, false)
                ));
    }

    private void seedClimbingStairs() {
        saveProblem(CodingProblem.builder()
                .title("8. Climbing Stairs")
                .slug("climbing-stairs")
                .difficulty(Difficulty.EASY)
                .categoryTag("Dynamic Programming")
                .description("""
                        You are climbing a staircase. It takes `n` steps to reach the top.

                        Each time you can either climb `1` or `2` steps. In how many distinct ways can you climb to the top?""")
                .constraintsText("1 <= n <= 45")
                .starterCodeJava("""
                        class Solution {
                            public int climbStairs(int n) {
                                // Write your code here
                                return 0;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def climbStairs(self, n: int) -> int:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            int climbStairs(int n) {
                                // Write your code here
                                return 0;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                System.out.println(new Solution().climbStairs(n));
                            }
                        }""")
                .driverCodePython("""
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            n = int(sys.stdin.read().strip())
                            print(Solution().climbStairs(n))""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            cout << Solution().climbStairs(n) << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("2", "2", "n = 2", "1. 1 step + 1 step\n2. 2 steps", true),
                        tc("3", "3", "n = 3", "1. 1+1+1\n2. 1+2\n3. 2+1", true),
                        tc("5", "8", null, null, false),
                        tc("10", "89", null, null, false)
                ));
    }

    private void seedMoveZeroes() {
        saveProblem(CodingProblem.builder()
                .title("9. Move Zeroes")
                .slug("move-zeroes")
                .difficulty(Difficulty.EASY)
                .categoryTag("Two Pointers")
                .description("""
                        Given an integer array `nums`, move all `0`'s to the end of it while maintaining the relative order of the non-zero elements.

                        Note that you must do this **in-place** without making a copy of the array.""")
                .constraintsText("1 <= nums.length <= 10^4\n-2^31 <= nums[i] <= 2^31 - 1")
                .starterCodeJava("""
                        class Solution {
                            public void moveZeroes(int[] nums) {
                                // Write your code here
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def moveZeroes(self, nums: List[int]) -> None:
                                # Modify nums in-place
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            void moveZeroes(vector<int>& nums) {
                                // Write your code here
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int n = sc.nextInt();
                                int[] nums = new int[n];
                                for (int i = 0; i < n; i++) nums[i] = sc.nextInt();
                                new Solution().moveZeroes(nums);
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < n; i++) {
                                    if (i > 0) sb.append(" ");
                                    sb.append(nums[i]);
                                }
                                System.out.println(sb.toString());
                            }
                        }""")
                .driverCodePython("""
                        from typing import List
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            data = list(map(int, sys.stdin.read().split()))
                            nums = data[1:1+data[0]]
                            Solution().moveZeroes(nums)
                            print(" ".join(map(str, nums)))""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int n; cin >> n;
                            vector<int> nums(n);
                            for (int i = 0; i < n; i++) cin >> nums[i];
                            Solution().moveZeroes(nums);
                            for (int i = 0; i < n; i++) {
                                if (i > 0) cout << " ";
                                cout << nums[i];
                            }
                            cout << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("5\n0 1 0 3 12", "1 3 12 0 0", "nums = [0,1,0,3,12]", "Zeroes moved to the end preserving order.", true),
                        tc("1\n0", "0", "nums = [0]", "Single zero.", true),
                        tc("4\n0 0 1 2", "1 2 0 0", null, null, false)
                ));
    }

    private void seedPalindromeNumber() {
        saveProblem(CodingProblem.builder()
                .title("10. Palindrome Number")
                .slug("palindrome-number")
                .difficulty(Difficulty.EASY)
                .categoryTag("Math")
                .description("""
                        Given an integer `x`, return `true` if `x` is a palindrome, and `false` otherwise.

                        An integer is a palindrome when it reads the same forward and backward.""")
                .constraintsText("-2^31 <= x <= 2^31 - 1")
                .starterCodeJava("""
                        class Solution {
                            public boolean isPalindrome(int x) {
                                // Write your code here
                                return false;
                            }
                        }""")
                .starterCodePython("""
                        class Solution:
                            def isPalindrome(self, x: int) -> bool:
                                # Write your code here
                                pass""")
                .starterCodeCpp("""
                        class Solution {
                        public:
                            bool isPalindrome(int x) {
                                // Write your code here
                                return false;
                            }
                        };""")
                .driverCodeJava("""
                        import java.util.*;
                        // {{SOLUTION}}
                        public class Main {
                            public static void main(String[] args) {
                                Scanner sc = new Scanner(System.in);
                                int x = sc.nextInt();
                                System.out.println(new Solution().isPalindrome(x));
                            }
                        }""")
                .driverCodePython("""
                        import sys
                        # {{SOLUTION}}
                        if __name__ == "__main__":
                            x = int(sys.stdin.read().strip())
                            print("true" if Solution().isPalindrome(x) else "false")""")
                .driverCodeCpp("""
                        #include <bits/stdc++.h>
                        using namespace std;
                        // {{SOLUTION}}
                        int main() {
                            int x; cin >> x;
                            cout << (Solution().isPalindrome(x) ? "true" : "false") << endl;
                            return 0;
                        }""")
                .build(),
                List.of(
                        tc("121", "true", "x = 121", "121 reads as 121 from left to right and from right to left.", true),
                        tc("-121", "false", "x = -121", "From left to right, it reads -121. From right to left, it becomes 121-.", true),
                        tc("10", "false", "x = 10", "Reads 01 from right to left.", true),
                        tc("0", "true", null, null, false)
                ));
    }
}

