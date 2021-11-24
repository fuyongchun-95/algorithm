package class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//有一堆有序线段,现在包含最多线段的公共段一共包含多少条线段
public class Code01_CoverMax {

    //普通方法,比如线段总范围是1到100,那就取每个0.5  比如1.5,遍历一遍所有线段,算出包含1.5的线段,2.5再遍历一遍,最后取出最大值
    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    //方法2
    //将线段头排序,依次放入小根堆,每次根据线段头弹出小于等于线段头的节点,并加入该线段尾的值,当前堆大小就是目前该线段头包含的线段数

    //比如 [1,5],[2,6],[2,9],[4,6]
    //第一次,堆中放入5,1开头的线段包含的公共线段数为堆大小1
    //第二次,检查,没有比2小的,放入6,堆内为5,6,堆大小为2,以2为开头的公共线段数为2
    //第三次,检查,没有比2小,放入9,堆内为5,6,9 堆大小为3,3开头的公共线段数为3
    //第四次,没有比4小,放入6,堆内5,6,6,9 堆大小4,4为开头公共线段数为4
    public static int maxCover2(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        //排序也是 O(N*logN)
        Arrays.sort(lines, new StartComparator());
        // 小根堆，每一条线段的结尾数值，使用默认的
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        //外层,N次,内层,总体一共N次进出堆,进出都是logN,2*N*logN  O(N*logN)
        for (int i = 0; i < lines.length; i++) {
            // lines[i] -> cur  在黑盒中，把<=cur.start 东西都弹出
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static int maxCover3(int[][] m) {
        //构建m.length的线段对象
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            //m[0][0]是第一个线段起始位置,m[0][1]是第一个线段的终止位置
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        //按线段起始位置排序
        Arrays.sort(lines, new StartComparator());
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        //定义最大值,开始放入最后一个数,弹出小于起始值的数,max统计堆中剩下的值
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static class EndComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.end - o2.end;
        }

    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static class StartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }

    }

    public static void main(String[] args) {

        Line l1 = new Line(4, 9);
        Line l2 = new Line(1, 4);
        Line l3 = new Line(7, 15);
        Line l4 = new Line(2, 4);
        Line l5 = new Line(4, 6);
        Line l6 = new Line(3, 7);

        // 底层堆结构，heap
        PriorityQueue<Line> heap = new PriorityQueue<>(new StartComparator());
        heap.add(l1);
        heap.add(l2);
        heap.add(l3);
        heap.add(l4);
        heap.add(l5);
        heap.add(l6);

        while (!heap.isEmpty()) {
            Line cur = heap.poll();
            System.out.println(cur.start + "," + cur.end);
        }

        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

}
