package class02;

public class Code01_Swap {
	
	public static void main(String[] args) {

		
		
		
		
		
		int a = 16;
		int b = 603;
		
		System.out.println(a);
		System.out.println(b);
		
		//首先ab不能在内存中同一个位置
		//a=a^b    b=a^b(代码公式)=a^b^b(代入目前ab的值)=a   a=a^b(代码公式)=a^b^a(代入目前ab的值)=b
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		
		
		System.out.println(a);
		System.out.println(b);
		
		
		
		
		int[] arr = {3,1,100};
		//当该公式中两变量位于内存中同一位置(a的值肯定也等于b),第一步a^b相当a=a^a=0=b  之后的运算没有意义了
		int i = 0;
		int j = 0;
		
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
		
		System.out.println(arr[i] + " , " + arr[j]);
		
		
		
		
		
		
		
		
		
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		
		swap(arr, 0, 0);
		
		System.out.println(arr[0]);
		System.out.println(arr[2]);
		
		
		
	}
	
	
	public static void swap (int[] arr, int i, int j) {
		// arr[0] = arr[0] ^ arr[0];
		arr[i]  = arr[i] ^ arr[j];
		arr[j]  = arr[i] ^ arr[j];
		arr[i]  = arr[i] ^ arr[j];
	}
	
	

}
