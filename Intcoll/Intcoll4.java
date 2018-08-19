//*********************************************************************
// FILE NAME    : Intcoll4.java
// DESCRIPTION  : This file contains the class Intcoll4.
// AUTHOR       : Vinayak Desai
//*********************************************************************

public class Intcoll4
{
	//CLASS MEMBERS
	private ListNode head;
	private int howmany;
	
/*********************************************************************/
	//METHODS//
   /**
    * CONSTRUCTOR - default empty
    */
   public Intcoll4(){
	   head = new ListNode();
	   howmany = 0;
   }
   
   /**
    * CONSTRUCTOR - constructs c to size i
    * @param i - size
    */
   public Intcoll4(int i){
	   head = new ListNode();
	   howmany = 0;
   }
   
   /**
    * METHOD copy - calling object copies obj
    * @param obj - object to be copied
    */
   public void copy(Intcoll4 obj){
	   ListNode curr = obj.head;
	   howmany = 0;
	   head = new ListNode();
	   while(curr.next != null) {
		   insert(curr.next.data);
		   curr=curr.next;
	   }
   }
   
   /**
    * METHOD belongs - returns true if i is in collection, false otherwise
    * @param i - object to be searched for
    */
   public boolean belongs(int i){
	   ListNode curr = head;
	   while(curr.next != null && curr.data != i) {
		   curr = curr.next;
	   }
	   return (curr.data==i);
   }
   
   /**
    * METHOD insert - adds i to collection if not 0
    * @param i - object to be added
    */
   public void insert(int i){
	   if (belongs(i)) {return;}
	   ListNode curr = head;
	   while(curr.next != null) {
		   curr = curr.next;
	   }
	   curr.next = new ListNode(i, null); 
	   ++howmany;
   }
   
   /**
    * METHOD omit - removes i from set iff i is in set
    * @param i - object to be removed
    */
   public void omit(int i){
	   if(belongs(i)) {
		   ListNode curr = head;
		   while(curr.next.data != i) {
			   curr = curr.next;
		   }
		   curr.next = curr.next.next;
		   --howmany;
	   }	   
   }
   
   /**
    * METHOD get_howmany - returns how many in collection
    */
   public int get_howmany(){
	   return howmany;
   }
   
   /**
    * METHOD print - displays the collection
    */
   public void print(){
	   ListNode curr = head.next;
	   while (curr != null) {
		   System.out.println(curr.data);
		   curr = curr.next;
	   }
   }
   
   /**
    * METHOD equals - compares obj to calling collection for equality
    * @param obj - object to be compared
    */
   public boolean equals(Intcoll4 obj){
	   if(this == obj) {return true;}
	   if(howmany != obj.howmany) {return false;}
	   ListNode a = head.next;
	   while (a != null) {
		   if (!(obj.belongs(a.data))) {return false;}
		   a = a.next;
	   }
	   return true;	   
   }
   /*********************************************************************/
   private class ListNode{
   	private int data;
   	private ListNode next;
   	private ListNode() {
   		data = 0;
   		next = null;
   	}
   	private ListNode(int d, ListNode n) {
   		data = d;
   		next = n;
   	}
   }

   /*********************************************************************/	   
}
