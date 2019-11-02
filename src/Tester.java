import java.io.IOException;

import java_cup.runtime.Symbol;

public class Tester {

	public static void main(String[] args) throws IOException {
		
		Lexer lexicalAnalyzer = new Lexer();
		String filePath = args[0];

		if (lexicalAnalyzer.initialize(filePath)) {
			Symbol token;
			try {
				while ((token = lexicalAnalyzer.next_token()) != null) {
					if(token.sym == Token.EOF) {
						break;
					}
					String toRet = "<" +
							Token.TOKENS[token.sym] +
							(token.value == null ? ">" : (", "+token.value+">"));
					System.out.println(toRet);
				}
			} catch (Exception e) {
				System.out.println("File parsing ended!!");
			}

		} else {
			System.out.println("File not found!!");
		}
	}

}
