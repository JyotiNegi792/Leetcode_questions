import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one level at a time
            while (size-- > 0) {

                String current = queue.poll();

                // If valid, add it to the answer
                if (isValid(current)) {
                    result.add(current);
                    found = true;
                }

                // If we already found valid strings,
                // don't generate strings with more removals
                if (found) {
                    continue;
                }

                // Remove one parenthesis at every possible position
                for (int i = 0; i < current.length(); i++) {

                    // We only remove parentheses
                    if (current.charAt(i) != '(' &&
                        current.charAt(i) != ')') {
                        continue;
                    }

                    String next = current.substring(0, i)
                                 + current.substring(i + 1);

                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }

            // Since BFS works level by level,
            // all valid strings at this level have
            // the minimum number of removals.
            if (found) {
                break;
            }
        }

        return result;
    }

    // Checks whether a string has valid parentheses
    private boolean isValid(String s) {

        int balance = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                balance++;
            } 
            else if (c == ')') {
                balance--;

                // More closing brackets than opening brackets
                if (balance < 0) {
                    return false;
                }
            }
        }

        // Valid only if all opening brackets are matched
        return balance == 0;
    }
}