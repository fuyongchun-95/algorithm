package class02;

public class Code02_EvenTimesOddTimes {

	// arr中，只有一种数，出现奇数次,求这个数是什么
	public static void printOddTimesNum1(int[] arr) {
		int eor = 0;
		//全部异或,偶数次全为0,只有奇数次剩下一个,即是eor
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}

	// arr中，有两种数a,b，出现奇数次,求这两个数是什么
	public static void printOddTimesNum2(int[] arr) {
		int eor = 0;
		//先全部异或,获得结果a^b=eor
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		// a 和 b是两种数
		// eor != 0
		// eor最右侧的1，提取出来
		// eor :     00110010110111000
		// rightOne :00000000000001000
		//该公式举例, eor:     			00110010110111000
		//			~eor:     			11001101001000111
		//			~eor+1:   			11001101001001000
		//			eor & ((~eor)+1):	00000000000001000 即取出最右侧的1
		int rightOne = eor & (-eor); // 提取出最右的1
		
		
		int onlyOne = 0; // eor'
		for (int i = 0 ; i < arr.length;i++) {
			//  arr[1] =  111100011110000
			// rightOne=  000000000010000
			//遍历数组,将数组分为最右侧1与eor一致的,和该位为0的
			//因为两个奇数次数字在eor的1位置不同,所以被分为两组,一组异或得出其中一个,用eor与eor'异或
			//eor'=a,eor=a^b,eor^a=b,得出答案

			//arr[i]&rightOne != 0 right只有一个位置有1,符合的arr[i]该位为1
			if ((arr[i] & rightOne) != 0) {
				onlyOne ^= arr[i];
			}
		}
		System.out.println(onlyOne + " " + (eor ^ onlyOne));
	}


	//统计一个数字二进制1的个数
	public static int bit1counts(int N) {
		int count = 0;
		
		//   011011010000
		//   000000010000     1
		
		//   011011000000
		// 
		
		
		
		while(N != 0) {
			//该公式举例, eor:     			00110010110111000
			//			-eor:     			11001101001000111
			//			-eor+1:   			11001101001001000
			//			eor & ((-eor)+1):	00000000000001000 即取出最右侧的1
			int rightOne = N & ((~N) + 1);
			count++;
			//消掉rightOne位置的1,直到N为0
			N ^= rightOne;
			// N -= rightOne
		}
		
		
		return count;
		
	}
	
	
	public static void main(String[] args) {
		int a = 5;
		int b = 7;

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
		printOddTimesNum1(arr1);

		int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
		printOddTimesNum2(arr2);

	}

}
