public enum Tokentype {
  LET,

  NUMBER,
  VARIABLE
}

/* https://github.com/arjunsk/codekrypt-compiler/blob/master/compiler-examples/ck-compiler/src/main/java/com/arjunsk/compiler/ck/domain/token/Token.java */

public class Token {

  private final Tokentype type;

  private final String value;

  public Token(TokenType type) {
    this.type = type;
    this.value = null;
  }

  public Token(TokenType type, String value) {
    this.type = type;
    this.value = value;
  }

  public TokenType getType() {
    return type;
  }

  public String getValue() {
    return value;
  }
}

