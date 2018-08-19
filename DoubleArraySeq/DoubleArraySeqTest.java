/*
 * Tests for DoubleArraySeq using JUnit 4
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class DoubleArraySeqTest {

	/*
	 * create DoubleArraySeq and check whether it's constructed properly
	 */
	@Test
	public void testStart( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		
		s.addBefore(23);
		s.addBefore(14);
		s.addBefore(-15.2);
		s.addBefore(17);
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("Four addBefores", "(17.0) -15.2 14.0 23.0 ", s.toString( ));
		
		assertEquals("Size is four", 4, s.size( ));
		assertEquals("Capacity is default of ten", 10, s.getCapacity());
		
		s.addAfter(9);
		s.addAfter(34);
		assertEquals("Two addAfters", "17.0 9.0 (34.0) -15.2 14.0 23.0 ", s.toString( ));
		
		s.advance( );
		assertEquals("Advance", "17.0 9.0 34.0 (-15.2) 14.0 23.0 ", s.toString( ));

		System.out.println("Inside loop, current should move from start to end");
		for(s.start( ); s.isCurrent( ); s.advance( )) {
			System.out.println("in loop: " + s.toString( ));		
		}
		
		assertTrue("No current after print loop", !s.isCurrent( ));
		
		s.start( );
		assertTrue("Current after start", s.isCurrent( ));
		assertEquals("Current value after start", 17, s.getCurrent( ), 0.0001);
		
		s.advance( );
		assertEquals("Current value after advance", 9, s.getCurrent( ), 0.0001);
	}
	
	/*
	 * try to break addBefore 
	 */
	@Test
	public void testAddBefore( ) {
		DoubleArraySeq s = new DoubleArraySeq(2);

		assertEquals("Size of new is zero", 0, s.size( ));
		assertEquals("Capacity was set to two", 2, s.getCapacity());
		assertTrue("No current without elements", !s.isCurrent( ));

		s.addBefore(345);
		assertEquals("Size after add is one", 1, s.size( ));
		assertEquals("Capacity didn't change", 2, s.getCapacity());
		assertEquals("Current value after adding 345", 345, s.getCurrent( ), 0.0001);

		s.addBefore(12);
		assertEquals("Size after add is two", 2, s.size( ));
		assertEquals("Capacity didn't change", 2, s.getCapacity());
		assertEquals("Current value after adding 12", 12, s.getCurrent( ), 0.0001);

		s.addBefore(789);
		assertEquals("Size after add is three", 3, s.size( ));
		assertTrue("Capacity should double", s.getCapacity() >= 4);
		assertEquals("Current value after adding 789", 789, s.getCurrent( ), 0.0001);
	
		System.out.println("s is: " + s.toString( ));
		assertEquals("Three addBefores", "(789.0) 12.0 345.0 ", s.toString( ));
		
		s.advance( );
		s.advance( );
		s.advance( );
		assertTrue("No current after advancing past end", !s.isCurrent( ));
		
		System.out.println("s has no current: " + s.toString( ));
		assertEquals("At end with no current", "789.0 12.0 345.0 ", s.toString( ));

		s.addBefore(17.35);
		assertTrue("Current at front", s.isCurrent( ));
		assertEquals("Current value after adding 17.35", 17.35, s.getCurrent( ), 0.0001);
		assertEquals("Size after add is four", 4, s.size( ));
		System.out.println("s is: " + s.toString( ));
		assertEquals("Added at front", "(17.35) 789.0 12.0 345.0 ", s.toString( ));		
	}


	/*
	 * try to break addAfter 
	 */
	@Test
	public void testAddAfter( ) {
		DoubleArraySeq s = new DoubleArraySeq( );

		assertEquals("Size of new is zero", 0, s.size( ));
		assertTrue("No current without elements", !s.isCurrent( ));

		s.addAfter(13.6);
		assertEquals("Size after add is one", 1, s.size( ));
			assertEquals("Current value after adding 13.6", 13.6, s.getCurrent( ), 0.0001);

		s.addAfter(97);
		assertEquals("Size after add is two", 2, s.size( ));
		assertEquals("Current value after adding 97", 97, s.getCurrent( ), 0.0001);

		s.addAfter(-225);
		assertEquals("Current value after adding -225", -225, s.getCurrent( ), 0.0001);
	
		System.out.println("s is: " + s.toString( ));
		assertEquals("Three addAfters", "13.6 97.0 (-225.0) ", s.toString( ));
		
		assertTrue("Current should exist", s.isCurrent( ));
		
		s.advance( );
		assertTrue("No current after advancing past end", !s.isCurrent( ));
		
		System.out.println("s has no current: " + s.toString( ));
		assertEquals("At end with no current", "13.6 97.0 -225.0 ", s.toString( ));

		s.addAfter(17.35);
		assertTrue("Current should exist after add", s.isCurrent( ));
		assertEquals("Current value after adding 17.35", 17.35, s.getCurrent( ), 0.0001);
		assertEquals("Size after add is four", 4, s.size( ));
		System.out.println("s is: " + s.toString( ));
		assertEquals("At last item should be current", "13.6 97.0 -225.0 (17.35) ", s.toString( ));
	}
	
	/*
	 * try to break concatenation 
	 */
	@Test
	public void testConcat( ) {
		DoubleArraySeq A = new DoubleArraySeq( );
		DoubleArraySeq B = new DoubleArraySeq( );
		DoubleArraySeq C;
		
		A.addAfter(13);
		A.addAfter(24);
		A.addAfter(95);
		A.addAfter(134);
		A.addAfter(158);
		
		B.addBefore(1024);
		B.addBefore(987);
		B.addBefore(919);
		B.addBefore(798);
		B.addBefore(733.45);
		B.addBefore(717);
		B.addBefore(700);
		B.addBefore(652);
		B.addBefore(512);
		B.addBefore(498);
		B.addBefore(436);
		B.addBefore(303);
		B.addBefore(300.001);
		B.addBefore(256);
		assertEquals("Current value after adding 256", 256, B.getCurrent( ), 0.0001);
		assertTrue("Capacity has doubled", B.getCapacity( ) >= 20);
		
		C = DoubleArraySeq.concatenation(A, B);
		assertTrue("No current after concatenation", !C.isCurrent( ));
		assertTrue("Size is added", C.size( ) == (A.size( ) + B.size( )));

		assertEquals("At end with no current", "13.0 24.0 95.0 134.0 158.0 256.0 300.001 303.0 436.0 498.0 512.0 652.0 700.0 717.0 733.45 798.0 919.0 987.0 1024.0 ", C.toString( ));
	}
	
	/*
	 * try to break trim 
	 */
	@Test
	public void testTrim( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		
		s.addBefore(23);
		s.addBefore(14);
		s.addBefore(-15.2);
		s.addBefore(17);
		
		assertEquals("Size is four", 4, s.size( ));
		assertEquals("Capacity is default of ten", 10, s.getCapacity());
		
		s.trimToSize( );
		assertEquals("Size is four", 4, s.size( ));
		assertEquals("Capacity should also be four", 4, s.getCapacity());

		assertEquals("After trim", "(17.0) -15.2 14.0 23.0 ", s.toString( ));
		assertEquals("Current value still 17", 17, s.getCurrent( ), 0.0001);
	}
	
	/*
	 * find bugs in not checking for current 
	 */
	@Test
	public void testNoCurrent( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		
		try {
			s.advance( );
			assertTrue("Should have thrown exception in advance", false);
		} catch (IllegalStateException e) {
			
		}
		
		try {
			s.addBefore(42);
			s.advance( );
			s.getCurrent( );
			assertTrue("Should have thrown exception in getCurrent", false);
		} catch (IllegalStateException e) {
			
		}
				
		try {
			s.removeCurrent( );
			assertTrue("Should have thrown exception in removeCurrent", false);
		} catch (IllegalStateException e) {
			
		}
	
	}

	/*
	 * Looks for bug in not having a currentItem
	 */
	@Test(expected=IllegalStateException.class)
	public void noCurrentAdvance1( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		s.advance( );
		assertTrue("Should have thrown exception in advance", false);
	}

	/*
	 * Looks for bug in not having a currentItem
	 */
	@Test(expected=IllegalStateException.class)
	public void noCurrentAdvance2( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		s.addBefore(42);
		s.advance( );
		s.getCurrent( );
		assertTrue("Should have thrown exception in getCurrent", false);
	}

	/*
	 * Looks for bug in not having a currentItem
	 */
	@Test(expected=IllegalStateException.class)
	public void noCurrentAdvance3( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		s.addBefore(42);
		s.advance( );
		s.removeCurrent( );
		assertTrue("Should have thrown exception in removeCurrent", false);
	}

	/*
	 * Looks for bug in not having a currentItem after remove
	 */
	@Test(expected=IllegalStateException.class)
	public void noCurrentAfterRemove( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		s.addBefore(42);
		s.addAfter(17);
		s.removeCurrent( );
		assertTrue("Should be no current after remove at end", !s.isCurrent( ));
		s.removeCurrent( );		
		assertTrue("Should have thrown exception in removeCurrent", false);
	}
	
	/*
	 * try to break remove 
	 */
	@Test
	public void testRemove( ) {
		DoubleArraySeq s = new DoubleArraySeq( );

		s.addBefore(42);
		assertEquals("Size is one after addBefore", 1, s.size( ));
		assertTrue("Item exists", s.isCurrent( ));
		s.removeCurrent( );
		assertEquals("Size is zero", 0, s.size( ));
		assertTrue("Item no longer exists", !s.isCurrent( ));
		
		s.addAfter(17);
		assertEquals("Size is one after addAfter", 1, s.size( ));
		assertTrue("Item exists", s.isCurrent( ));
		s.removeCurrent( );
		assertEquals("Size is zero", 0, s.size( ));
		assertTrue("Item no longer exists", !s.isCurrent( ));
		
		s.addAfter(23);
		s.addAfter(79.56);
		s.addAfter(893.23);
		s.addAfter(1024);
		s.addAfter(4096);
		s.addAfter(24356);
		s.addAfter(34251);
		s.addAfter(42516);

		s.start( );
		assertEquals("Size is eight after several adds", 8, s.size( ));
		s.removeCurrent( );
		assertEquals("New current after remove", 79.56, s.getCurrent( ), 0.0001);
		s.advance( );
		s.advance( );
		assertEquals("After advancing", 1024, s.getCurrent( ), 0.0001);
		s.removeCurrent( );
		assertEquals("After removing in middle", 4096, s.getCurrent( ), 0.0001);
		s.advance( );
		s.advance( );
		s.advance( );
		s.removeCurrent( );
		assertTrue("After removing last", !s.isCurrent( ));
		assertEquals("Size is five after several removes", 5, s.size( ));
	}

	/*
	 * try to break clone and equals  
	 */
	@Test
	public void testCloneEquals( ) {
		DoubleArraySeq A = new DoubleArraySeq( );
		DoubleArraySeq B = new DoubleArraySeq( );
		DoubleArraySeq C;
		
		A.addAfter(13);
		A.addAfter(24);
		A.addAfter(95);
		A.addAfter(134);
		A.addAfter(158);
		A.start( );

		B.addBefore(158);
		B.addBefore(134);
		B.addBefore(95);
		B.addBefore(24);
		B.addBefore(13);
	
		assertTrue("A and B are not same object", !A.equals(B));
		assertTrue("B and A are not same object", !B.equals(A));
		assertTrue("A is equivalent to itself", A.equals(A));
		
		System.out.println("A is: " + A.toString( ));
		assertEquals("A's data", "(13.0) 24.0 95.0 134.0 158.0 ", A.toString( ));
		assertEquals("B's data", "(13.0) 24.0 95.0 134.0 158.0 ", B.toString( ));
		
		C = A.clone( );
		assertTrue("A and C are not same object", !A.equals(C));
		assertTrue("A and C are similar", A.isCurrent( ) == C.isCurrent());
		assertTrue("A and C are same size", A.size( ) == C.size( ));
		assertTrue("A and C are same capacity", A.getCapacity( ) == C.getCapacity( ));
		assertTrue("A and C have same current", A.getCurrent( ) == C.getCurrent( ));		
		assertEquals("C's data", "(13.0) 24.0 95.0 134.0 158.0 ", C.toString( ));
		
		A.addAfter(17.25);
		assertEquals("C's data shouldn't change", "(13.0) 24.0 95.0 134.0 158.0 ", C.toString( ));
		assertEquals("A's data should change", "13.0 (17.25) 24.0 95.0 134.0 158.0 ", A.toString( ));
	}

	/*
	 * try to break addAll 
	 */
	@Test	
	public void testAddAll( ) {
		DoubleArraySeq s = new DoubleArraySeq( );
		DoubleArraySeq t = new DoubleArraySeq( );
		DoubleArraySeq u = new DoubleArraySeq( );
		
		s.addAfter(32.32);
		s.addAfter(7.77);
		s.addAfter(513.315);
		
		t.addBefore(-12);
		t.addBefore(-75);
		t.addBefore(-11.411);
		
		assertEquals("Insert in s", "32.32 7.77 (513.315) ", s.toString( ));		
		assertEquals("Insert in t", "(-11.411) -75.0 -12.0 ", t.toString( ));
		
		s.addAll(t);
		System.out.println("s with t added is: " + s.toString( ));		
		assertEquals("After addAll", "32.32 7.77 (513.315) -11.411 -75.0 -12.0 ", s.toString( ));
		
		s.advance( );
		s.advance( );
		s.advance( );
		s.addAfter(17.17);
		assertEquals("After adding 17.17 to end", "32.32 7.77 513.315 -11.411 -75.0 -12.0 (17.17) ", s.toString( ));

		// t's last item should have been separate and not affected by
		// the addAfter in s above
		t.advance( );   
		t.advance( );
		assertEquals("t not affected by add to s", "-11.411 -75.0 (-12.0) ", t.toString( ));

		assertEquals("u is empty", 0, u.size( ));				
		u.addAll(t);
		assertEquals("After adding to empty", "-11.411 -75.0 -12.0 ", u.toString( ));
		assertTrue("u should have no current item", !u.isCurrent( ));
		u.addAll(t);
		assertTrue("u should still have no current item", !u.isCurrent( ));
		assertEquals("a lot of items in u", "-11.411 -75.0 -12.0 -11.411 -75.0 -12.0 ", u.toString( ));		
	}
}
