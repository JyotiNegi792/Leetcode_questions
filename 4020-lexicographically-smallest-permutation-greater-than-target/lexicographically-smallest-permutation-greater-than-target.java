import java.util.*;

class Solution {
    int n;
    String targetStr;
    int[] count = new int[26];
    Boolean[][] memo;

    public String lexGreaterPermutation(String s, String target) {
        n = s.length();
        targetStr = target;
        Arrays.fill(count, 0);
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // memo[i][isGreater]: i is current index, isGreater means prefix is already > target's prefix
        memo = new Boolean[n + 1][2];

        StringBuilder result = new StringBuilder();
        if (dfs(0, false, result)) {
            return result.toString();
        }
        return "";
    }

    private boolean dfs(int idx, boolean isGreater, StringBuilder sb) {
        if (idx == n) {
            return isGreater;
        }

        int greaterState = isGreater ? 1 : 0;
        if (memo[idx][greaterState] != null) {
            return memo[idx][greaterState];
        }

        int startChar = isGreater ? 0 : (targetStr.charAt(idx) - 'a');

        for (int c = startChar; c < 26; c++) {
            if (count[c] > 0) {
                count[c]--;
                sb.append((char) ('a' + c));

                boolean nextIsGreater = isGreater || (c > targetStr.charAt(idx) - 'a');

                if (dfs(idx + 1, nextIsGreater, sb)) {
                    return memo[idx][greaterState] = true;
                }

                sb.deleteCharAt(sb.length() - 1);
                count[c]++;
            }
        }

        return memo[idx][greaterState] = false;
    }
}