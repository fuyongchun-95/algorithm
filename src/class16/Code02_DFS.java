package class16;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {

	//深度优先遍历图
	//一条路走到死,然后回头看哪个没走完走哪个
	//使用栈记录深度遍历路径,用来回退,使用set校验是否记录过这个点
	public static void dfs(Node node) {
		if (node == null) {
			return;
		}
		Stack<Node> stack = new Stack<>();
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}

	//练习
	public static void dfs1(Node node) {
		if (node==null){
			return;
		}
		Stack<Node> stack = new Stack<>();
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		System.out.println(node.value);
		set.add(node);
		while (!stack.isEmpty()){
			Node cur = stack.pop();
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}
	
	

}
