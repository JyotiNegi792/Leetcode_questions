class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int left = 0;
        int ones = 0;
        int minLen = Integer.MAX_VALUE;
        String ans = "";

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            // We have exactly k ones
            while (ones == k) {
                // Remove leading zeroes to get the shortest substring
                while (left < right && s.charAt(left) == '0') {
                    left++;
                }

                int len = right - left + 1;
                String curr = s.substring(left, right + 1);

                if (len < minLen) {
                    minLen = len;
                    ans = curr;
                } else if (len == minLen && curr.compareTo(ans) < 0) {
                    ans = curr;
                }

                // Move left past the first '1'
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }
        }

        return ans;
    }
}