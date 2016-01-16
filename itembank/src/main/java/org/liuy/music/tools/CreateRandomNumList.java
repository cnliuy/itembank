package org.liuy.music.tools;

import java.util.List;
import java.util.Random;

public class CreateRandomNumList {
	/**
	 * nice
	 * 
	 * randnum 随机数个数
	 * 
	 * changenum 循环交换的次数
	 * 
	 * 把源数据放到数组中，然后循环随机交换这个数组元素的位置就可以了
	 * */
	public  static int[] GetRandomNum2(int[]aa ,int changenum ) {
		
		Random r = new Random();
		int arraylength = aa.length ;
		for(int i=0;i<changenum;i++){  
			int index = r.nextInt(arraylength);
			int temp = aa[0];
			aa[0] = aa[index];
			aa[index] = temp;
		}
		return aa ;
	}
	
	
	public  static Long[] GetRandomNum2(Long[]aa ,int changenum ) {
		
		Random r = new Random();
		int arraylength = aa.length ;
		for(int i=0;i<changenum;i++){  
			int index = r.nextInt(arraylength);
			Long temp = aa[0];
			aa[0] = aa[index];
			aa[index] = temp;
		}
		return aa ;
	}
}
