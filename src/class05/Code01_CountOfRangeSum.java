package class05;

import java.util.WeakHashMap;

// 这道题直接在leetcode测评：
// https://leetcode.com/problems/count-of-range-sum/
//有多少子数组和在lower到upper之间
public class Code01_CountOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //构造前缀和数组
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >> 1);
        return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
                + merge(sum, L, M, R, lower, upper);
    }

    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // [windowL, windowR)
        for (int i = M + 1; i <= R; i++) {
            //算出当前索引符合lower到upper的范围
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            //左右都向前推r<=max,l>min就继续推
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }
            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            //最终返回r-l就是当前索引符合的子数组个数
            ans += windowR - windowL;
        }
        long[] help = new long[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

    //练习
    public static int merge1(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        //左右窗口
        int winR = L;
        int winL = L;
        //遍历右侧
        for (int i = M + 1; i < R; i++) {
            //计算左右边界 即右侧当前数 比如6  lowerupper [-1,2]  则在当前数下左侧应满足的范围是 [6-2,6-(-1)) 即[4,7]
            //左测[1,3,4,5,6,7,8]
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (winR <= M && arr[winR] <= max) {
                //winR=6
                winR++;
            }
            while (winL <= M && winL < min) {
                //winL=2
                winL++;
            }
            //[winL,winR)
            ans += winR - winL;
        }
        //归并
        long[] help = new long[R - L + 1];
        int p1 = L;
        int p2 = M + 1;
        int i = 0;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }
}
