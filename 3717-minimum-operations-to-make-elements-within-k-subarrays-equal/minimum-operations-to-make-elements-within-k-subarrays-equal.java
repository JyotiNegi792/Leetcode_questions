class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long leftSum = 0, rightSum = 0;
        int leftSize = 0, rightSize = 0;

        // Sliding window median
        for (int i = 0; i < n; i++) {
            int v = nums[i];

            if (left.isEmpty() || v <= left.lastKey()) {
                left.merge(v, 1, Integer::sum);
                leftSum += v;
                leftSize++;
            } else {
                right.merge(v, 1, Integer::sum);
                rightSum += v;
                rightSize++;
            }

            // Balance the two halves
            while (leftSize > rightSize + 1) {
                int a = left.lastKey();
                remove(left, a);
                leftSum -= a;
                leftSize--;

                right.merge(a, 1, Integer::sum);
                rightSum += a;
                rightSize++;
            }

            while (leftSize < rightSize) {
                int a = right.firstKey();
                remove(right, a);
                rightSum -= a;
                rightSize--;

                left.merge(a, 1, Integer::sum);
                leftSum += a;
                leftSize++;
            }

            if (i >= x) {
                int a = nums[i - x];

                if (left.containsKey(a)) {
                    remove(left, a);
                    leftSum -= a;
                    leftSize--;
                } else {
                    remove(right, a);
                    rightSum -= a;
                    rightSize--;
                }

                while (leftSize > rightSize + 1) {
                    int b = left.lastKey();
                    remove(left, b);
                    leftSum -= b;
                    leftSize--;

                    right.merge(b, 1, Integer::sum);
                    rightSum += b;
                    rightSize++;
                }

                while (leftSize < rightSize) {
                    int b = right.firstKey();
                    remove(right, b);
                    rightSum -= b;
                    rightSize--;

                    left.merge(b, 1, Integer::sum);
                    leftSum += b;
                    leftSize++;
                }
            }

            if (i >= x - 1) {
                int median = left.lastKey();
                cost[i - x + 1] =
                    (long) median * leftSize - leftSum
                    + rightSum - (long) median * rightSize;
            }
        }

        // DP: dp[j][i] = min cost using first i elements
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++)
            java.util.Arrays.fill(dp[j], INF);

        for (int i = 0; i <= n; i++)
            dp[0][i] = 0;

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                dp[j][i] = dp[j][i - 1];

                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }

    private void remove(TreeMap<Integer, Integer> map, int v) {
        int count = map.get(v);
        if (count == 1) map.remove(v);
        else map.put(v, count - 1);
    }
}