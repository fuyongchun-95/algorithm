package class01;

public class Code06_BSAwesome {

	//任意一个局部最小值
	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		//检查arr[0]局部最小
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		//检查arr[arr.length-1]局部最小
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		//左右都不是局部最小,说明左边趋势向下,右边趋势向上,中间必有局部最小
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			//中点比左侧大,往左找,中点取代右节点成为新的右边界缩小范围
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
				//中点比右侧大,往右找
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
				//比两侧都小,结束
			} else {
				return mid;
			}
		}
		//其他都不是局部最小,左右指向同一处时该位置必有一个最小
		return left;
	}

}
