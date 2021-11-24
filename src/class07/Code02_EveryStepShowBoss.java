package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

//见day08视频2小时左右

public class Code02_EveryStepShowBoss {

	public static class Customer {
		public int id;
		public int buy;
		public int enterTime;

		public Customer(int v, int b, int o) {
			id = v;
			buy = b;
			enterTime = 0;
		}
	}

	public static class CandidateComparator implements Comparator<Customer> {

		@Override
		public int compare(Customer o1, Customer o2) {
			return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
		}

	}

	public static class DaddyComparator implements Comparator<Customer> {

		@Override
		public int compare(Customer o1, Customer o2) {
			return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
		}

	}

	//使用两个堆进行优化
	public static class WhosYourDaddy {
		private HashMap<Integer, Customer> customers;
		private HeapGreater<Customer> candHeap;
		private HeapGreater<Customer> daddyHeap;
		private final int daddyLimit;

		//构造方法,获奖区按购买数倒序,按时间正序,候选区按购买数正序,按时间倒序,limit为获奖区大小
		public WhosYourDaddy(int limit) {
			customers = new HashMap<Integer, Customer>();
			candHeap = new HeapGreater<>(new CandidateComparator());
			daddyHeap = new HeapGreater<>(new DaddyComparator());
			daddyLimit = limit;
		}

		// 当前处理i号事件，arr[i] -> id,  buyOrRefund
		//应该是一个大根堆一个小根堆,互相push和pop值的问题
		public void operate(int time, int id, boolean buyOrRefund) {
			//退货并且没客户,直接下一轮遍历
			if (!buyOrRefund && !customers.containsKey(id)) {
				return;
			}
			//没客户,插入一个新客户
			if (!customers.containsKey(id)) {
				customers.put(id, new Customer(id, 0, 0));
			}
			Customer c = customers.get(id);
			if (buyOrRefund) {
				c.buy++;
			} else {
				c.buy--;
			}
			if (c.buy == 0) {
				customers.remove(id);
			}
			//两个区都没有这个客户
			if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
				//获奖区没满,直接进获奖区
				if (daddyHeap.size() < daddyLimit) {
					c.enterTime = time;
					daddyHeap.push(c);
				} else {
					//进候选区
					c.enterTime = time;
					candHeap.push(c);
				}
				//等待区有这个用户
			} else if (candHeap.contains(c)) {
				if (c.buy == 0) {
					candHeap.remove(c);
				} else {
					//更换到最终位置
					candHeap.resign(c);
				}
			} else {
				//获奖区buy归零,干掉
				if (c.buy == 0) {
					daddyHeap.remove(c);
				} else {
					//放入获奖堆最终位置
					daddyHeap.resign(c);
				}
			}
			daddyMove(time);
		}

		public List<Integer> getDaddies() {
			List<Customer> customers = daddyHeap.getAllElements();
			List<Integer> ans = new ArrayList<>();
			for (Customer c : customers) {
				ans.add(c.id);
			}
			return ans;
		}

		private void daddyMove(int time) {
			//等待区空,随便
			if (candHeap.isEmpty()) {
				return;
			}
			//获奖区有地方
			if (daddyHeap.size() < daddyLimit) {
				//把等待区拿个放获奖区
				Customer p = candHeap.pop();
				p.enterTime = time;
				daddyHeap.push(p);
			} else {
				//获奖区没地方
				//等待区堆顶比获奖区堆顶买的多
				if (candHeap.peek().buy > daddyHeap.peek().buy) {
					//替换,两个客户都赋值time,放入两个堆中
					Customer oldDaddy = daddyHeap.pop();
					Customer newDaddy = candHeap.pop();
					oldDaddy.enterTime = time;
					newDaddy.enterTime = time;
					daddyHeap.push(newDaddy);
					candHeap.push(oldDaddy);
				}
			}
		}

	}

	public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
		List<List<Integer>> ans = new ArrayList<>();
		WhosYourDaddy whoDaddies = new WhosYourDaddy(k);
		for (int i = 0; i < arr.length; i++) {
			whoDaddies.operate(i, arr[i], op[i]);
			ans.add(whoDaddies.getDaddies());
		}
		return ans;
	}

	// 干完所有的事，模拟，不优化
	public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
		HashMap<Integer, Customer> map = new HashMap<>();
		ArrayList<Customer> cands = new ArrayList<>();
		ArrayList<Customer> daddy = new ArrayList<>();
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			int id = arr[i];
			boolean buyOrRefund = op[i];
			//退货并且没这个用户
			if (!buyOrRefund && !map.containsKey(id)) {
				//把获奖的人重新放入ans,继续下一次
				ans.add(getCurAns(daddy));
				continue;
			}
			// 没有发生：用户购买数为0并且又退货了
			// 用户之前购买数是0，此时买货事件
			// 用户之前购买数>0， 此时买货
			// 用户之前购买数>0, 此时退货

			//用户不存在,新增一个,再改变购买数
			if (!map.containsKey(id)) {
				map.put(id, new Customer(id, 0, 0));
			}
			// 买、卖
			Customer c = map.get(id);
			if (buyOrRefund) {
				c.buy++;
			} else {
				c.buy--;
			}
			if (c.buy == 0) {
				map.remove(id);
			}
			// c
			// 下面做
			//这个用户两个区域都不再,说明是新用户,放入两个区域,根据daddy是否满了判断,满了就去候选区,没满就去获奖区
			if (!cands.contains(c) && !daddy.contains(c)) {
				if (daddy.size() < k) {
					c.enterTime = i;
					daddy.add(c);
				} else {
					c.enterTime = i;
					cands.add(c);
				}
			}
			//清除购买数为0的用户
			cleanZeroBuy(cands);
			cleanZeroBuy(daddy);
			//先按购买数排,购买数一样按进入时间排
			cands.sort(new CandidateComparator());
			daddy.sort(new DaddyComparator());
			move(cands, daddy, k, i);
			ans.add(getCurAns(daddy));
		}
		return ans;
	}

	public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
		//候选区都没客户,就不用有操作了
		if (cands.isEmpty()) {
			return;
		}
		// 获奖区不为空
		if (daddy.size() < k) {
			Customer c = cands.get(0);
			c.enterTime = time;
			daddy.add(c);
			cands.remove(0);
		} else { // 获奖区满了，候选区有东西
			//候选区的客户购买数大于了获奖区客户购买数,取候选区第一个换获奖区第一个(因为排序过,获奖区第一个肯定购买数少或者获奖时间早)
			//换下来的不用管购买数归零没,因为归零下次就被删掉了,而且这个函数只关心获奖区的动态变化
			if (cands.get(0).buy > daddy.get(0).buy) {
				Customer oldDaddy = daddy.get(0);
				daddy.remove(0);
				Customer newDaddy = cands.get(0);
				cands.remove(0);
				newDaddy.enterTime = time;
				oldDaddy.enterTime = time;
				daddy.add(newDaddy);
				cands.add(oldDaddy);
			}
		}
	}

	public static void cleanZeroBuy(ArrayList<Customer> arr) {
		List<Customer> noZero = new ArrayList<Customer>();
		for (Customer c : arr) {
			if (c.buy != 0) {
				noZero.add(c);
			}
		}
		arr.clear();
		for (Customer c : noZero) {
			arr.add(c);
		}
	}

	public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
		List<Integer> ans = new ArrayList<>();
		for (Customer c : daddy) {
			ans.add(c.id);
		}
		return ans;
	}

	// 为了测试
	public static class Data {
		public int[] arr;
		public boolean[] op;

		public Data(int[] a, boolean[] o) {
			arr = a;
			op = o;
		}
	}

	// 为了测试
	public static Data randomData(int maxValue, int maxLen) {
		int len = (int) (Math.random() * maxLen) + 1;
		int[] arr = new int[len];
		boolean[] op = new boolean[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * maxValue);
			op[i] = Math.random() < 0.5 ? true : false;
		}
		return new Data(arr, op);
	}

	// 为了测试
	public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
		if (ans1.size() != ans2.size()) {
			return false;
		}
		for (int i = 0; i < ans1.size(); i++) {
			List<Integer> cur1 = ans1.get(i);
			List<Integer> cur2 = ans2.get(i);
			if (cur1.size() != cur2.size()) {
				return false;
			}
			cur1.sort((a, b) -> a - b);
			cur2.sort((a, b) -> a - b);
			for (int j = 0; j < cur1.size(); j++) {
				if (!cur1.get(j).equals(cur2.get(j))) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int maxValue = 10;
		int maxLen = 100;
		int maxK = 6;
		int testTimes = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			Data testData = randomData(maxValue, maxLen);
			int k = (int) (Math.random() * maxK) + 1;
			int[] arr = testData.arr;
			boolean[] op = testData.op;
			List<List<Integer>> ans1 = topK(arr, op, k);
			List<List<Integer>> ans2 = compare(arr, op, k);
			if (!sameAnswer(ans1, ans2)) {
				for (int j = 0; j < arr.length; j++) {
					System.out.println(arr[j] + " , " + op[j]);
				}
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
