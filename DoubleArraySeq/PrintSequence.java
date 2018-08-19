/*
A program exploring various ways to print a sequence without accessing the
underlying representation.
DoubleArraySeq.toString has access to the data.
The simple loop in print does it if you don't need to know current item.
But you need to clone the sequence before iterating through it.
The count in print is needed to find the current item.

Beth Katz - February 2007
*/

public class PrintSequence {

   public static void main(String[] args) {
         DoubleArraySeq s = new DoubleArraySeq( );
         DoubleArraySeq t = new DoubleArraySeq( );      
         
         s.addAfter(42);
         s.addAfter(17);
         s.addAfter(3);
         s.addAfter(5);
         s.addAfter(9);
         
         s.start( );
         s.advance( );
         s.advance( );
         
         display(s);
         
         t.addAfter(34);
         t.addAfter(42);
         t.advance( );
         display(t);
      }
  
      // displays seq before and after the call to print to show 
      // that it hasn't changed
      public static void display(DoubleArraySeq seq) {
          System.out.println("Before calling print: " + seq.toString( ));
          print(seq);
          System.out.println("After calling print: " + seq.toString( ));   	  
      }
      
      // print sOrig indicating which item is the current one
      // does *not* change sOrig
      public static void print(DoubleArraySeq sOrig) {
         DoubleArraySeq s = sOrig.clone( );
         int count, indexOfCurrent;
         
         for (count=0; s.isCurrent( ); s.advance( )) {
            count++;
         }

         indexOfCurrent = s.size( ) - count;

         if (count == 0) {
            System.out.println("No current value");
         }
         
         count = 0;
         for(s.start( ); s.isCurrent( ); s.advance( )) {
            if (count == indexOfCurrent) {
               System.out.println(s.getCurrent( ) + " <--- current");
            } else {
               System.out.println(s.getCurrent( ));            
            }
            count++;
         }      
      }

}
