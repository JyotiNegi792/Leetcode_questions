import java.util.*;

class Solution {

    static class State {
        int row, col;
        int energy;
        int mask;
        int moves;

        State(int row, int col, int energy, int mask, int moves) {
            this.row = row;
            this.col = col;
            this.energy = energy;
            this.mask = mask;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startRow = 0;
        int startCol = 0;

        // Store an index for every litter cell
        int[][] litterIndex = new int[m][n];
        for (int[] row : litterIndex) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startRow = i;
                    startCol = j;
                }

                if (ch == 'L') {
                    litterIndex[i][j] = litterCount++;
                }
            }
        }

        // All litter collected mask
        int finalMask = (1 << litterCount) - 1;

        if (finalMask == 0) {
            return 0;
        }

        /*
         * visited[row][col][mask]
         *
         * Instead of storing every possible energy value,
         * store the MAXIMUM energy with which we reached
         * this (row, col, mask).
         *
         * If we reach the same state with less or equal energy,
         * there is no reason to process it again.
         */
        int[][][] bestEnergy =
                new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(bestEnergy[i][j], -1);
            }
        }

        Queue<State> queue = new ArrayDeque<>();

        queue.offer(
                new State(
                        startRow,
                        startCol,
                        energy,
                        0,
                        0
                )
        );

        bestEnergy[startRow][startCol][0] = energy;

        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        while (!queue.isEmpty()) {

            State current = queue.poll();

            // All litter collected
            if (current.mask == finalMask) {
                return current.moves;
            }

            // Cannot move if energy is already 0
            // unless current position is R.
            // Normally energy gets reset immediately on R,
            // so this condition protects us anyway.
            if (current.energy == 0) {
                continue;
            }

            for (int[] dir : directions) {

                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];

                // Outside grid
                if (newRow < 0 || newRow >= m ||
                    newCol < 0 || newCol >= n) {
                    continue;
                }

                char cell = classroom[newRow].charAt(newCol);

                // Obstacle
                if (cell == 'X') {
                    continue;
                }

                // One move costs one energy
                int newEnergy = current.energy - 1;
                int newMask = current.mask;

                // Collect litter
                if (cell == 'L') {
                    int index = litterIndex[newRow][newCol];
                    newMask |= (1 << index);
                }

                // Reset energy
                if (cell == 'R') {
                    newEnergy = energy;
                }

                /*
                 * If energy becomes 0 on a normal cell,
                 * the state is still valid.
                 *
                 * For example, if this move collected the
                 * last litter, we should allow it.
                 *
                 * But we won't be able to move from there
                 * in the next BFS iteration.
                 */

                if (bestEnergy[newRow][newCol][newMask] >= newEnergy) {
                    continue;
                }

                bestEnergy[newRow][newCol][newMask] = newEnergy;

                queue.offer(
                        new State(
                                newRow,
                                newCol,
                                newEnergy,
                                newMask,
                                current.moves + 1
                        )
                );
            }
        }

        return -1;
    }
}