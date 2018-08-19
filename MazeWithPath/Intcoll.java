public class Intcoll
{
   private int[] c;
   private int howmany;
   
   /**
    * CONSTRUCTOR - default empty
    */
   public Intcoll()
   {
      	c = new int[2];
      	howmany=0;
   }
   
   /**
    * CONSTRUCTOR - constructs c to size i
    * @param i - size
    */
   public Intcoll(int i)
   {
      	c = new int[i];
    	howmany=0;
   }
   
   /**
    * METHOD copy - calling object copies obj
    * @param obj - object to be copied
    */
   public void copy(Intcoll obj)
   {
      	if (this != obj)
      	{
      	int ind = 0;
      	c = new int[obj.howmany];
	    while(ind != obj.howmany) {
	    	insert(obj.c[ind]);
	    	ind++;
	    }
	}
   }
   
   /**
    * METHOD belongs - returns true if i is in collection, false otherwise
    * @param i - object to be searched for
    */
   public boolean belongs(int i)
   {
      	for (int j=0; j<howmany;j++) {
      		if (c[j]==i) {return true;}
      	}
      	return false;
   }
   
   /**
    * METHOD insert - adds i to collection if not 0
    * @param i - object to be added
    */
   public void insert(int i)
   {
	   if(howmany==c.length-1) {
		   int [] nCollec = new int[c.length*2+1];
		   int a=0;
		   while(c[a] != 0) {
			   nCollec[a]=c[a];
			   ++a;
		   }
		   c = nCollec;
	   }
	   if(!(belongs(i))) {
		   c[howmany]=i;
		   howmany++;
	   }
   }
   
   /**
    * METHOD omit - removes i from set iff i is in set
    * @param i - object to be removed
    */
   public void omit(int i)
   {
	   int j;
       for(j=0; j<howmany;j++) {
    	   if (c[j]==i) {
    		   break;
    	   }
       }
       if(j<howmany) {
    	   for(int ind=j+1;ind<howmany;ind++) {
    		   c[ind-1]=c[ind];
    	   }
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
      	for(int j=0;j<howmany;j++)
      	{
           System.out.println(c[j]);
      	}
   }
   
   /**
    * METHOD equals - compares obj to calling collection for equality
    * @param obj - object to be compared
    */
   public boolean equals(Intcoll obj)
   {
      if(this==obj) {return true;}
      if(howmany!=obj.howmany) {return false;}
      else {
    	  for (int i=0; i<howmany;i++) {
    		  if(!(obj.belongs(c[i]))) {return false;}
    	  }
      }
      return true;
   }
}