class Solution {
    public int longestSubsequence(int[] nums) {
        int n = nums.length;
        int xor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            xor ^= num;

            if (num != 0) {
                hasNonZero = true;
            }
        }

        // Case 1: Entire array has non-zero XOR
        if (xor != 0) {
            return n;
        }

        // Case 2: All elements are zero
        if (!hasNonZero) {
            return 0;
        }

        // Case 3: Total XOR is 0, but there is a non-zero element
        return n - 1;
    }
}