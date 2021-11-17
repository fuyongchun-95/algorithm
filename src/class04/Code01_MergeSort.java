package class04;

//归并排序
public class Code01_MergeSort {

    // 递归方法实现
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    // 请把arr[L..R]排有序
    // l...r N
    // T(N) = 2 * T(N / 2) + O(N)
    // O(N * logN)
    public static void process(int[] arr, int L, int R) {
        if (L == R) { // base case
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 要么p1越界了，要么p2越界了
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    // 非递归方法实现
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 步长
        int mergeSize = 1;
        while (mergeSize < N) { // log N
            // 当前左组的，第一个位置
            int L = 0;
            while (L < N) {
                if (mergeSize >= N - L) {
                    break;
                }
                int M = L + mergeSize - 1;
                int R = M + Math.min(mergeSize, N - M - 1);
                merge(arr, L, M, R);
                L = R + 1;
            }
            // 防止溢出
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort3(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }


    //练习mergeSort 归并排序
    public static void mergeSort3(int[] arr) {
        //不存在或长度为1,返回
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    //递归写出架子
    public static void process1(int[] arr, int L, int R) {
        //baseCase
        if (L == R) {
            return;
        }
        int mid = L + (R - L) >> 1;
        //排序左侧
        process(arr, L, mid);
        //排序右侧
        process(arr, mid + 1, R);
        //merge
        merge1(arr, L, mid, R);
    }

    //将l到r上的数排序
    private static void merge1(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = mid + 1;
        //不越界下,哪个小存下哪个,继续向后遍历,一样大存左侧
        while (p1 <= mid && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        //有一侧存完
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

//    // 步长
//    int mergeSize = 1;
//        while (mergeSize < N) { // log N
//        // 当前左组的，第一个位置
//        int L = 0;
//        while (L < N) {
//            if (mergeSize >= N - L) {
//                break;
//            }
//            int M = L + mergeSize - 1;
//            int R = M + Math.min(mergeSize, N - M - 1);
//            merge(arr, L, M, R);
//            L = R + 1;
//        }
//        // 防止溢出
//        if (mergeSize > N / 2) {
//            break;
//        }
//        mergeSize <<= 1;
//    }
    //练习非递归mergeSort
    public static void mergeSort4(int[] arr){
        if (arr==null||arr.length<2){
            return;
        }
        int N = arr.length;
        int mergeSize=1;
        //每轮mergeSize翻倍
        while (mergeSize<N){
            //左边从0开始
            int L=0;
            //L<N,继续遍历
            while (L<N) {
                //mergeSize大于N-L,说明只有左侧,左侧有序,直接返回
                if (mergeSize >= N - L) {
                    break;
                }
                //求出中点和右边
                int M = L + mergeSize - 1;
                int R = M+Math.min(mergeSize,N-M-1);
                merge(arr,L,M,R);
                //左边右移,开始下一组merge
                L=R+1;
            }
            //mergeSize如果大于 n/2 说明接下来mergeSize会大于n,可能会溢出整型范围,所以用n/2提前处理返回
            if (mergeSize>(N>>1)){
                break;
            }
            mergeSize<<=1;
        }
    }
}
