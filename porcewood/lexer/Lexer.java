package porcewood.lexer;

import porcewood.token.Token;
import porcewood.token.TokenType;
import java.util.Arrays;


// reference: https://github.com/arjunsk/codekrypt-compiler/blob/master/compiler-examples/ck-compiler/src/main/java/com/arjunsk/compiler/ck/lexer/Lexer.java 


public class Lexer {

  private final String code;
  private final int lenght;

  private int index;

  private Token currentToken;
  private Token prevToken;

  public Lexer(String code) {
    this.code = code;
    this.index = 0;
    this.lenght = code.length();
  }

  public boolean iterateToken() {

    char charAtInd = code.charAt(index);

    prevToken = currentToken;

    while(!isEOC()) {

      if (charAtInd == ' ' || charAtInd == '\t') {
        skipwhite();
        continue;
      } else if (code.substring(index, index + 3).equalsIgnoreCase("let")) {
        currentToken = new Token(TokenType.LET);
        index++;
      } else if (charAtInd == '='){
        currentToken = new Token(TokenType.EQ);
      } else if (Character.isDigit(charAtInd)) {
        currentToken = new Token(TokenType.INT, readInt());
      } else if (Character.isLetter(charAtInd)) {
        currentToken = new Token(TokenType.VAR, readVar());
      } else {
        System.out.println("Token not defined.");
      }
      return true;
    }
    return false;
  }

  private boolean isEOC() {
    return index >= lenght;
  }

  private String readInt() {
    StringBuilder sb = new StringBuilder();
    char charAtInd = code.charAt(index);
    while (!isEOC() && Character.isDigit(charAtInd)) {
      sb.append(charAtInd);
      index++;
      if (isEOC()) break;
      charAtInd = code.charAt(index);
    }
    return sb.toString();
  }

  private String readVar() {
    StringBuilder sb = new StringBuilder();
    char charAtInd = code.charAt(index);
    while (!isEOC() && Character.isLetter(charAtInd)) {
      sb.append(charAtInd);
      index++;
      if (isEOC()) break;
      charAtInd = code.charAt(index);
    }
    return sb.toString();
  }

  private void skipwhite() {
    
    char charAtInd = code.charAt(index);

    while(!isEOC()) {
      charAtInd = code.charAt(index);
      if(charAtInd == ' ' || charAtInd == '\t') {
        index++;
      } else {
        break;
      }
    }
  }
  
  public Token getprevToken() {
    return prevToken;
  }

  public Token getcurrentToken() {
    return currentToken;
  }

}
