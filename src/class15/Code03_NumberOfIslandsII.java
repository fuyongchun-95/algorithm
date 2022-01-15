package class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 本题为leetcode原题
// 测试链接：https://leetcode.com/problems/number-of-islands-ii/
// 所有方法都可以直接通过
//mn,比如m=3,n=3 就是全是0的3*3矩阵,positions[[0,1].[11]]
//positions数组每个元素都把矩阵内对应位置元素替换成1,每一步的岛的数量
public class Code03_NumberOfIslandsII {

	public static List<Integer> numIslands21(int m, int n, int[][] positions) {
		//空并查集
		UnionFind1 uf = new UnionFind1(m, n);
		List<Integer> ans = new ArrayList<>();
		//每个position执行一次connect获取当前岛数量存到结果返回
		for (int[] position : positions) {
			ans.add(uf.connect(position[0], position[1]));
		}
		return ans;
	}

	public static class UnionFind1 {
		private int[] parent;
		//size[i]!=0 size[i]=0 用size等不等于0标记i有没有被初始化
		//size[i]=1 i位置是1 为了表明有没有被初始化,就算i合并到j位置,i位置的值也不改为0
		private int[] size;
		private int[] help;
		private final int row;
		private final int col;
		//集合数
		private int sets;

		//初始化,全是0
		public UnionFind1(int m, int n) {
			row = m;
			col = n;
			sets = 0;
			int len = row * col;
			parent = new int[len];
			size = new int[len];
			help = new int[len];
		}

		private int index(int r, int c) {
			return r * col + c;
		}

		private int find(int i) {
			int hi = 0;
			while (i != parent[i]) {
				help[hi++] = i;
				i = parent[i];
			}
			for (hi--; hi >= 0; hi--) {
				parent[help[hi]] = i;
			}
			return i;
		}

		private void union(int r1, int c1, int r2, int c2) {
			//两个点不越界能连
			if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
				return;
			}
			int i1 = index(r1, c1);
			int i2 = index(r2, c2);
			//有1才会连,全没初始化不用连
			if (size[i1] == 0 || size[i2] == 0) {
				return;
			}
			int f1 = find(i1);
			int f2 = find(i2);
			if (f1 != f2) {
				if (size[f1] >= size[f2]) {
					size[f1] += size[f2];
					parent[f2] = f1;
				} else {
					size[f2] += size[f1];
					parent[f1] = f2;
				}
				sets--;
			}
		}

		public int connect(int r, int c) {
			//算出下标
			int index = index(r, c);
			//空降的1从来没出现过,出现过就不管了
			if (size[index] == 0) {
				//自己的父是自己
				parent[index] = index;
				//大小是1
				size[index] = 1;
				//集合数量+1
				sets++;
				//上下左右合并一下试试
				union(r - 1, c, r, c);
				union(r + 1, c, r, c);
				union(r, c - 1, r, c);
				union(r, c + 1, r, c);
			}
			return sets;
		}

	}

	// 课上讲的如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法
	public static List<Integer> numIslands22(int m, int n, int[][] positions) {
		UnionFind2 uf = new UnionFind2();
		List<Integer> ans = new ArrayList<>();
		for (int[] position : positions) {
			ans.add(uf.connect(position[0], position[1]));
		}
		return ans;
	}

	public static class UnionFind2 {
		private HashMap<String, String> parent;
		private HashMap<String, Integer> size;
		private ArrayList<String> help;
		private int sets;

		public UnionFind2() {
			parent = new HashMap<>();
			size = new HashMap<>();
			help = new ArrayList<>();
			sets = 0;
		}

		private String find(String cur) {
			while (!cur.equals(parent.get(cur))) {
				help.add(cur);
				cur = parent.get(cur);
			}
			for (String str : help) {
				parent.put(str, cur);
			}
			help.clear();
			return cur;
		}

		private void union(String s1, String s2) {
			//parent里得有这两个点,没有说明这俩点有问题
			if (parent.containsKey(s1) && parent.containsKey(s2)) {
				String f1 = find(s1);
				String f2 = find(s2);
				if (!f1.equals(f2)) {
					int size1 = size.get(f1);
					int size2 = size.get(f2);
					String big = size1 >= size2 ? f1 : f2;
					String small = big == f1 ? f2 : f1;
					parent.put(small, big);
					size.put(big, size1 + size2);
					sets--;
				}
			}
		}

		public int connect(int r, int c) {
			//可以用17_1009表示空降到17行1009列一个1
			String key = String.valueOf(r) + "_" + String.valueOf(c);
			//这个key没来过
			if (!parent.containsKey(key)) {
				//新建个节点,自己是自己父亲
				parent.put(key, key);
				//自己是自己集合的点,初始化为1
				size.put(key, 1);
				//多了新的点,所以集合数+1
				sets++;
				//合并上下左右
				String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
				String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
				String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
				String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
				union(up, key);
				union(down, key);
				union(left, key);
				union(right, key);
			}
			return sets;
		}

	}


	//如果整个二维数组极大,且有极多的0,1  怎么并行地把这个题做出来
	//首先分块,可以分两块,四块
	//比如分两块

	//1 1 1 1 1 1 1 |1 1 1 1 1 1 1
	//1 0 0 0 0 0 0 |0 0 0 0 0 0 1
	//1 0 0 1 1 1 1 |1 1 1 1 1 1 1
	//1 0 0 1 0 0 0 |0 0 0 0 0 0 0
	//1 0 0 1 1 1 1 |1 1 1 1 1 1 1
	//1 1 1 1 1 1 1 |1 1 1 1 1 1 1

	//可以看到,一个岛,被分开后变成了4个岛,需要标记每个岛在边界是属于哪个岛的
	//不如,第一行左边那个是A岛,中间是B岛,右边第一行是C岛,右边中间是D岛
	//合并到一起的时候,第一行A岛和C岛是连接在一起的,{A,C},岛-1
	//B岛和c岛也是一起的.{ABC},岛-1
	//B和D也是一起的,{ABCD} 岛-1
	//A和D也是一起的,因为已经是一起的了,所以岛不再减了,最后只有一个岛
	//该算法可以用来分无数块统计中国陆地和海洋有多少集合等等

}
