package class16;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
	//点集和边集组成
	public HashMap<Integer, Node> nodes;
	public HashSet<Edge> edges;
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
