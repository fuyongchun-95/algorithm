package class03;

import java.util.ArrayList;
import java.util.List;

public class Code01_ReverseList {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			value = data;
		}
	}

	public static class DoubleNode {
		public int value;
		public DoubleNode last;
		public DoubleNode next;

		public DoubleNode(int data) {
			value = data;
		}
	}

	//  head
	//   a    ->   b    ->  c  ->  null
	//   c    ->   b    ->  a  ->  null
	public static Node reverseLinkedList(Node head) {
		Node pre = null;
		Node next = null;
		while (head != null) {
			//head开始在a的位置
			//null指向头的下一位,也就是next现在指向b
			next = head.next;
			//head的next指向pre(null),此处断开a的next指针,指向空,进行a的反转null<--a
			head.next = pre;
			//pre指向head   null<--a(pre)
			pre = head;
			//head指向next(b) null<--a(pre)   b(head)-->c-->null
			head = next;

			//第二轮 head开始在b的位置  null<--a(pre) b(head)-->c-->null
			//第一步:   next指向head.next 也就是c  null<--a(pre) b(head)-->c(next)-->null
			//第二步:   head.next指向pre(a)       null<--a(pre)<--b(head)  c(next)-->null
			//第三步:   pre指向head               null<--a<--b(head)(pre)  c(next)-->null
			//第四步:   head指向next              null<--a<--b(pre)  c(next)(head)-->null

			//第三轮    null<--a<--b(pre)  c(head)-->null(next)
			//         null<--a<--b(pre)<--c(head)  null(next)
			//         null<--a<--b<--c(head)(pre)  null(next)
			//         null<--a<--b<--c(pre)  null(head)(next)

			//第四轮  head==null结束
		}
		return pre;
	}

	//双链表反转
	public static DoubleNode reverseDoubleList(DoubleNode head) {
		DoubleNode pre = null;
		DoubleNode next = null;
		while (head != null) {
			next=head.next;
			head.next=pre;
			head.last=next;
			pre=head;
			head=next;
		}

		//第一轮, a   b    c
		//       a为head,将a的下面环境给next
		//       pre(null)给a.next
		//       a.last指向b(至此完成a的反转)
		//       pre指向a
		//       head指向next(b)
		//第二轮 b为head,next指向c
		//       b.next指向a
		//       b.last指向c(完成b反转)
		//       pre指向b
		//       head指向c
		return pre;
	}

	//单链表反转容器版
	public static Node testReverseLinkedList(Node head) {
		if (head == null) {
			return null;
		}
		ArrayList<Node> list = new ArrayList<>();
		//头结点放入集合,head指向head.next再次放入,直到全部放入集合
		while (head != null) {
			list.add(head);
			head = head.next;
		}
		//头结点指向空
		list.get(0).next = null;
		int N = list.size();
		//遍历,将第二个结点指向第一个结点,实现反转
		for (int i = 1; i < N; i++) {
			list.get(i).next = list.get(i - 1);
		}
		//最终返回list最后一个结点为头结点
		return list.get(N - 1);
	}

	//双链表反转容器版
	public static DoubleNode testReverseDoubleList(DoubleNode head) {
		if (head == null) {
			return null;
		}
		ArrayList<DoubleNode> list = new ArrayList<>();
		while (head != null) {
			list.add(head);
			head = head.next;
		}
		list.get(0).next = null;
		DoubleNode pre = list.get(0);
		int N = list.size();
		for (int i = 1; i < N; i++) {
			DoubleNode cur = list.get(i);
			cur.last = null;
			cur.next = pre;
			pre.last = cur;
			pre = cur;
		}
		return list.get(N - 1);
	}

	// for test
	//随机生成单链表
	public static Node generateRandomLinkedList(int len, int value) {
		int size = (int) (Math.random() * (len + 1));
		if (size == 0) {
			return null;
		}
		size--;
		Node head = new Node((int) (Math.random() * (value + 1)));
		Node pre = head;
		while (size != 0) {
			Node cur = new Node((int) (Math.random() * (value + 1)));
			pre.next = cur;
			pre = cur;
			size--;
		}
		return head;
	}

	// for test
	//随机生成双链表
	public static DoubleNode generateRandomDoubleList(int len, int value) {
		int size = (int) (Math.random() * (len + 1));
		if (size == 0) {
			return null;
		}
		size--;
		DoubleNode head = new DoubleNode((int) (Math.random() * (value + 1)));
		DoubleNode pre = head;
		while (size != 0) {
			DoubleNode cur = new DoubleNode((int) (Math.random() * (value + 1)));
			pre.next = cur;
			cur.last = pre;
			pre = cur;
			size--;
		}
		return head;
	}

	// for test
	//将单链表值依次放入ans集合
	public static List<Integer> getLinkedListOriginOrder(Node head) {
		List<Integer> ans = new ArrayList<>();
		while (head != null) {
			ans.add(head.value);
			head = head.next;
		}
		return ans;
	}

	// for test
	//检查
	public static boolean checkLinkedListReverse(List<Integer> origin, Node head) {
		for (int i = origin.size() - 1; i >= 0; i--) {
			if (!origin.get(i).equals(head.value)) {
				return false;
			}
			head = head.next;
		}
		return true;
	}

	// for test
	public static List<Integer> getDoubleListOriginOrder(DoubleNode head) {
		List<Integer> ans = new ArrayList<>();
		while (head != null) {
			ans.add(head.value);
			head = head.next;
		}
		return ans;
	}

	// for test
	public static boolean checkDoubleListReverse(List<Integer> origin, DoubleNode head) {
		DoubleNode end = null;
		for (int i = origin.size() - 1; i >= 0; i--) {
			if (!origin.get(i).equals(head.value)) {
				return false;
			}
			end = head;
			head = head.next;
		}
		for (int i = 0; i < origin.size(); i++) {
			if (!origin.get(i).equals(end.value)) {
				return false;
			}
			end = end.last;
		}
		return true;
	}

	// for test
	public static void main(String[] args) {
		int len = 50;
		int value = 100;
		int testTime = 100000;
		System.out.println("test begin!");
		for (int i = 0; i < testTime; i++) {
			Node node1 = generateRandomLinkedList(len, value);
			List<Integer> list1 = getLinkedListOriginOrder(node1);
			node1 = reverseLinkedList(node1);
			if (!checkLinkedListReverse(list1, node1)) {
				System.out.println("Oops1!");
			}

			Node node2 = generateRandomLinkedList(len, value);
			List<Integer> list2 = getLinkedListOriginOrder(node2);
			node2 = testReverseLinkedList(node2);
			if (!checkLinkedListReverse(list2, node2)) {
				System.out.println("Oops2!");
			}

			DoubleNode node3 = generateRandomDoubleList(len, value);
			List<Integer> list3 = getDoubleListOriginOrder(node3);
			node3 = reverseDoubleList(node3);
			if (!checkDoubleListReverse(list3, node3)) {
				System.out.println("Oops3!");
			}

			DoubleNode node4 = generateRandomDoubleList(len, value);
			List<Integer> list4 = getDoubleListOriginOrder(node4);
			node4 = reverseDoubleList(node4);
			if (!checkDoubleListReverse(list4, node4)) {
				System.out.println("Oops4!");
			}

		}
		System.out.println("test finish!");

	}

}
