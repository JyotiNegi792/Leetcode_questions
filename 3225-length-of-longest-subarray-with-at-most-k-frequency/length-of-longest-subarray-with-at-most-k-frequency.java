import java.util.*;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < nums.length; right++) {

            // Increase frequency of nums[right]
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            // If frequency exceeds k, shrink the window
            while (map.get(nums[right]) > k) {
                map.put(nums[left], map.get(nums[left]) - 1);
                left++;
            }

            // Current window is a valid subarray
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}