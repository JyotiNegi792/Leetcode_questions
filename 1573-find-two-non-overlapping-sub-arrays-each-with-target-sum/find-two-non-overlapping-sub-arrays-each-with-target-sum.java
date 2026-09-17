class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;

        // best[i] = minimum length of a valid subarray
        // completely inside arr[0...i]
        int[] best = new int[n];
        java.util.Arrays.fill(best, INF);

        int left = 0;
        int sum = 0;
        int ans = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Shrink window if sum becomes greater than target
            while (sum > target) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int len = right - left + 1;

                // If there is a previous non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(ans, len + best[left - 1]);
                }

                // Store the shortest valid subarray ending at/before right
                best[right] = Math.min(
                    len,
                    right > 0 ? best[right - 1] : INF
                );
            } else {
                // Carry forward the previous best
                if (right > 0) {
                    best[right] = best[right - 1];
                }
            }
        }

        return ans == INF ? -1 : ans;
    }
}