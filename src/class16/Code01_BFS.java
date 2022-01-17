package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node start) {
		if (start == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		//set集合每次入栈校验,存在就不入栈,因为存在还入栈就会导致结果变多
		HashSet<Node> set = new HashSet<>();
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
				}
			}
		}
	}

	//练习  根树的层序遍历很像
	public static void bfs1(Node start) {
		if (start==null){
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(start);
		HashSet<Node> set = new HashSet<>();
		set.add(start);
		while (!queue.isEmpty()){
			Node cur = queue.poll();
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				if (!set.contains(next)){
					set.add(next);
					queue.add(next);
				}
			}
		}
	}
}
