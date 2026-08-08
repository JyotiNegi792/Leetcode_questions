import java.util.Arrays;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        int[] ans = new int[n2];
        
        // last[j] stores the last occurrence index in word1 where word1[i] == word2[j]
        int[] last = new int[n2];
        Arrays.fill(last, -1);
        
        int i = n1 - 1;
        int j = n2 - 1;
        
        // Populate suffix match positions from right to left
        while (i >= 0 && j >= 0) {
            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }
            i--;
        }
        
        boolean canSkip = true;
        j = 0;
        
        // Greedily build the smallest valid index sequence
        for (i = 0; i < n1; ++i) {
            if (j == n2) {
                break;
            }
            
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j++] = i;
            } else if (canSkip && (j == n2 - 1 || i < last[j + 1])) {
                // Use our single mismatch allowance
                canSkip = false;
                ans[j++] = i;
            }
        }
        
        // Return result if all characters of word2 were successfully matched
        return j == n2 ? ans : new int[0];
    }
}