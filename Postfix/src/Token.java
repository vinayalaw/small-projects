/**
 * A Token class in which tokens know whether they are 
 * operators or numbers and can return appropriate values.
 * If a token cannot be created from the string, an 
 * IllegalArgumentException is thrown.
 * The legal operators are + - *  / ( )
 * @author Beth Katz
 */
public class Token {

	/*
	 * Class invariant:
	 * - a token's type is either an operator, a number, or a parentheses
	 * - any attempt to construct a token that is not of a known type
	 *     will not allow one to to be created
	 * - a token's type is stored in the type instance variable
	 * - the token's value instance variable contains a Double for numbers
	 *   and a Character for operators
	 * - operators have precedence level of 0 for low (+ -) and 1 for high (/ *) 
	 * - non-operators have precedence 0 that is ignored 
	 */
	enum TokenType {operator, number, leftParen, rightParen};
	public static final String OperatorList = "+-/*()";
	private TokenType type;
	private Object value;
	private int precedence;

	/**
	 * Build a Token from a String. This can build number Tokens.
	 * @param s
	 *   String containing a potential Token.
	 *   Will throw IllegalArgumentException if Token cannot be built.
	 */
	public Token(String s) {
		precedence = 0;
		if (s.length( ) <= 0) {
			throw new IllegalArgumentException("Empty token");
		} else if (s.length( ) == 1 && OperatorList.contains(s) ) {
			type = TokenType.operator;
			value = (Character)(s.charAt(0));
			if (value.equals('*') || value.equals('/')) {
				precedence = 1;
			} else if (value.equals('(')) {
				type = TokenType.leftParen;
			} else if (value.equals(')')) {
				type = TokenType.rightParen;
			}
		} else if (Character.isDigit(s.charAt(0))) { // starts as number
			type = TokenType.number;
			try {
            value = Double.parseDouble(s); 
			}
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Cannot create number from " + s);
			}
		} else {
			throw new IllegalArgumentException("Cannot understand token: " + s);			
		}
	}
	
	/**
	 * Build a number Token from a Double.
	 * @param s
	 *   Double containing a Token.
	 */
	public Token(Double d) {
		type = TokenType.number;
		value = d;
		precedence = 0;
	}
	
	/**
	 * Whether or not the Token is a number.
	 * @return true if Token is a number, false if not
	 */
	public boolean isNumber( ) {
		return type == TokenType.number;
	}

	/**
	 * Whether or not the Token is an operator. 
	 * Parentheses are not operators.
	 * @return true if Token is a operator, false if not
	 */
	public boolean isOperator( ) {
		return type == TokenType.operator;
	}

	/**
	 * Whether or not the Token is a left parentheses.
	 * @return true if Token is a left parentheses, false if not
	 */
	public boolean isLeftParen( ) {
		return type == TokenType.leftParen;
	}

	/**
	 * Whether or not the Token is a right parentheses.
	 * @return true if Token is a right parentheses, false if not
	 */
	public boolean isRightParen( ) {
		return type == TokenType.rightParen;
	}

	/**
	 * Whether or not the Token has higher precedence than other Token
	 * @return true if Token has higher precedence, false if not
	 */
	public boolean hasHigherPrecedenceThan(Token other) {
		return precedence > other.precedence;
	}

	/**
	 * The numeric value of the Token if it is a number Token
	 * Throws IllegalArgumentException if Token is not a number
	 * @return Token's Double value
	 */
	public Double numberValue( ) {
		if (type == TokenType.number) {
			return (Double)value;
		} else {
			throw new IllegalArgumentException("Operators do not have numeric values");
		}
	}

	/**
	 * The character value of the Token if it is an operator Token
	 * Throws IllegalArgumentException if Token is not an operator
	 * @return Token's char value
	 */
	public Character operatorCharValue( ) {
		if (type == TokenType.number) {
			throw new IllegalArgumentException("Numbers do not have character values.");
		} else {
			return (Character)value;
		}
	}

	/**
	 * String representation of the Token
	 */
	public String toString( ) {
		if (type == TokenType.number) {
			return ((Double)value).toString( );
		} else {
			return ((Character)value).toString( );
		}
	}
}
