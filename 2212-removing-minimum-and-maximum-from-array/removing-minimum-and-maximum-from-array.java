class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return 1;
        }

        int minIndex = 0;
        int maxIndex = 0;

        // Find indices of minimum and maximum elements
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Make sure minIndex is the smaller index
        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        // Case 1: Remove both from the front
        int removeFromFront = right + 1;

        // Case 2: Remove both from the back
        int removeFromBack = n - left;

        // Case 3: Remove one from front and one from back
        int removeFromBothSides = (left + 1) + (n - right);

        return Math.min(
            removeFromFront,
            Math.min(removeFromBack, removeFromBothSides)
        );
    }
}