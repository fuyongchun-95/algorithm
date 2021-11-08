package class03;

public class Code02_DeleteGivenValue {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// head = removeValue(head, 2);
	public static Node removeValue(Node head, int num) {
		// head来到第一个不需要删的位置
		//如果是输入的数,head指向下个位置,跳过本结点
		while (head != null) {
			if (head.value != num) {
				break;
			}
			head = head.next;
		}
		// 1 ) head == null
		// 2 ) head != null
		Node pre = head;
		Node cur = head;
		//
		while (cur != null) {
			if (cur.value == num) {
				pre.next = cur.next;
			} else {
				pre = cur;
			}
			cur = cur.next;
		}
		return head;
	}


	public static void main(String[] args) {
		int[] ints ={2,3,4,3,5};
		Node head = new Node(1);
		Node pre = head;
		for (int i = 0; i < ints.length; i++) {
			Node cur = new Node(ints[i]);
			pre.next = cur;
			pre = cur;
		}
		removeValuetest(head,3);
		while (head!=null){
			System.out.print(head.value+", ");
			head = head.next;
		}
	}


	//练习
	public static Node removeValuetest(Node head, int num) {
		//取到不为num的节点作为头
		while (head.next !=null){
			if (head.value !=num){
				break;
			}
			head = head.next;
		}
		//定义两个变量从头开始遍历
		Node pre = head;
		Node cur = head;

		while (cur !=null){
			if (cur.value==num){
				pre.next = cur.next;
			}else {
				pre = cur;
			}
			cur = cur.next;
		}

		return head;
	}
}











