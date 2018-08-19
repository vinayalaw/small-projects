//*********************************************************************
// FILE NAME    : Intcoll1.java
// DESCRIPTION  : This file contains the class Intcoll1.
//*********************************************************************

public class Intcoll1
{
   private int[] c;
   
   /**
    * CONSTRUCTOR - default
    */
   public Intcoll1()
   {
      	c = new int[2+1];
      	c[0] = 0;
   }
   
   /**
    * CONSTRUCTOR - constructs c to size i
    * @param i - size
    */
   public Intcoll1(int i)
   {
      	c = new int[i+1];
    	c[0] = 0;
   }
   
   /**
    * METHOD copy - calling object copies obj
    * @param obj - object to be copied
    */
   public void copy(Intcoll1 obj)
   {
      	if (this != obj)
      	{
	    c = new int[obj.c.length];
	    int j = 0;
	    while (obj.c[j] != 0)
	    {
		c[j] = obj.c[j]; j++;
	    }
	    c[j] = 0;
	}
   }
   
   /**
    * METHOD belongs - returns true if i is in collection, false otherwise
    * @param i - object to be searched for
    */
   public boolean belongs(int i)
   {
      	int j = 0;
      	while ((c[j] != 0)&&(c[j] != i)) j++;
      	return (c[j] != 0);
   }
   
   /**
    * METHOD insert - adds i to collection if not 0
    * @param i - object to be added
    */
   public void insert(int i)
   {
      	if (i > 0)
	{
	    int j = 0;
	    while ((c[j] != 0) && (c[j] != i)) j++;
	    if (c[j] == 0)
	    {
		if (j == c.length - 1)
		{
			int [] nCollec = new int[c.length*2+1];
			int a=0;
			while(c[a] != 0) {
				nCollec[a]=c[a];
				++a;
			}
			c = nCollec;
		}
		c[j] = i; c[j + 1] = 0;
	    }
	}
   }
   
   /**
    * METHOD omit - removes i from set iff i is in set
    * @param i - object to be removed
    */
   public void omit(int i)
   {
       if (i>0)
       {
      	  int j = 0;
      	  while ((c[j] != 0)&&(c[j] != i)) j++;
      	  if (c[j] == i)
      	  {
             int k = j+1;
             while (c[k] != 0) k++;
             c[j] = c[k-1]; c[k-1]=0;
      	  }
       }
   }
   
   /**
    * METHOD get_howmany - returns how many in collection
    */
   public int get_howmany()
   {
      	int j=0, howmany=0;

      	while (c[j]!=0) {howmany++; j++;}
      	return howmany;
   }
   
   /**
    * METHOD print - displays the collection
    */
   public void print()
   {
      	int j = 0;
      	System.out.println();
      	while (c[j] != 0)
      	{
           System.out.println(c[j]); j++;
      	}
   }
   /**
    * METHOD equals - compares obj to calling collection for equality
    * @param obj - object to be compared
    */
   public boolean equals(Intcoll1 obj)
   {
      	int j = 0; boolean result = true;
      	while ((c[j] != 0)&&result)
      	{
           result = obj.belongs(c[j]); j++;
      	}
      	j = 0;
      	while ((obj.c[j] != 0)&&result)
      	{
           result = belongs(obj.c[j]); j++;
      	}
      	return result;
   }
}