class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] count = new int[3];

        for (int stone : stones) {
            count[stone % 3]++;
        }

        // If there are no stones with remainder 1 or 2,
        // every move keeps the sum unchanged modulo 3.
        if (count[1] == 0 && count[2] == 0) {
            return false;
        }

        // If count[0] is even, the game effectively depends
        // on whether Alice can force the first non-zero remainder.
        if (count[0] % 2 == 0) {
            return count[1] > 0 && count[2] > 0;
        }

        // If count[0] is odd, Alice needs a sufficiently large
        // imbalance between the number of 1s and 2s.
        return Math.abs(count[1] - count[2]) > 2;
    }
}