/**
 * Token
 */
public class Token {

  /* Keywords*/
  public static final int IF = 0;
  public static final int THEN = 1;
  public static final int ELSE = 2;
  public static final int WHILE = 3;

  /* Numbers */
  public static final int INT = 4;
  public static final int FLOAT = 5;

  /* Operators */
  public static final int LT = 6;
  public static final int LE = 7;
  public static final int EQ = 8;
  public static final int NE = 9;
  public static final int GT = 10;
  public static final int GE = 11;
  public static final int ASSIGN = 12;
  
  /* Separators */
  public static final int LPAR = 13;
  public static final int RPAR = 14;
  public static final int LBRA = 15;
  public static final int RBRA = 16;
  public static final int COMMA = 17;
  public static final int SEMI = 18;

  /* Identifier */
  public static final int ID = 19;

  public static final int EOF = -1;

  public static final String[] TOKENS = {
    "IF",
    "THEN",
    "ELSE",
    "WHILE",
    "INT",
    "FLOAT",
    "LT",
    "LE",
    "EQ",
    "NE",
    "GT",
    "GE",
    "ASSIGN",
    "LPAR",
    "RPAR",
    "LBRA",
    "RBRA",
    "COMMA",
    "SEMI",
    "ID"
  };

}