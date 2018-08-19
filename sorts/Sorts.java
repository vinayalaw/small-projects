/*
 * AUTHOR:		Vinayak Desai
 * FILENAME: 	Sorts.java
 * DESCRIPTION: This program generates arrays of various sizes,
 * 				fills them, and sorts each with merge/quick/
 * 				insertion sorts then generates a table showing
 * 				# comparisons for each in k runs
 */
import java.util.Scanner;
import java.util.Random;
public class Sorts {
	private static int qcount,icount,m;
	
	public static void main(String[] args) {
		int numTrials = 0;
		Random rand = new Random();
		int i;
		Scanner t = new Scanner(System.in);
		System.out.println("How many trials should be run?: ");
		numTrials = t.nextInt();
		t.close();
		//all statistical values held in these arrays
		int allNums [][][]= new int[3][4][numTrials];
		double mean [][] = new double [3][4];
		int max [][] = new int [3][4];
		int min [][] = new int [3][4];
		
		//trials for sorting array sized 2048
		for(i = 0; i < numTrials; i++) {
			//reset variables needed for each trial
			qcount = 0; icount = 0; m = 0;
			int[] list= new int[2048];
			for(int e=0;e<2048;e++) { list[e] = rand.nextInt(999999999);}
			//execution of the sorts with pass by copy
			quickSort(list,0,list.length);
			insertionSort(list);
			mergeSort(list,0,list.length);
			//adding count values to array for recordkeeping
			allNums[0][0][i]=qcount;
			allNums[1][0][i]=icount;
			allNums[2][0][i]=m;
		}
		//trials for sorting array sized 4096
		for(i = 0; i < numTrials; i++) {
			qcount = 0; icount = 0; m = 0;
			int[] list = new int[4096];
			for(int e=0;e<4096;e++) { list[e] = rand.nextInt(999999999);}
			quickSort(list,0,list.length);
			insertionSort(list);
			mergeSort(list,0,list.length);
			allNums[0][1][i]=qcount;
			allNums[1][1][i]=icount;
			allNums[2][1][i]=m;
		}
		//trials for sorting array sized 8192
		for(i = 0; i < numTrials; i++) {
			qcount = 0; icount = 0; m = 0;
			int[] list = new int[8192];
			for(int e=0;e<8192;e++) { list[e] = rand.nextInt(999999999);}
			quickSort(list,0,list.length);
			insertionSort(list);
			mergeSort(list,0,list.length);
			allNums[0][2][i]=qcount;
			allNums[1][2][i]=icount;
			allNums[2][2][i]=m;
		}
		//trials for sorting array sized 16384
		for(i = 0; i < numTrials; i++) {
			qcount = 0; icount = 0; m = 0;
			int[] list = new int[16384];
			for(int e=0;e<16384;e++) { list[e] = rand.nextInt(999999999);}
			quickSort(list,0,list.length);
			insertionSort(list);
			mergeSort(list,0,list.length);
			allNums[0][3][i]=qcount;
			allNums[1][3][i]=icount;
			allNums[2][3][i]=m;
		}
		//calculation of mean, max, and min completed concurrently
		for(i = 0; i < 3;i++) {
			for(int j = 0; j < 4; j++) {
				int sum = 0;
				int maximum=0;
				int minimum=Integer.MAX_VALUE;
				for(int k = 0; k < numTrials; k++) {
					int curr = allNums[i][j][k];
					sum+=curr;
					if(maximum<curr) {
						maximum = curr;
					}
					if(minimum>curr) {
						minimum = curr;
					}
					
				}
				mean[i][j]= sum/numTrials;
				max[i][j]=maximum;
				min[i][j]=minimum;
			}
		}
		//output of table
		for(i = 0; i < 3; i++) {
			String sortt="";
			if(i == 0) {sortt=("Quick");}
			if(i == 1) {sortt=("Insertion");}
			if(i == 2) {sortt=("Merge");}
			System.out.printf("%15s %15s %15s %15s\n", sortt+"(size)", "MEAN", "MIN", "MAX");
			for(int k = 0; k<4;k++) {			
				System.out.printf("%15.2f %15.2f %15d %15d\n", 2048*java.lang.Math.pow(2, k), mean[i][k], min[i][k], max[i][k]);
			}
			System.out.println();
		}
	}
	
	private static void quickSort(int [] a, int i, int j) {
		int p;
		if(i<j) {
			p = partition (a, i ,j-1);
			quickSort(a, i, p-1);
			quickSort(a, p, j-1);
		}
	}
	private static int partition(int[] a, int i, int j) {
		int hold = a[i], upper = i, lower = j;
		//loops until upper meets lower
		while(upper != lower) {
			
			//decrements lower
			while(upper < lower && a[lower]<=hold) {
				lower--;
				qcount++;
			}
			//swaps if value a[lower]<=hold
			if(upper != lower) {
				a[upper] = a[lower];
			}
			//increments upper
			while(upper < lower && hold<=a[upper]) {
				upper++;
				qcount++;
			}
			//swaps if hold<=a[upper]
			if(upper != lower) {
				a[lower] = a[upper];
			}
		}
		a[upper] = hold;
		//upper will be the final position of the pivot(1st element in subarray)
		return upper;
	}

	private static void mergeSort(int [] a, int first, int last) {
		if(last-first > 1) {
			int mid = first + (last - first) / 2;
			mergeSort(a, first, mid-1);
			mergeSort(a, mid, last-1);
			//combines sub-arrays into sorted order
			merge(a,first,mid,last-1);
		}
	}
	private static void merge(int[] arr, int lt, int md, int rt) {
			//s1 and s2 used to define size of sub-arrays
			int s1=md-lt+1, s2=rt-md;
			//create and fill temp arrays
			int left[]=new int[s1];
			int right[]=new int[s2];
			for(int i=0;i<s2;i++) {
				left[i]= arr[lt+i];
			}
			for(int i=0;i<s2;i++) {
				right[i]= arr[md+1+i];
			}
			//MERGING
			//i tracks elements in left[] while j tracks elements in right[]
			int i = 0, j = 0;
			//k tracks elements in original array
			int k = lt;
			//loops until i or j would exceed bounds of temp arrays, left and right
			while (i<s1 && j<s2) {
				//put in whatever value of left or right is less than the other
				if(left[i]<= right[j]) {
					arr[k]=left[i];
					i++;
				}
				else {
					arr[k]=right[j];
					j++;
				}
				m++;
				k++;
			}
			//copy over remaining elements if any
			while(i<s1) {
				arr[k]=left[i];
				i++;k++;m++;
			}
			while(j<s2) {
				arr[k]=right[j];
				j++;k++;m++;
			}
	}

	private static void insertionSort(int [] a) {
		for(int i = 1; i < a.length-1; i++) {
			int hold = a[i];
			int j = i-1;
			while(j >= 0 && a[j] > hold) {
				a[j+1] = a[j];
				j--;
				icount++;
			}
			a[j+1]=hold;
		}
	}
}
