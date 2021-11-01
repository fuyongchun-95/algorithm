package class01;

import java.util.Arrays;

public class Code04_BSExist {

	//二分查找       一个数是否存在于数组中
	//二分查找需要数组有序或者左右侧可以构成淘汰逻辑
	public static boolean exist(int[] sortedArr, int num) {
		if (sortedArr == null || sortedArr.length == 0) {
			return false;
		}
		int L = 0;
		int R = sortedArr.length - 1;
		int mid = 0;
		// L..R
		while (L < R) { // L..R 至少两个数的时候
			//与(R+L)/2等效,防止计算R+L时越界,超出int范围
			mid = L + ((R - L) >> 1);
			//从第一位开始,如果mid等于num,返回
			if (sortedArr[mid] == num) {
				return true;
				//如果中间大于num,往左找,并将mid-1赋给R
			} else if (sortedArr[mid] > num) {
				R = mid - 1;
				//否则向右找,mid+1赋给L
			} else {
				L = mid + 1;
			}
		}
		//最终LR指向一个位置,判断是否等于num
		return sortedArr[L] == num;
	}
	
	// for test
	public static boolean test(int[] sortedArr, int num) {
		for(int cur : sortedArr) {
			if(cur == num) {
				return true;
			}
		}
		return false;
	}
	
	
	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}
	
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 10;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			Arrays.sort(arr);
			int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
			if (test(arr, value) != exist(arr, value)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
