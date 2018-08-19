//*********************************************************************
// FILE NAME    : Intcoll3.java
// DESCRIPTION  : This file contains the class Intcoll3.
//*********************************************************************\

public class Intcoll3
{
   private boolean[] c;
   private int howmany;
   
   /**
    * CONSTRUCTOR - default empty
    */
   public Intcoll3()
   {
      	c = new boolean[2];
      	howmany=0;
   }
   
   /**
    * CONSTRUCTOR - constructs c to size i
    * @param i - size
    */
   public Intcoll3(int i)
   {
      	c = new boolean[i];
    	howmany=0;
   }
   
   /**
    * METHOD copy - calling object copies obj
    * @param obj - object to be copied
    */
   public void copy(Intcoll3 obj)
   {
      	if (this != obj)
      	{
      	c = new boolean[obj.c.length];
	    for(int ind=0;ind<obj.c.length;ind++) {
	    	if(obj.c[ind]) {
	    		c[ind]=true;
	    	}
	    }
	    howmany=obj.howmany;
	}
   }
   
   /**
    * METHOD belongs - returns true if i is in collection, false otherwise
    * @param i - object to be searched for
    */
   public boolean belongs(int i)
   {
      	if((i<c.length)) {
      		return c[i];
      	}
      	return false;
   }
   
   /**
    * METHOD insert - adds i to collection if not 0
    * @param i - object to be added
    */
   public void insert(int i)
   {
	   if(c.length<=i) {
		   boolean[] nCollec = new boolean[i*2+1];
		   for(int ind=0;ind<c.length;++ind) {
			   if(c[ind]) {
				   nCollec[ind]=true;
			   }
		   }
		   c = nCollec;
	   }
	   c[i]=true;
	   ++howmany;
   }
   
   /**
    * METHOD omit - removes i from set iff i is in set
    * @param i - object to be removed
    */
   public void omit(int i)
   {
	   if(c.length>i) {
		   c[i]=false;
		   --howmany;
	   }
   }
   
   /**
    * METHOD get_howmany - returns how many in collection
    */
   public int get_howmany()
   {
      	return howmany;
   }
   
   /**
    * METHOD print - displays the collection
    */
   public void print()
   {
      	System.out.println();
      	for(int j=0;j<c.length;j++)
      	{
           if(c[j]) {System.out.println(j);}
      	}
   }
   
   /**
    * METHOD equals - compares obj to calling collection for equality
    * @param obj - object to be compared
    */
   public boolean equals(Intcoll3 obj)
   {
      if(this==obj) {return true;}
      if(howmany!=obj.howmany) {return false;}
      else {
    	  for(int ind=0;ind<c.length;++ind) {
    		  if(c[ind]&&!(obj.belongs(ind))) {
    			  return false;
    		  }
    	  }
      }
      return true;
   }
}