/**
 * This class represents a solution for the problem of counting pseudo-palindromic paths in a binary tree.
 */
public class Solution {
    /**
     * This method counts the number of pseudo-palindromic paths in a binary tree.
     * @param root The root of the binary tree.
     * @return The number of pseudo-palindromic paths in the binary tree.
     */
    public int pseudoPalindromicPaths (TreeNode root) {
        return countPalindromicPaths(root, 0);
    }

    /**
     * This method counts the number of pseudo-palindromic paths in a binary tree.
     * @param root The root of the binary tree.
     * @param bitMap The bit map of the path. Mapping all the node values encountered
     * @return The number of pseudo-palindromic paths in the binary tree.
     */
    private int countPalindromicPaths(TreeNode root, int bitMap) {
        if(root == null)
            return 0;
        int newBitMap = bitMap ^ (1 << root.val);
        if(root.left == null && root.right == null)
            return isPowerOfTwo(newBitMap);
        return countPalindromicPaths(root.left, newBitMap) + countPalindromicPaths(root.right, newBitMap);
    }

    /**
     * This method checks if a number is a power of two.
     * @param n The number to check.
     * @return 1 if the number is a power of two, 0 otherwise.
     */
    private int isPowerOfTwo(int n) {
        return ((n & (n - 1)) == 0)? 1: 0;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}