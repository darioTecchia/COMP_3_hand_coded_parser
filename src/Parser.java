/*
	Program to implement Recursive Descent Parser in Java
	Author: Manav Sanghavi		Author Link: https://www.facebook.com/manav.sanghavi 
	www.pracspedia.com
	
	Grammar:

  G         -> Program EOF
	Program 	-> Stmt FProgram EOF
	FProgram 	-> SEMI Stmt FProgram
	FProgram 	-> eps
	Stmt 			-> IF Expr THEN Stmt ELSE Stmt
	Stmt 			-> ID ASSIGN Expr
	Stmt 			-> WHILE Expr DO Stmt
	Expr 			-> Term FExpr
	FExpr 		-> RELOP Term
	FExpr 		-> eps
	Term 			-> ID
	Term 			-> INT
	Term      -> FLOAT
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

		boolean isValid = G();

		if ((isValid) & (ptr+1 == tokensFlow.size())) {
			System.out.println("The input string is valid.");
		} else {
			System.out.println("The input string is invalid.");
		}
	}

	// G -> Program EOF
	static  boolean G() {
	  int fallback = ptr;
	  boolean result = false;

	  if(Program()) {
      if(getSymbol(ptr).sym == Token.EOF) {
        result = true;
      } else {
        ptr = fallback;
      }
    }

	  return result;
  }

	// Program 	-> Stmt FProgram EOF
	static boolean Program() {
		int fallback = ptr;
		boolean result = false;

		if(Stmt()) {
		  if(FProgram()) {
        if(getSymbol(ptr).sym == Token.EOF) {
          result = true;
        } else {
          ptr = fallback;
        }
      }
		}

		return result;
	}
	
	// FProgram -> SEMI Stmt FProgram
	// FProgram -> eps
	static boolean FProgram() {
		int fallback = ptr;
		boolean result = false;

		if(getSymbol(ptr).sym == Token.SEMI) {
			ptr++;
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

		if(getSymbol(ptr).sym == Token.IF) {
			ptr++;
			if(Expr()) {
				if(getSymbol(ptr).sym == Token.THEN) {
					ptr++;
					if(Stmt()) {
						if(getSymbol(ptr).sym == Token.ELSE) {
							ptr++;
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
		} else if(getSymbol(ptr).sym == Token.ID) {
			ptr++;
			if(getSymbol(ptr).sym == Token.ASSIGN) {
				ptr++;
				if(Expr()) {
					result = true;
				} else {
					ptr = fallback;
				}
			} else {
				ptr = fallback;
			}
		} else if(getSymbol(ptr).sym == Token.WHILE) {
			ptr++;
			if(Expr()) {
				if(getSymbol(ptr).sym == Token.DO) {
					ptr++;
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

		if(getSymbol(ptr).sym >= 7 && getSymbol(ptr).sym <= 13) {
			ptr++;
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

		if(getSymbol(ptr).sym == Token.ID
        || getSymbol(ptr).sym == Token.INT
        || getSymbol(ptr).sym == Token.FLOAT) {
			result = true;
			ptr++;
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
