public class Scratchpad {

	public static void main(String[] args) {
		System.out.println("==badInput==");
		String[ ] problem = {"3 5 a", "4 5 * 13.3.3   ", "12 5 %"};
			for (String item : problem) {
				String answer = PostfixEvaluator.evaluate(item);
				System.out.println(answer);
			}
	}
}
