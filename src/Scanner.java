import java.io.*;

public class Scanner {

    // Error 검사
    private boolean isError = false;
    private boolean isEof = false;
    private char ch = ' ';
    private BufferedReader input;
    private String line = "";
    private int lineno = 0;
    private int col = 1;
    private final String letters = "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String digits = "0123456789";
    private final char eolnCh = '\n';
    private final char eofCh = '\004';

    public Scanner(String fileName) { // source filename
        System.out.println("Begin scanning... programs/" + fileName + "\n");
        try {
            input = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            System.exit(1);
        }
    }

    private char nextChar() { // Return next char
        if (ch == eofCh) {
            error("Attempt to read past end of file");
            System.exit(1);
        }

        col++;
        if (col >= line.length()) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                System.err.println(e);
                System.exit(1);
            } // try
            if (line == null) // at end of file
                line = "" + eofCh;
            else {
                // System.out.println(lineno + ":\t" + line);
                lineno++;
                line += eolnCh;
            } // if line
            col = 0;
        } // if col
        return line.charAt(col);
    }

    public Token next() { // Return next token
        do {
            if (isLetter(ch) || ch == '_') { // ident or keyword
                String spelling = concat(letters + digits + '_');
                return Token.keyword(spelling, lineno, col);
                // 추가구현 - Short Form 인식 - .123 형태

            } else if (ch == '.') {
                String number = concat(digits);
                return Token.mkDoubleLiteral(number, lineno, col);

                // int literal
                // 추가구현 - Double literal, Short Form 인식 - 123. 형태
            } else if (isDigit(ch)) {
                String number = concat(digits);
                if (ch != '.') {
                    return Token.mkIntLiteral(number, lineno, col);
                }
                number += concat(digits);
                return Token.mkDoubleLiteral(number, lineno, col);

            } else
                switch (ch) {
                    case ' ':
                    case '\t':
                    case '\r':
                    case eolnCh:
                        ch = nextChar();
                        break;

                    case '/': // divide or divAssign or comment
                        ch = nextChar();
                        if (ch == '=') { // divAssign
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.divAssignTok, lineno, col);
                        }

                        // divide
                        if (ch != '*' && ch != '/')
                            return Token.mkDefaultToken(Token.divideTok, lineno, col);

                        // multi line comment
                        if (ch == '*') {
                            do {
                                while (ch != '*')
                                    ch = nextChar();
                                ch = nextChar();
                            } while (ch != '/');
                            ch = nextChar();
                        }
                        // single line comment
                        else if (ch == '/') {
                            do {
                                ch = nextChar();
                            } while (ch != eolnCh);
                            ch = nextChar();
                        }

                        break;

                    // Char Literial Token Add - 추가 구현
                    case '\'': // char literal
                        char ch1 = nextChar();
                        nextChar(); // get '
                        ch = nextChar();
                        return Token.mkCharLiteral("" + ch1, lineno, col);

                    // String Literial Token Add - 추가 구현
                    case '\"':
                        String str = "";
                        ch = nextChar();
                        while (ch != '\"') { // 시작이 시퀸스이라면 시퀸스 처리 아니면 문자열 처리
                            if (ch == '\\') { // 탈출 시퀸스일 경우
                                str += ch;
                                ch = nextChar(); // '\' 먼저 넣어주고
                                str += ch;
                                ch = nextChar(); // 시퀸스를 넣어준다.
                            }
                            str += concatStr(); // 문자열 인식(탈출 시퀸스나 '"' 정지)
                        }
                        ch = nextChar();
                        return Token.mkStringLiteral(str, lineno, col);

                    // 마지막 토큰은 행, 열의 정보가 필요 없음
                    case eofCh:
                        return Token.eofTok;

                    // Add, AddAssign, Increment
                    case '+':
                        ch = nextChar();
                        if (ch == '=') { // addAssign
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.addAssignTok, lineno, col);
                        } else if (ch == '+') { // increment
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.incrementTok, lineno, col);
                        }
                        return Token.mkDefaultToken(Token.plusTok, lineno, col);

                    // Sub, SubAssign, Decrement
                    case '-':
                        ch = nextChar();
                        if (ch == '=') { // subAssign
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.subAssignTok, lineno, col);
                        } else if (ch == '-') { // decrement
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.decrementTok, lineno, col);
                        }
                        return Token.minusTok;
                    case '*':
                        ch = nextChar();
                        if (ch == '=') { // multAssign
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.multAssignTok, lineno, col);
                        }
                        return Token.multiplyTok;
                    case '%':
                        ch = nextChar();
                        if (ch == '=') { // remAssign
                            ch = nextChar();
                            return Token.mkDefaultToken(Token.remAssignTok, lineno, col);
                        }
                        return Token.mkDefaultToken(Token.reminderTok, lineno, col);
                    case '(':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.leftParenTok, lineno, col);
                    case ')':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.rightParenTok, lineno, col);
                    case '{':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.leftBraceTok, lineno, col);
                    case '}':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.rightBraceTok, lineno, col);

                    // Left Bracket Token Add - 추가 구현
                    case '[':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.leftBracketTok, lineno, col);
                    // Right Bracket Token Add - 추가 구현
                    case ']':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.rightBracketTok, lineno, col);

                    case ':':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.colonTok, lineno, col);
                    case ';':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.semicolonTok, lineno, col);

                    case ',':
                        ch = nextChar();
                        return Token.mkDefaultToken(Token.commaTok, lineno, col);

                    case '&':
                        check('&');
                        return Token.mkDefaultToken(Token.andTok, lineno, col);
                    case '|':
                        check('|');
                        return Token.mkDefaultToken(Token.orTok, lineno, col);
                    case '=':
                        return chkOpt('=', Token.mkDefaultToken(Token.assignTok, lineno, col),
                                Token.mkDefaultToken(Token.eqeqTok, lineno, col));

                    case '<':
                        return chkOpt('=', Token.mkDefaultToken(Token.ltTok, lineno, col),
                                Token.mkDefaultToken(Token.lteqTok, lineno, col));
                    case '>':
                        return chkOpt('=', Token.mkDefaultToken(Token.gtTok, lineno, col),
                                Token.mkDefaultToken(Token.gteqTok, lineno, col));
                    case '!':
                        return chkOpt('=', Token.mkDefaultToken(Token.notTok, lineno, col),
                                Token.mkDefaultToken(Token.noteqTok, lineno, col));

                    default:
                        error("Illegal character " + ch);
                } // switch
        } while (true);

    } // next

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private void check(char c) {
        ch = nextChar();
        if (ch != c)
            error("Illegal character, expecting " + c);
        ch = nextChar();
    }

    private Token chkOpt(char c, Token one, Token two) {
        ch = nextChar();
        if (ch != c)
            return one;
        ch = nextChar();
        return two;
    }

    private String concat(String set) {
        String r = "";
        do {
            r += ch;
            ch = nextChar();
        } while (set.indexOf(ch) >= 0);
        return r;
    }

    private String concatStr() {
        String r = "";
        do {
            r += ch;
            ch = nextChar();
        } while (ch != '\"' && ch != '\\');
        return r;
    }

    public void error(String msg) {
        isError = true;
        System.err.println("Error: Line - " + lineno + ", column - " + col + ", msg: " + msg);
    }

    public boolean getError() {
        boolean temp = isError;
        isError = false;

        return temp;
    }

    static public void main(String[] argv) {
        Scanner lexer = new Scanner(argv[0]);
        Token tok = lexer.next();
        while (tok != Token.eofTok) {
            if (!lexer.getError()) {
                System.out.println(tok.toString(argv[0]));
            }
            tok = lexer.next();
        }
    } // main
}
