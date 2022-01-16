package class16;

import java.util.ArrayList;
//图中点的描述
// 点结构的描述
public class Node {
	//点的值
	public int value;
	//入度,有多少指向他
	public int in;
	//出度,他指向多少其他点
	public int out;
	//邻居是哪些,是从这个点出发,能找到的,指向他的点不是邻居
	public ArrayList<Node> nexts;
	//边是哪些,他指向邻居才是边
	public ArrayList<Edge> edges;

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
