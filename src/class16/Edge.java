package class16;

public class Edge {
	//权重
	public int weight;
	//从哪个点到哪个点
	public Node from;
	public Node to;

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

}
