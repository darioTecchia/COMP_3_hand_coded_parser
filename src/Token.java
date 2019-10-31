/**
 * Token
 */
public class Token {

  /* Keywords*/
  public static final int IF = 0;
  public static final int THEN = 1;
  public static final int ELSE = 2;
  public static final int WHILE = 3;
  public static final int DO = 4;

  /* Numbers */
  public static final int INT = 5;
  public static final int FLOAT = 6;

  /* Operators */
  public static final int LT = 7;
  public static final int LE = 8;
  public static final int EQ = 9;
  public static final int NE = 10;
  public static final int GT = 11;
  public static final int GE = 12;
  public static final int ASSIGN = 13;
  
  /* Separators */
  public static final int LPAR = 14;
  public static final int RPAR = 15;
  public static final int LBRA = 16;
  public static final int RBRA = 17;
  public static final int COMMA = 18;
  public static final int SEMI = 19;

  /* Identifier */
  public static final int ID = 20;

  public static final int EOF = -1;

  public static final String[] TOKENS = {
    "IF",
    "THEN",
    "ELSE",
    "WHILE",
    "DO",
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