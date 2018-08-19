/*******************************************************************
 * Reads in numbers from the user until end-of-file then prints  
 * them in sorted order from smallest to largest. Uses SortedList  
 * class built by students as a first assignment using linked lists.
 * @author Beth Katz
 * February 2007
 *******************************************************************/

import java.util.Scanner;

public class SortNumbers {

	private static Scanner stdin = new Scanner(System.in);

	public static void main(String[] args) {
		SortedList myList = new SortedList( );
		double num = 0;

		System.out.println("Enter numeric values." 
				+ " Finish with return and control-D.");
		System.out.print("Next? ");
		while (stdin.hasNextDouble( )) {
			num = stdin.nextDouble( );
			myList.insert(num);
			System.out.print("Next? ");
		}
		System.out.println( );
		System.out.println(myList.toString( ));
	}	
}
