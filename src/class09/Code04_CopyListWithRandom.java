package class09;

import java.util.HashMap;

//拷贝一个random链表,是个正常单链表,但是每个节点有一个random指针,可能指向任意一个节点
//1.用一个map存储(容器)
//map 的key为1,2,3节点,value为复制的1',2',3'节点,遍历1节点,可以找到next和random指针,这两个指针指向可以通过map获取到对应的克隆节点
//通过这种方式完成复制,返回1对应的1'为头节点
//2.先把克隆节点1'插入到1和2之间,以此类推
//1的random指针比如指向3,1'指向3的next,即3'
//最后将新老链表拆分
public class Code04_CopyListWithRandom {

	public static class Node {
		public int value;
		public Node next;
		public Node rand;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node copyListWithRand1(Node head) {
		// key 老节点
		// value 新节点
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node cur = head;
		while (cur != null) {
			map.put(cur, new Node(cur.value));
			cur = cur.next;
		}
		cur = head;
		while (cur != null) {
			// cur 老
			// map.get(cur) 新
			// 新.next ->  cur.next克隆节点找到
			map.get(cur).next = map.get(cur.next);
			map.get(cur).rand = map.get(cur.rand);
			cur = cur.next;
		}
		return map.get(head);
	}

	public static Node copyListWithRand2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		Node next = null;
		// copy node and link to every node
		// 1 -> 2
		// 1 -> 1' -> 2
		while (cur != null) {
			// cur 老 next 老的下一个
			next = cur.next;
			cur.next = new Node(cur.value);
			cur.next.next = next;
			cur = next;
		}
		cur = head;
		Node curCopy = null;
		// set copy node rand
		// 1 -> 1' -> 2 -> 2'
		while (cur != null) {
			// cur 老
			// cur.next 新 copy
			next = cur.next.next;
			curCopy = cur.next;
			curCopy.rand = cur.rand != null ? cur.rand.next : null;
			cur = next;
		}
		// head head.next
		Node res = head.next;
		cur = head;
		// split
		while (cur != null) {
			next = cur.next.next;
			curCopy = cur.next;
			cur.next = next;
			curCopy.next = next != null ? next.next : null;
			cur = next;
		}
		return res;
	}

	public static void printRandLinkedList(Node head) {
		Node cur = head;
		System.out.print("order: ");
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.next;
		}
		System.out.println();
		cur = head;
		System.out.print("rand:  ");
		while (cur != null) {
			System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
			cur = cur.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = null;
		Node res1 = null;
		Node res2 = null;
		printRandLinkedList(head);
		res1 = copyListWithRand1(head);
		printRandLinkedList(res1);
		res2 = copyListWithRand2(head);
		printRandLinkedList(res2);
		printRandLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(4);
		head.next.next.next.next = new Node(5);
		head.next.next.next.next.next = new Node(6);

		head.rand = head.next.next.next.next.next; // 1 -> 6
		head.next.rand = head.next.next.next.next.next; // 2 -> 6
		head.next.next.rand = head.next.next.next.next; // 3 -> 5
		head.next.next.next.rand = head.next.next; // 4 -> 3
		head.next.next.next.next.rand = null; // 5 -> null
		head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

		System.out.println("原始链表：");
		printRandLinkedList(head);
		System.out.println("=========================");
		res1 = copyListWithRand1(head);
		System.out.println("方法一的拷贝链表：");
		printRandLinkedList(res1);
		System.out.println("=========================");
		res2 = copyListWithRand2(head);
		System.out.println("方法二的拷贝链表：");
		printRandLinkedList(res2);
		System.out.println("=========================");
		System.out.println("经历方法二拷贝之后的原始链表：");
		printRandLinkedList(head);
		System.out.println("=========================");

	}

}
