public class SortedList {
	// Class Invariant:
	//1.Variable head will always refer to the smallest value in the list
	//2.The list will be sorted in increasing order

	private DoubleNode head;
	private int listLength;
	/**
	 * Constructor SortedList: explicitly defines starting head and listLength
	 * params:none
	 * returns:none
	 * precon:none
	 * postcon:head is null and size is 0
	 */
	public SortedList() {
		head=null;
		listLength=0;
	}
	/**
	 * Method insert: insert values at proper places in list
	 * params:value
	 * returns:none
	 * precon:none
	 * postcon:give value will be inserted at proper position in list and listLength increments
	 * throws:none
	 */
	public void insert(double value) {
		if(listLength==0){head=new DoubleNode(value,null);}
		//where new value is smallest
		else if(value<head.getData()){
			head=new DoubleNode(value,head);
		}
		//all other cases
		else{
			DoubleNode pre=getPrecedingNode(value);
			DoubleNode n=new DoubleNode(value,pre.getLink());
			pre.setLink(n);
		}
		listLength++;
	}
	/**
	 * Method getPrecedingNode
	 * params:value
	 * returns: DoubleNode c
	 * precon:none
	 * postcon:the node, after which the new value can be placed, is returned
	 * throws:none
	 */
	private DoubleNode getPrecedingNode(double value) {
		for(DoubleNode c=head;c!=null;c=c.getLink()){
			//returns last node if value is list's largest
			if(c.getLink()==null){return c;}
			//all other cases
			if(c.getLink().getData()>value){return c;}
		}
		return null;
	}
	/**
	 * Method find: looks for values in list
	 * params:value
	 * returns:-1 if value not in list, or position in list
	 * precon:none
	 * postcon:none
	 * throws:none
	 */
	public int find(double value) {
		if(listLength==0){return -1;}
		int pos=1;
		//iterates through list until list ends or value is found
		for(DoubleNode c=head;c!=null;c=c.getLink()){
			if(c.getData()==value){return pos;}
			pos++;
		}
		return -1;
	}
	/**
	 * Method removeAt
	 * params:index (1 based)
	 * returns:true if something is removed
	 * precon:none
	 * postcon:listLength decrements and node at index is no longer in list
	 * throws:none
	 */
	public boolean removeAt(int index) {
		if(listLength<index||index<1){return false;}
		//if index 1, then resets head
		if(index==1){head.setLink(head.getLink());}
		DoubleNode c=head;
		//else method iterates c to targeted pos
		if(index>2){
			for(int i=1;i<index-1;i++)
				c=c.getLink();
		}
		//and removes node like so
		c.setLink(c.getLink().getLink());
		listLength--;
		return true;
	}
	/**
	 * Method size: returns list size
	 * params:none
	 * returns: listLength
	 * precon:none
	 * postcon:none
	 * throws:none
	 */
	public int size() {
		return listLength;
	}
	/**
	 * Method toString: formats list into string
	 * params:none
	 * returns:none
	 * precon:none
	 * postcon:none
	 */
	public String toString() {
		String answer = "[ ";
		DoubleNode current;
		for (current = head; current != null; current = current.getLink()) {
				answer += current.getData() + " ";
		}
		answer += "]";
		return answer;
	}
}
