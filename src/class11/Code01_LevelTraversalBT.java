package class11;

import java.util.LinkedList;
import java.util.Queue;


//层序遍历
public class Code01_LevelTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static void level(Node head) {
		if (head == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);
		//全部放入队列
		//第一轮,头放入,进入循环,头为cur,有左入队左,有右入队右  出队head
		//第二轮,左为cur,左右入队,第三轮,右为cur,左右入队      出队左孩子,右孩子
		//第四轮,以此类推
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			if (cur.left != null) {
				queue.add(cur.left);
			}
			if (cur.right != null) {
				queue.add(cur.right);
			}
		}
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		level(head);
		System.out.println("========");
	}

}
