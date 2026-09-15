import java.util.Arrays;

class Solution {
    private boolean[][] isPal;
    private int[] memo;
    private String s;
    private int n, k;

    public int maxPalindromes(String s, int k) {
        this.s = s;
        this.k = k;
        this.n = s.length();
        this.memo = new int[n];
        Arrays.fill(memo, -1);
        
        // isPal[i][j] stores whether s[i...j] is a palindrome
        isPal = new boolean[n][n];
        for (boolean[] row : isPal) {
            Arrays.fill(row, true);
        }
        
        // Precompute palindrome lookup table
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                isPal[i][j] = (s.charAt(i) == s.charAt(j)) && isPal[i + 1][j - 1];
            }
        }
        
        return dfs(0);
    }
    
    private int dfs(int i) {
        if (i >= n) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        
        // Option 1: Skip the current index
        int maxCount = dfs(i + 1);
        
        // Option 2: Try to form a valid palindrome starting at index i
        for (int j = i + k - 1; j < n; ++j) {
            if (isPal[i][j]) {
                maxCount = Math.max(maxCount, 1 + dfs(j + 1));
            }
        }
        
        return memo[i] = maxCount;
    }
}