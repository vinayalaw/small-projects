//*********************************************************************
// FILE NAME    : Intcoll5.java
// DESCRIPTION  : This file contains the class Intcoll5.
// AUTHOR       : Vinayak Desai
//*********************************************************************
import java.util.*;
public class Intcoll5<T>
{
	//CLASS MEMBERS
	private LinkedList<T> c;
	private int howmany;
	
/*********************************************************************/
	//METHODS//
   /**
    * CONSTRUCTOR - default empty
    */
   public Intcoll5(){
	   c = new LinkedList<T>();
	   howmany = 0;
   }
   
   /**
    * CONSTRUCTOR - constructs c to size i
    * @param i - size
    */
   public Intcoll5(int i){
	   c = new LinkedList<T>();
	   howmany = 0;
   }
   
   /**
    * METHOD copy - calling object copies obj
    * @param obj - object to be copied
    */
   public void copy(Intcoll5<T> obj){
	   c.clear();
	   c.addAll(obj.c);
	   howmany = obj.howmany;
   }
   
   /**
    * METHOD belongs - returns true if i is in collection, false otherwise
    * @param i - object to be searched for
    */
   public boolean belongs(T i){
	   if(c.contains(i)) {return true;}
	   return false;
   }
   
   /**
    * METHOD insert - adds i to collection if not 0
    * @param i - object to be added
    */
   public void insert(T i){
	   if (belongs(i)) {return;}
	   c.addFirst(i); 
	   ++howmany;
   }
   
   /**
    * METHOD omit - removes i from set iff i is in set
    * @param i - object to be removed
    */
   public void omit(T i){
	   if(belongs(i)) {
		   c.remove(i);
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
	   ListIterator<T> I=c.listIterator();
	   while(I.hasNext()) {
		   T i = I.next();
		   System.out.println(i);
	   }
   }
   
   /**
    * METHOD equals - compares obj to calling collection for equality
    * @param obj - object to be compared
    */
   public boolean equals(Intcoll5<T> obj){
	   if(this == obj) {return true;}
	   if(howmany != obj.howmany) {return false;}
	   ListIterator<T> I=c.listIterator();
	   while (I.hasNext()) {
		   T i = I.next();
		   if (!(obj.c.contains(i))) {return false;}
	   }
	   return true;   
   }
}