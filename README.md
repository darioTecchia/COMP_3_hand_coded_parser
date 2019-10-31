# YASPL

Programming language definited for the Compiler course from University.

### Project Part 2
JFlex Coded Lexer

## Regular Definition

|Regular Definition | Regular Expression|
|-------------------|-------------------|
|LineTerminator | `\r\|\n\|\r\n` |
|InputCharacter | `[^\r\n]` |
|WhiteSpace | `{LineTerminator} \| [ \t\f]` |
|||
|Comment | `{TraditionalComment} \| {EndOfLineComment} \| {DocumentationComment}` |
|TraditionalComment | `"/*" [^*] ~"*/" \| "/*" "*"+ "/"` |
|EndOfLineComment | `"//" {InputCharacter}* {LineTerminator}?` |
|DocumentationComment | `"/**" {CommentContent} "*"+ "/"` |
|CommentContent | `( [^*] \| \*+ [^/*] )*` |
|||
|Identifier | `[:jletter:] [:jletterdigit:]*` |
|||
|IntegerLiteral | `0 \| [1-9][0-9]*` |
|||
|FloatLiteral | `(0 \| [1-9][0-9]*)\.[0-9]+` |

## Lexical Specification

|Token |Lexeme or Regular Definition|Attribute|
|------|------|---------|
|**Delimiters**|
|*-*|WhiteSpace|-|
||||
|**Separators**|
|LPAR|(|-|
|RPAR|)|-|
|LBRA|{|-|
|RBRA|}|-|
|COMMA|,|-|
|SEMI|;|-|
||||
|**Keywords**|
|IF|if|-|
|THEN|then|-|
|ELSE|else|-|
|WHILE|while|-|
||||
|**Identifiers**|
|ID|Identifier|Pointer to table entry|
||||
|**Numbers**|
|INT|IntegerLiteral|Pointer to table entry|
|FLOAT|FloatLiteral|Pointer to table entry|
||||
|**Relops**|
|<|<|LT|
|<=|<=|LE|
|=|=|EQ|
|<>|<>|NE|
|>|>|GT|
|>=|>=|GE|
|<--|<--|ASSIGN|

## Grammar Specification

Data la seguente grammatica

Dove i tokens sono per lo più quelli definiti nella esercitazione 1 precedente.

G = ( \
&ensp; N = {Program, Stmt, Expr, Term}, \
&ensp; T = {ID, IF, THEN, ELSE, RELOP, NUMBER, ;, ASSIGN, WHILE, DO}, \
&ensp; S = Program \
&ensp; P =

```
  Program -> Program ; Stmt
  Program -> Stmt
  Stmt -> IF Expr THEN Stmt ELSE Stmt
  Stmt -> ID ASSIGN Expr
  Stmt -> WHILE Expr DO Stmt
  Expr -> Term RELOP Term
  Expr -> Term
  Term -> ID
  Term -> NUMBER
```
)

La grammatica iniziale non è adatta al top down parsing, rimuovo la ricorsione:

```
  Program -> Stmt FProgram
  FProgram -> ; Stmt FProgram
  FProgram -> eps
  Stmt -> IF Expr THEN Stmt ELSE Stmt
  Stmt -> ID ASSIGN Expr
  Stmt -> WHILE Expr DO Stmt
  Expr -> Term RELOP Term
  Expr -> Term
  Term -> ID
  Term -> NUMBER
```

Rimuovo la fattorizzazione:

```
  Program -> Stmt FProgram
  FProgram -> ; Stmt FProgram
  FProgram -> eps
  Stmt -> IF Expr THEN Stmt ELSE Stmt
  Stmt -> ID ASSIGN Expr
  Stmt -> WHILE Expr DO Stmt
  Expr -> Term FExpr
  FExpr -> relop Term
  FExpr -> eps
  Term -> ID
  Term -> NUMBER
```

Riassumo i caratteri:
```
  P -> S Pi
  Pi -> ; S Pi
  Pi -> eps
  S -> if E then S else S
  S -> ID assign E
  S -> while E do S
  E -> T Ei
  Ei -> relop T
  Ei -> eps
  T -> id
  T -> number
```

## Notes
The requested *sym* class is named Token
