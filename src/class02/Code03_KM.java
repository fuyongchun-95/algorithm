package class02;

import java.util.HashMap;
import java.util.HashSet;

public class Code03_KM {

	//稳妥的hash表方法,每种数字存入键值对,遍历key取value与k比较,得出结果
	public static int test(int[] arr, int k, int m) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int num : arr) {
			if (map.containsKey(num)) {
				map.put(num, map.get(num) + 1);
			} else {
				map.put(num, 1);
			}
		}
		for (int num : map.keySet()) {
			if (map.get(num) == k) {
				return num;
			}
		}
		return -1;
	}

	public static HashMap<Integer, Integer> map = new HashMap<>();

	// 请保证arr中，只有一种数出现了K次，其他数都出现了M次
	public static int onlyKTimes(int[] arr, int k, int m) {
		if (map.size() == 0) {
			mapCreater(map);
		}
		int[] t = new int[32];
		// t[0] 0位置的1出现了几个
		// t[i] i位置的1出现了几个
		for (int num : arr) {
			while (num != 0) {
				//rightOne:最右边的1
				int rightOne = num & (-num);
				//map的key存的各个位置1的大小,value存的位置,t数组给这个位置+1,最后没个位置统计的是所有数字在该位上1的个数
				t[map.get(rightOne)]++;
				//num减去最右边的1直到为0
				num ^= rightOne;
			}
		}
		//给一个ans=00000000000000
		//用来往上+1,最后填完还没返回-1这个ans就是k次的那个数字
		int ans = 0;
		//32位,从0位开始,t[0]位置为所有该位有1的相加,模m剩下的>0,表示另一种数字该位为1,反之为0
		for (int i = 0; i < 32; i++) {
			//模m不为0,另一种数字该位为1
			if (t[i] % m != 0) {
				//如果剩下的为k,表示另一个数字出现了k次,满足题意
				if (t[i] % m == k) {
					//ans或上把1左移i次,就是给ans第i位变成1
					//ans=000000000000000000000
					//ans=000000000000000100000
					ans |= (1 << i);
					//剩下的不为k,表示另一种数字只是次数比m少的一个数字,并不是k,返回-1
				} else {
					return -1;
				}
			}
		}
		//如果k=3 但是有5个0,这时候ans还是0,但是0的个数并不是k次,所以针对0再遍历一次,检查0元素是否存在,是否为k次
		if (ans == 0) {
			int count = 0;
			for (int num : arr) {
				if (num == 0) {
					count++;
				}
			}
			if (count != k) {
				return -1;
			}
		}
		return ans;
	}

	public static void mapCreater(HashMap<Integer, Integer> map) {
		int value = 1;
		for (int i = 0; i < 32; i++) {
			map.put(value, i);
			value <<= 1;
		}
	}

	public static int[] randomArray(int maxKinds, int range, int k, int m) {
		//出现k次的数字也可以指任意小于m次的数字
		int ktimeNum = randomNumber(range);
		// 真命天子出现的次数,一般几率出现k次,一半几率出现任意小于m的数  1~(m-1)
		int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
		// 2  最少得两种数字
		int numKinds = (int) (Math.random() * maxKinds) + 2;
		// k * 1 + (numKinds - 1) * m  定义数组长度
		int[] arr = new int[times + (numKinds - 1) * m];
		//给arr数组放入times个ktimeNum
		int index = 0;
		for (; index < times; index++) {
			arr[index] = ktimeNum;
		}
		//减去k数字一种
		numKinds--;
		HashSet<Integer> set = new HashSet<>();
		//将k放进set集合
		set.add(ktimeNum);
		//剩余数字种类不为0
		while (numKinds != 0) {
			int curNum = 0;
			//如果集合包含新的随机数,重新随机出一个当做m次数字
			do {
				curNum = randomNumber(range);
			} while (set.contains(curNum));
			//set集合放入新的m次数字,数字种类减一
			set.add(curNum);
			numKinds--;
			//arr数组放入m次数字
			for (int i = 0; i < m; i++) {
				arr[index++] = curNum;
			}
		}
		// arr 填好了,遍历一遍,随机互换一轮元素
		for (int i = 0; i < arr.length; i++) {
			// i 位置的数，我想随机和j位置的数做交换
			int j = (int) (Math.random() * arr.length);// 0 ~ N-1
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}
		return arr;
	}

	// [-range, +range]
	public static int randomNumber(int range) {
		return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
	}

	public static void main(String[] args) {
		//数组长度
		int kinds = 5;
		//数组参数值范围+-30,可以不等概率,只需要一定的随机
		int range = 30;
		//测试次数
		int testTime = 100000;
		//k和m最大值
		int max = 9;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
			int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
			int k = Math.min(a, b);
			int m = Math.max(a, b);
			// k < m
			if (k == m) {
				m++;
			}
			//生成测试数组
			int[] arr = randomArray(kinds, range, k, m);
			int ans1 = test(arr, k, m);
			int ans2 = onlyKTimes(arr, k, m);
			//结果不同,打印两个数组
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");

	}

}
