class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Reverse alphabet value: a=26, b=25, ..., z=1
            int reverseValue = 'z' - ch + 1;

            // Position is 1-indexed
            int position = i + 1;

            sum += reverseValue * position;
        }

        return sum;
    }
}