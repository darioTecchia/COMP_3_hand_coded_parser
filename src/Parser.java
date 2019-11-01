/*
	Program to implement Recursive Descent Parser in Java
	Author: Manav Sanghavi		Author Link: https://www.facebook.com/manav.sanghavi 
	www.pracspedia.com
	
	Grammar:

	Program 	-> Stmt FProgram
	FProgram 	-> SEMI Stmt FProgram
	FProgram 	-> eps
	Stmt 			-> IF Expr THEN Stmt ELSE Stmt
	Stmt 			-> ID ASSIGN Expr
	Stmt 			-> WHILE Expr DO Stmt
	Expr 			-> Term FExpr
	FExpr 		-> RELOP Term
	FExpr 		-> eps
	Term 			-> ID
	Term 			-> NUMBER
*/

import java.io.IOException;
import java.util.*;

import java_cup.runtime.Symbol;

class Parser {
	static int ptr;

	static Lexer lexicalAnalyzer = new Lexer();

	static ArrayList<Symbol> tokensFlow = new ArrayList<>();

	public static void main(String args[]) {

		String filePath = args[0];
		lexicalAnalyzer.initialize(filePath);

		ptr = 0;

		boolean isValid = Program();

		if ((isValid) & (ptr == tokensFlow.size())) {
			System.out.println("The input string is valid.");
		} else {
			System.out.println("The input string is invalid.");
		}
	}

	// Program 	-> Stmt FProgram
	static boolean Program() {
		int fallback = ptr;
		boolean result = false;

		if(Stmt()) {
			if(FProgram()) {
				result = true;
			} else {
				ptr = fallback;
			}
		}
		return result;
	}
	
	// FProgram -> SEMI Stmt FProgram
	// FProgram -> eps
	static boolean FProgram() {
		int fallback = ptr;
		boolean result = false;

		if(getSymbol(ptr++).sym == Token.SEMI) {
			if(Stmt()) {
				if(FProgram()) {
					result = true;
				} else {
					ptr = fallback;
				}
			} else {
				ptr = fallback;
			}
		} else {
			result = true;
		}
		return result;
	}

	// Stmt -> IF Expr THEN Stmt ELSE Stmt
	// Stmt -> ID ASSIGN Expr
	// Stmt -> WHILE Expr DO Stmt
	static boolean Stmt() {
		int fallback = ptr;
		boolean result = false;

		if(getSymbol(ptr++).sym == Token.IF) {
			if(Expr()) {
				if(getSymbol(ptr++).sym == Token.THEN) {
					if(Stmt()) {
						if(getSymbol(ptr++).sym == Token.ELSE) {
							if(Stmt()) {
								result = true;
							}
						} else {
							ptr = fallback;
						}
					} else {
						ptr = fallback;
					}
				} else {
					ptr = fallback;
				}
			} else {
				ptr = fallback;
			}
		} else if(getSymbol(ptr++).sym == Token.ID) {
			if(getSymbol(ptr++).sym == Token.ASSIGN) {
				if(Expr()) {
					result = true;
				} else {
					ptr = fallback;
				}
			} else {
				ptr = fallback;
			}
		} else if(getSymbol(ptr++).sym == Token.WHILE) {
			if(Expr()) {
				if(getSymbol(ptr++).sym == Token.DO) {
					if(Stmt()) {
						result = true;
					}
				} else {
					ptr = fallback;
				}
			} else {
				ptr = fallback;
			}
		}
		return result;
	}

	// Expr -> Term FExpr
	static boolean Expr() {
		int fallback = ptr;
		boolean result = false;

		if(Term()) {
			if(FExpr()) {
				result = true;
			} else {
				ptr = fallback;
			}
		}

		return result;
	}

	// FExpr -> RELOP Term
	// FExpr -> eps
	static boolean FExpr() {
		int fallback = ptr;
		boolean result = false;

		if(getSymbol(ptr++).sym >= 7 || getSymbol(ptr).sym <= 13) {
			if(Term()) {
				result = true;
			} else {
				ptr = fallback;
			}
		} else {
			result = true;
		}
		return result;
	}

	// Term -> ID
	// Term -> NUMBER
	static boolean Term() {
		int fallback = ptr;
		boolean result = false;

		if(getSymbol(ptr++).sym == Token.ID) {
			result = true;
		} else if(getSymbol(ptr++).sym == Token.INT || getSymbol(ptr).sym == Token.FLOAT) {
			result = true;
		}

		return result;
	}

	static Symbol getSymbol(int index) {
		Symbol s;
		try {
			s = tokensFlow.get(index);
		} catch (IndexOutOfBoundsException ioobe) {
			try {
				tokensFlow.add(lexicalAnalyzer.next_token());
			} catch (IOException ioe) {}
		} finally {
			s = tokensFlow.get(index);
			return s;
		}
	}
}
