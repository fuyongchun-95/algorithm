package class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree

//多叉树序列化,反序列化   将多叉树每层,放在二叉树的左孩子的右节点,第一个孩子当二叉树的左孩子,第一个孩子的兄弟当左孩子的右节点串一串
public class Code03_EncodeNaryTreeToBinaryTree {

	// 提交时不要提交这个类
	public static class Node {
		public int val;
		public List<Node> children;

		public Node() {
		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};

	// 提交时不要提交这个类
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	// 只提交这个类即可
	class Codec {
		// Encodes an n-ary tree to a binary tree.
		public TreeNode encode(Node root) {
			if (root == null) {
				return null;
			}
			//生成二叉树的头
			TreeNode head = new TreeNode(root.val);
			//递归生成左孩子和所有右节点
			head.left = en(root.children);
			return head;
		}

		private TreeNode en(List<Node> children) {
			TreeNode head = null;
			TreeNode cur = null;
			for (Node child : children) {
				//每个孩子节点,先构建二叉树节点
				TreeNode tNode = new TreeNode(child.val);
				//头为空,把孩子第一个当头
				if (head == null) {
					head = tNode;
				} else {
					//否则往右挂
					cur.right = tNode;
				}
				//头给cur,头之后的往cur右挂
				cur = tNode;
				//cur的左子树是下一轮遍历的一个节点和一长条右子树
				cur.left = en(child.children);
			}
			return head;
		}

		// Decodes your binary tree to an n-ary tree.
		public Node decode(TreeNode root) {
			if (root == null) {
				return null;
			}
			return new Node(root.val, de(root.left));
		}

		public List<Node> de(TreeNode root) {
			List<Node> children = new ArrayList<>();
			while (root != null) {
				Node cur = new Node(root.val, de(root.left));
				children.add(cur);
				root = root.right;
			}
			return children;
		}

	}

}
