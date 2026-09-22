class Solution {

    int n, k;

    class Node {
        int prod;
        int[] cnt;

        Node() {
            cnt = new int[k];
        }
    }

    Node[] tree;
    int[] nums;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.nums = nums;
        this.k = k;
        this.n = nums.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Persistent update
            nums[index] = value;
            update(1, 0, n - 1, index, value);

            // Get information for nums[start ... n-1]
            Node res = query(1, 0, n - 1, start, n - 1);

            ans[q] = res.cnt[x];
        }

        return ans;
    }

    // --------------------------------------------------
    // BUILD
    // --------------------------------------------------

    void build(int node, int l, int r) {

        if (l == r) {
            tree[node] = new Node();

            int rem = nums[l] % k;

            tree[node].prod = rem;
            tree[node].cnt[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // --------------------------------------------------
    // UPDATE
    // --------------------------------------------------

    void update(int node, int l, int r, int index, int value) {

        if (l == r) {
            tree[node] = new Node();

            int rem = value % k;

            tree[node].prod = rem;
            tree[node].cnt[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // --------------------------------------------------
    // MERGE
    // --------------------------------------------------

    Node merge(Node left, Node right) {

        Node res = new Node();

        // Product of the complete segment
        res.prod = (left.prod * right.prod) % k;

        // Subarrays starting in the left part
        for (int rem = 0; rem < k; rem++) {
            res.cnt[rem] += left.cnt[rem];
        }

        // Subarrays that start in left and continue into right
        for (int rem = 0; rem < k; rem++) {

            int newRem = (left.prod * rem) % k;

            res.cnt[newRem] += right.cnt[rem];
        }

        return res;
    }

    // --------------------------------------------------
    // QUERY
    // --------------------------------------------------

    Node query(int node, int l, int r, int ql, int qr) {

        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }
}