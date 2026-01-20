import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
LeetCode 102. Binary Tree Level Order Traversal

Goal:
Return the level order traversal of a binary tree’s nodes’ values.
Output is a list of levels, where each level is a list of node values from left to right.

We provide two approaches:
1) BFS (Queue)   -> typical level-order traversal
2) DFS (Recursion) while tracking "level" -> build lists by depth
*/
public class LevelOrderTraversal {

    // Basic tree node (LeetCode style)
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /*
     APPROACH 1: BFS using a queue
     Idea:
     - Use a queue to process nodes level-by-level.
     - For each level, capture the queue size first (number of nodes in this level).
     - Pop exactly 'size' nodes, add their values to the current level list,
       and push their children into the queue.

     Time Complexity: O(n)
       - every node is enqueued and dequeued once

     Space Complexity: O(w)
       - w is the maximum width of the tree (max nodes in any level)
       - worst case can be O(n)
     */
    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>(); // queue for level order traversal
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();               // number of nodes at current level
            List<Integer> list = new ArrayList<>(); // store current level values

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();      // remove one node from queue
                list.add(node.val);                // add its value to current level list

                // add children for next level
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }

            result.add(list); // one full level collected
        }

        return result;
    }

    /*
     APPROACH 2: DFS (Recursion) with level tracking
     Idea:
     - Use recursion and pass the current depth/level.
     - When first visiting a level, create a new list in result.
     - Add current node's value to result.get(level).
     - Recurse left and right with level + 1.

     Time Complexity: O(n)
       - each node is visited once

     Space Complexity: O(h)
       - recursion stack height h
       - worst case skewed tree: O(n), balanced: O(log n)
     */
    private List<List<Integer>> result;

    public List<List<Integer>> levelOrderDFS(TreeNode root) {
        result = new ArrayList<>();
        if (root == null) return result;

        helper(root, 0);
        return result;
    }

    private void helper(TreeNode root, int level) {
        if (root == null) return;

        // If we are entering this level for the first time, create a new list
        if (level == result.size()) result.add(new ArrayList<>());

        // Add value to its level list
        result.get(level).add(root.val);

        // Recurse children to next level
        helper(root.left, level + 1);
        helper(root.right, level + 1);
    }

    // Simple main method to test (no assertions)
    public static void main(String[] args) {
        /*
            Build this tree:
                    3
                   / \
                  9  20
                    /  \
                   15   7

            Expected level order:
            [[3], [9, 20], [15, 7]]
        */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        LevelOrderTraversal t = new LevelOrderTraversal();

        List<List<Integer>> bfs = t.levelOrder(root);
        System.out.println("BFS Level Order: " + bfs);

        List<List<Integer>> dfs = t.levelOrderDFS(root);
        System.out.println("DFS Level Order: " + dfs);
    }
}
