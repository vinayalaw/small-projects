import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class SortedListTest {
	//checks that head is not set with removeAt and that size is 0
	@Test
	public void testConstructor() {
		SortedList s=new SortedList();
		assertTrue("", s.size()==0);
		assertEquals("",false,s.removeAt(1));
	}
	//checks insert where value must be placed at beginning
	@Test
	public void testInsertbefore(){
		SortedList s=new SortedList();
		s.insert(0);
		s.insert(-1);
		assertEquals("",1,s.find(-1));
	}
	//checks insert where value must be placed in middle
	@Test
	public void testInsertMiddle(){
		SortedList s=new SortedList();
		s.insert(0);
		s.insert(2);
		s.insert(1);
		assertEquals("", 2, s.find(1));
	}
	//checks insert where value must be placed at end
	@Test
	public void testInsertEnd(){
		SortedList s=new SortedList();
		s.insert(0);
		s.insert(1);
		assertEquals("", 2, s.find(1));
	}
	/*
	 * checks that:
	 * value not in list should not be found
	 * return value for position is correct
	 */
	@Test
	public void testFind(){
		SortedList s=new SortedList();
		s.insert(48);
		assertTrue("Value not in list should not be found", -1==s.find(9001));
		s.insert(28);
		s.insert(37);
		s.insert(32);
		assertEquals("Value pos does not match", 2,s.find(32));
	}
	/*
	 * checks that:
	 * negative values return false
	 * parameter for index value greater than size returns false
	 * method removes correct value
	 */
	@Test
	public void testRemoveAt(){
		SortedList s=new SortedList();
		s.insert(3);
		s.insert(16);
		s.insert(290);
		assertEquals("Nothing should be removed",false,s.removeAt(-1));	
		assertEquals("Nothing should be removed",false,s.removeAt(16));
		s.removeAt(2);
		assertEquals("Wrong Value removed",-1,s.find(16));
	}
	//checks that size returned is correct with nothing and somethings in list
	@Test
	public void testSize(){
		SortedList s=new SortedList();
		assertEquals("Nothing inserted yet",0,s.size());
		s.insert(1);
		s.insert(1);
		assertEquals("Size incorrect",2,s.size());
	}
}
