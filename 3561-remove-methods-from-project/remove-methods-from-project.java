class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : invocations) {
            graph.get(edge[0]).add(edge[1]);
        }

        // Find all suspicious methods reachable from k
        boolean[] suspicious = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(k);
        suspicious[k] = true;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int v : graph.get(u)) {
                if (!suspicious[v]) {
                    suspicious[v] = true;
                    stack.push(v);
                }
            }
        }

        // Check if any non-suspicious method invokes a suspicious one
        for (int[] edge : invocations) {
            int from = edge[0];
            int to = edge[1];
            if (!suspicious[from] && suspicious[to]) {
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    ans.add(i);
                }
                return ans;
            }
        }

        // Remove all suspicious methods
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }
}