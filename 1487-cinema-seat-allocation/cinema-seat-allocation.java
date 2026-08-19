import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        // Store reserved seats for only the rows that have reservations
        Map<Integer, Integer> reserved = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // Mark this seat as reserved
            reserved.put(row, reserved.getOrDefault(row, 0) | (1 << col));
        }

        int answer = (n - reserved.size()) * 2;

        // Masks for the three possible groups
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        for (int mask : reserved.values()) {

            boolean canLeft = (mask & left) == 0;
            boolean canMiddle = (mask & middle) == 0;
            boolean canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                // Can fit two groups: 2-5 and 6-9
                answer += 2;
            } else if (canLeft || canMiddle || canRight) {
                // Can fit at least one group
                answer += 1;
            }
        }

        return answer;
    }
}