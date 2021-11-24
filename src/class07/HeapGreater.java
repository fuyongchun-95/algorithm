package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

//加强堆,实现自己的功能更多的堆
/*
 * T一定要是非基础类型，有基础类型需求包一层
 */
public class HeapGreater<T> {

	private ArrayList<T> heap;
	private HashMap<T, Integer> indexMap;
	private int heapSize;
	private Comparator<? super T> comp;

	public HeapGreater(Comparator<T> c) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>();
		heapSize = 0;
		comp = c;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public int size() {
		return heapSize;
	}

	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}

	public T peek() {
		return heap.get(0);
	}

	//加节点,在堆中加,map中加,heapinsert找到自己位置
	public void push(T obj) {
		heap.add(obj);
		indexMap.put(obj, heapSize);
		heapInsert(heapSize++);
	}

	//弹出,从堆顶弹出一个元素,把堆最后一个放到堆顶,堆删除最后一个元素,第一个元素下沉到最终位置
	public T pop() {
		T ans = heap.get(0);
		swap(0, heapSize - 1);
		indexMap.remove(ans);
		heap.remove(--heapSize);
		heapify(0);
		return ans;
	}

	//删除,删除,并把堆最后元素放到删除位置,来一遍插入和下沉,只会走一个到达最终位置
	public void remove(T obj) {
		T replace = heap.get(heapSize - 1);
		int index = indexMap.get(obj);
		indexMap.remove(obj);
		heap.remove(--heapSize);
		if (obj != replace) {
			heap.set(index, replace);
			indexMap.put(replace, index);
			resign(replace);
		}
	}

	public void resign(T obj) {
		heapInsert(indexMap.get(obj));
		heapify(indexMap.get(obj));
	}

	// 请返回堆上的所有元素
	public List<T> getAllElements() {
		List<T> ans = new ArrayList<>();
		for (T c : heap) {
			ans.add(c);
		}
		return ans;
	}

	//每次插入,跟爹比较,交换
	private void heapInsert(int index) {
		while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	//每次下沉,如果小根堆,跟儿子中大的比较,自己小就下落,继续
	private void heapify(int index) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
			best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
			if (best == index) {
				break;
			}
			swap(best, index);
			index = best;
			left = index * 2 + 1;
		}
	}

	//交换的同时,将映射map也交换,保证根据id能找到对象
	private void swap(int i, int j) {
		T o1 = heap.get(i);
		T o2 = heap.get(j);
		heap.set(i, o2);
		heap.set(j, o1);
		indexMap.put(o2, i);
		indexMap.put(o1, j);
	}

}
