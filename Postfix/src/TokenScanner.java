import java.util.NoSuchElementException;

/**
 * A "scanner" for Tokens that doesn't need to have  
 * whitespace separated Tokens. There are no delimiters. 
 * Recognizes the operators from Token.OperatorList. 
 * These are all operators from Token including parentheses.
 * Recognizes simple non-negative real numbers with or 
 * without a decimal point. Must have digits on both sides
 * of the decimal point if there is one. Does not recognize
 * negative numbers. Will tokenize them as minus and a number.
 * Throws NoSuchElementException if Token is requested
 * but doesn't exist at current location in source.
 * There is no error recovery or peek.
 * 
 * @author Beth Katz
 * @version March 2008
 */

public class TokenScanner {
	/* Class invariant:
	 * source is a null-terminated String which will provide Tokens
     *      when they are requested
     * null is added to end of source as a sentinel 
	 * loc is the index into source where next Token may start
	 */
	
	private String source;
	private int loc;
	static final char NULL = '\0';
	static final String opList = Token.OperatorList;

	/**
	 * Constructs a TokenScanner from a String
	 */
	public TokenScanner(String s) {
		source = new String(s + NULL);
		loc = 0;
	}

	/**
	 * Whether or not there is a Token available.
	 * @return true if there is a Token available, false if not
	 */
	public boolean hasNextToken( ) {
		skipWhiteSpace( );
		if (source.charAt(loc) != NULL) {
		   char ch = source.charAt(loc);
		   return (Character.isDigit(ch) || isOperator(ch));
		}
		return false;
	}

	/**
	 * Provides the next Token from source.
	 * Throws NoSuchElementException if there is no Token.
	 * @return next Token
	 */
	public Token nextToken( ) {
		skipWhiteSpace( );
		if (source.charAt(loc) == NULL) {
			throw new NoSuchElementException("end of input");
		}
		char ch = source.charAt(loc);
		if (isOperator(ch)) {
			loc++;
			return new Token(source.substring(loc-1,loc)); // just one character
		}
		if (!Character.isDigit(ch)) {
			throw new NoSuchElementException("character not recognized: " + ch);
		}
		return getNumberToken( );		
	}
	
	/**
	 * Returns a Token that's a number.
	 * Should get here only if there is at least one digit.
	 * After this method, loc will be immediately after the number. 
	 * @return number Token
	 */
	private Token getNumberToken( ) {
		int start = loc;
		int stop;
		while (Character.isDigit(source.charAt(loc))) {
			loc++;
		}
		if (source.charAt(loc) == '.' && 
		      Character.isDigit(source.charAt(loc+1))) {  
			loc++; // move past decimal point
			while (Character.isDigit(source.charAt(loc))) {
				loc++;
			}
		}
		stop = loc;
		return new Token(source.substring(start,stop));
	}

	/**
	 * Return whether or not ch is a character in opList
	 * @param ch
	 *     character to be checked for being an operator
	 * @return true if ch is an operator, false if not
	 */
	private static boolean isOperator(char ch) {
		for (int i = 0; i < opList.length( ); i++) {
			if (opList.charAt(i) == ch) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Move loc past the white space in source.
	 */
	private void skipWhiteSpace( ) {
		while (Character.isWhitespace(source.charAt(loc))) {
			loc++;
		}
	}
	
	/**
	 * Returns whether or not entire source was tokenized.
	 * @return true if all of source has been tokenized and
	 * there is no more data, false if not
	 */
	public boolean reachedEnd( ) {
		return loc == source.length( )-1;
	}
}
