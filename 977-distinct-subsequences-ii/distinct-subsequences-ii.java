class Solution {
    public int distinctSubseqII(String s) {
        long MOD = 1000000007;
        long[] dp = new long[26];

        for (char c : s.toCharArray()) {
            int i = c - 'a';

            long total = 1; // empty subsequence
            for (long x : dp)
                total = (total + x) % MOD;

            dp[i] = total;
        }

        long ans = 0;
        for (long x : dp)
            ans = (ans + x) % MOD;

        return (int) ans;
    }
}