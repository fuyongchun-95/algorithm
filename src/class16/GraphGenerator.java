package class16;

public class GraphGenerator {

	// matrix 所有的边
	// N*3 的矩阵
	// [weight, from节点上面的值，to节点上面的值]
	// 
	// [ 5 , 0 , 7]
	// [ 3 , 0,  1]
	// 该方法可以把这种二维数组直接转为我定义的图结构
	public static Graph createGraph(int[][] matrix) {
		Graph graph = new Graph();
		for (int i = 0; i < matrix.length; i++) {
			 // 拿到每一条边， matrix[i] 
			int weight = matrix[i][0];
			int from = matrix[i][1];
			int to = matrix[i][2];
			//from和to节点不存在,就建出来放进图的nodes里
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new Node(from));
			}
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new Node(to));
			}
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);
			//根据from和to的节点,建边
			Edge newEdge = new Edge(weight, fromNode, toNode);
			//from节点的邻居添加上to节点
			fromNode.nexts.add(toNode);
			//from出度加一
			fromNode.out++;
			//to节点入度加一
			toNode.in++;
			//from节点边记上这条新边
			fromNode.edges.add(newEdge);
			//图的边,加上这条新边
			graph.edges.add(newEdge);
		}
		return graph;
	}

}
