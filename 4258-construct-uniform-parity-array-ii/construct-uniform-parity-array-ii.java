class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = Integer.MAX_VALUE;
        boolean hasOdd = false;
        boolean hasEven = false;

        for (int num : nums1) {
            min = Math.min(min, num);

            if (num % 2 == 0) {
                hasEven = true;
            } else {
                hasOdd = true;
            }
        }

        // Already uniform parity
        if (!hasOdd || !hasEven) {
            return true;
        }

        // For mixed parity, smallest element must be odd
        return min % 2 == 1;
    }
}