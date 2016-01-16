package org.liuy.music.tools;

import java.util.Random;

public class testRandom {

	public static void main(String[] args) {
		//testRandom.GetRandomNum(2, 1, 2);
		int[]aa = {1,2,3,4,5,6,7,8,9};
		//testRandom.GetRandomNum2(aa, 2);
		
		int[]bb = CreateRandomNumList.GetRandomNum2(aa, 100) ;		 
		System.out.println("--------------------------");
		for(int  j =0 ; j<bb.length ;j++){
			System.out.print(bb[j]+" ");
		}
	}
	
	/**
	 * randnum 随机数个数
	 * 
	 * rangeend 结束范围值
	 * */
	public  static void GetRandomNum(int randnum,int rangestart,int rangeend ) {
		Random random = new Random (); 
		int rangeend1 = rangeend-rangestart+1 ;
        boolean[]  bool = new boolean[rangeend1];  
        int randInt = 0; 
        int resunum = 0;
        for(int j = 0; j < randnum ; j++) {  
            /**得到randnum个不同的随机数*/  
            do{  
                randInt  = random.nextInt(rangeend1);                 
            }while(bool[randInt]);               
            bool[randInt] = true;  
            resunum += rangestart;            
            System.out.println(resunum);
        }
	}
	/**
	 * nice
	 * 
	 * randnum 随机数个数
	 * 
	 * changenum 循环交换的次数
	 * 
	 * 把源数据放到数组中，然后循环随机交换这个数组元素的位置就可以了
	 * */
	public  static void GetRandomNum2(int[]aa ,int changenum ) {
		//int[]aa = {1,2,5,6,8,9,7};
		Random r = new Random();
		int arraylength = aa.length ;
		for(int i=0;i<changenum;i++){  
			int index = r.nextInt(arraylength);
			int temp = aa[0];
			aa[0] = aa[index];
			aa[index] = temp;
		}
		System.out.println("--------------------------");
		for(int  j =0 ; j<arraylength ;j++){
			System.out.print(aa[j]+" ");
		}
	}
}
