package com.julius.jobmanagementsystem.automaticrating.utils;


public class VectorUtil {

	public static double[] add(double[] a , double[] b){
//		System.out.println(a.length+";"+b.length);
		for(int i = 0 ; i < a.length ; i++){
			a[i] = a[i] + b[i];
		}
		return a;
	}
	
	public static double[] center(double[] a, int length){
		for(int i = 0 ; i < a.length ; i++){
			a[i] = a[i] / length;
		}
		return a;
	}
	
	
}
