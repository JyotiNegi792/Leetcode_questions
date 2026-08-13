class Solution {

    class Node {
        int len;
        int prefix;
        int suffix;
        int max;
        char leftChar;
        char rightChar;

        Node(int len, int prefix, int suffix, int max,
             char leftChar, char rightChar) {
            this.len = len;
            this.prefix = prefix;
            this.suffix = suffix;
            this.max = max;
            this.leftChar = leftChar;
            this.rightChar = rightChar;
        }
    }

    Node[] tree;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {

        int n = s.length();
        tree = new Node[4 * n];

        build(1, 0, n - 1, s);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            ans[i] = tree[1].max;
        }

        return ans;
    }

    private void build(int node, int l, int r, String s) {

        if (l == r) {
            char ch = s.charAt(l);

            tree[node] = new Node(
                1,      // len
                1,      // prefix
                1,      // suffix
                1,      // max
                ch,     // leftChar
                ch      // rightChar
            );

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid, s);
        build(node * 2 + 1, mid + 1, r, s);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(int node, int l, int r, int index, char ch) {

        if (l == r) {
            tree[node] = new Node(
                1, 1, 1, 1, ch, ch
            );
            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, r, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node merge(Node left, Node right) {

        int len = left.len + right.len;

        int prefix = left.prefix;
        int suffix = right.suffix;

        int max = Math.max(left.max, right.max);

        // We can join the two parts
        if (left.rightChar == right.leftChar) {

            max = Math.max(max, left.suffix + right.prefix);

            // Entire left part consists of one character
            if (left.prefix == left.len) {
                prefix = left.len + right.prefix;
            }

            // Entire right part consists of one character
            if (right.suffix == right.len) {
                suffix = right.len + left.suffix;
            }
        }

        return new Node(
            len,
            prefix,
            suffix,
            max,
            left.leftChar,
            right.rightChar
        );
    }
}