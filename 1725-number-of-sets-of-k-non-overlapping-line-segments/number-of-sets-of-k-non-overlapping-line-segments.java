class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1_000_000_007;

        long[][] dp = new long[k + 1][n];

        // 0 segments -> 1 way
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int seg = 1; seg <= k; seg++) {
            long sum = 0;

            for (int i = 1; i < n; i++) {
                // Start a segment at some previous point
                sum = (sum + dp[seg - 1][i - 1]) % MOD;

                // Either use the current point as the end
                // or don't use it
                dp[seg][i] = (dp[seg][i - 1] + sum) % MOD;
            }
        }

        return (int) dp[k][n - 1];
    }
}