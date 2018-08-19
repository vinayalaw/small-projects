/***********************************************************************/
// FILE NAME : Intcoll6.java
// DESCRIPTION : This file contains the class Intcoll6.
/************************************************************************/

import java.util.*;
import java.io.*;

public class Intcoll6
{
     private int howmany;
     private btNode c;
     
     public Intcoll6()
     {
    	 c = null;
    	 howmany = 0;
     }
     
     public Intcoll6(int i)
     {
    	 c = null;
    	 howmany = 0;
     }
     
     private static btNode copytree(btNode t)
     {
           btNode root=null;
           if (t!=null)
           {
                root=new btNode();
                root.info=t.info;
                root.left=copytree(t.left);
                root.right=copytree(t.right);
           }
           return root;
     }
     public void copy(Intcoll6 obj)
     {
          if (this!=obj)
          {
                howmany=obj.howmany;
                c=copytree(obj.c);
          }
     }
     public void insert(int i)
     {
           if (i>0)
           {
               btNode pred=null, p=c;
               while ((p!=null)&&(p.info!=i))
               {
                     pred=p;
                     if (p.info>i) p=p.left;
                     else p=p.right;
               }
               if (p==null)
               {
                     howmany++;
                     p=new btNode(i, null, null);
                     if (pred!=null)
                     {
                           if (pred.info>i) pred.left=p;
                           else pred.right=p;
                     }
                     else c=p;
               }
          }
     }
     
     public void omit(int i)
     {
           btNode curr=c;
           btNode pred = null;
           //finding position of i on tree
           while ((curr!=null)&&(curr.info!=i)) {
        	   pred = curr;
               if (curr.info>i) {curr = curr.left;}
               else {curr = curr.right;}
           }
           //case: i is not in tree
           if(curr == null) {return;}
           //case: curr is leaf node
           else if(curr.left == null && curr.right == null) {
        	   if(pred != null) {
        		   if(pred.info>i) {
        			   pred.left = null;
        		   }
        		   else {
        			   pred.right = null;
        		   }
        	   }
        	   else {
        		   c = null;
        	   }
           }
           //case: curr has only right child to replace it
           else if(curr.left == null && curr.right != null) {
        	   if(pred != null) {
        		   if(pred.info>i) {
        			   pred.left = curr.right;
        		   }
        		   else {
        			   pred.right = curr.right;
        		   }
        	   }
        	   else {
        		   c = curr.right;
        	   }
           }
           //case: curr has only left child to replace it
           else if(curr.left != null && curr.right == null) {
        	   if(pred != null) {
        		   if(pred.info>i) {
        			   pred.left = curr.left;
        		   }
        		   else {
        			   pred.right = curr.left;
        		   }
        	   }
        	   else {
        		   c = curr.left;
        	   }
           }
           //case: curr has 2 children, in-order predecessor replaces it
           if(curr.left != null && curr.right != null) {
        	   btNode succ = curr.left;
        	   btNode sPred = curr;
        	   //finding in-order successor
        	   while(succ.right != null) {
        		   sPred = succ;
        		   succ = succ.right;
        	   }
        	   //moving links
        	   if(sPred != curr && succ != null) {
        		   sPred.right = succ.left;
        		   succ.left = curr.left;
        	   }      	   
        	   succ.right = curr.right;
        	   //replacing
        	   if(pred != null && succ != null) {
        		   if(pred.info>i) {
        			   pred.left = succ;
        		   }
        		   else {
        			   pred.right = succ;
        		   }
        	   }
        	   else {
        		   c = succ;
        	   }
           }
           howmany--;
     }
     public boolean belongs(int i)
     {
           btNode p=c;
           while ((p!=null)&&(p.info!=i))
           {
                if (p.info>i) p=p.left;
                else p=p.right;
           }
           return (p!=null);
     }
     public int get_howmany() {return howmany;}
     
     public void print()
     {
           printtree(c);
           System.out.println();
     }
     public boolean equals(Intcoll6 obj)
     {
          int j = 0; boolean result = (howmany==obj.howmany);
          if (result)
          {
               int[] a = new int[howmany];
               int[] b = new int[howmany];
               toarray(c,a,0);
               toarray(obj.c,b,0);
               //comparing arrays element by element
               for(int elem:a) {
            	   if(elem != b[j]) {
            		   result = false;
            		   break;
            	   }
            	   j++;
               }
          }
          return result;
     }
     private static void printtree(btNode t)
     {
    	 if (t!=null)
         {
             printtree(t.left);
             System.out.print(t.info+" ");
             printtree(t.right);
         }
     }
      private static int toarray(btNode t, int[] a, int i)
      {
            int num_nodes=0;
            if (t!=null)
            {
                  num_nodes=toarray(t.left, a, i);
                  a[num_nodes+i]=t.info;
                  num_nodes=num_nodes+1+toarray(t.right, a, num_nodes+i+1);
            }
            return num_nodes;
     }
      private static class btNode
      {
            int info; btNode left; btNode right;
            private btNode(int s, btNode lt, btNode rt)
            {
                   info=s; left=lt; right=rt;
            }
            private btNode()
            {
                  info=0; left=null; right=null;
            }
      }
}