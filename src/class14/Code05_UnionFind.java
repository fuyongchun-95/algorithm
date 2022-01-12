package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

//并查集  一个大集合,里面有一堆节点,要求有两个函数,一个是isSameSet,是不是同一个集合的,Union合并集合
//要求这两个函数时间复杂度常数
//做法,每个节点建一个指针指向自己,当合并的时候,检查集合大小,小的找到找到自己第一个爹,指到大的爹下面
//nodes,以v值为头的集合,parents,每个节点有唯一的爹,sizemap,根据头知道自己集合大小,快速对比,进行合并,然后被合并的那个删掉记录
public class Code05_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionFind<V> {
		public HashMap<V, Node<V>> nodes;
		public HashMap<Node<V>, Node<V>> parents;
		public HashMap<Node<V>, Integer> sizeMap;

		public UnionFind(List<V> values) {
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		// 给你一个节点，请你往上到不能再往上，把代表返回
		//就是爹不是自己,继续,爹是自己,自己就是大爹根节点
		public Node<V> findFather(Node<V> cur) {
			Stack<Node<V>> path = new Stack<>();
			//优化点,合并第一次,用栈接受沿途节点,并将根节点拿到
			while (cur != parents.get(cur)) {
				path.push(cur);
				cur = parents.get(cur);
			}
			//之后将路径上所有节点父节点都设置为头节点,下次使用可以节约查找到头节点需要的时间
			while (!path.isEmpty()) {
				parents.put(path.pop(), cur);
			}
			return cur;
		}

		public boolean isSameSet(V a, V b) {
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		public void union(V a, V b) {
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));
			if (aHead != bHead) {
				int aSetSize = sizeMap.get(aHead);
				int bSetSize = sizeMap.get(bHead);
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;
				parents.put(small, big);
				sizeMap.put(big, aSetSize + bSetSize);
				sizeMap.remove(small);
			}
		}

		public int sets() {
			return sizeMap.size();
		}

	}
}
