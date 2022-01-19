package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//拓扑排序,每次找入度是0的节点就可以
public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		// key 某个节点   value 剩余的入度
		HashMap<Node, Integer> inMap = new HashMap<>();
		// 只有剩余入度为0的点，才进入这个队列
		Queue<Node> zeroInQueue = new LinkedList<>();
		//全遍历一遍,入度为0的进入队列,并且把所有节点和入度放入map
		for (Node node : graph.nodes.values()) {
			inMap.put(node, node.in);
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		List<Node> result = new ArrayList<>();
		while (!zeroInQueue.isEmpty()) {
			//出队一个节点
			Node cur = zeroInQueue.poll();
			//放入结果
			result.add(cur);
			//出队节点的邻居遍历,把邻居的入度都减一
			for (Node next : cur.nexts) {
				inMap.put(next, inMap.get(next) - 1);
				//再次检查入度为0的放入队列
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
