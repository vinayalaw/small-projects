import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;
public class PostfixEvaluator {
	public static String evaluate(String in){
		//checks if in is blank
		if(in.trim().isEmpty()){return "No input";}
		TokenScanner s1=new TokenScanner(in);
		Stack<Double> nums=new Stack<Double>();
		while(s1.hasNextToken()){
			Token t;
				t=s1.nextToken();		
			//paren has no meaning
			if(t.isLeftParen() || t.isRightParen()){return "'(' or ')' has no meaning here";}
			else if(t.isNumber()){
				try{
					nums.push(t.numberValue());
				}
				catch(NoSuchElementException e){
					return "not all input used";
				}
			}
			//operator case
			else if(t.isOperator()){
				double first,second;
				try{
					second=nums.pop();
					first=nums.pop();
				}
				//not enough values to process
				catch(EmptyStackException e){
					return "Stack Underflow";
				}
				switch(t.operatorCharValue()){
				case('+'):nums.push(first+second);break;
				case('-'):nums.push(first-second);break;
				case('*'):nums.push(first*second);break;
				case('/'):nums.push(first/second);break;
				default:break;
				}
			}			
		}
		// nothing placed in stack
		if(nums.size()<1){return "No input";}
		//case where values remain on stack after TokenScanner reaches end
		if(s1.reachedEnd() && nums.size()>1){return "Computed answer, but values remain on stack";}
		//token scanner did not reach end and too many values on stack
		if(!(s1.reachedEnd()) && nums.size()>1){return "values remain on stack";}
		//tokenScanner has not reached end, then something was not used
		if(!(s1.reachedEnd())){return "not all input used";}
		//value remaining in stack is answer
		String ret=nums.pop().toString();
		return ret;
	}
}