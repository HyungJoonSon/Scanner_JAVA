public class Token {

    private static final int KEYWORDS = TokenType.Eof.ordinal();

    private static final String[] reserved = new String[KEYWORDS];
    private static Token[] token = new Token[KEYWORDS];

    public static final Token eofTok = new Token(TokenType.Eof, "0");
    public static final Token constTok = new Token(TokenType.Const, "0");
    public static final Token returnTok = new Token(TokenType.Return, "0");
    public static final Token voidTok = new Token(TokenType.Void, "0");
    public static final Token elseTok = new Token(TokenType.Else, "0");
    public static final Token ifTok = new Token(TokenType.If, "0");
    public static final Token intTok = new Token(TokenType.Int, "0");
    public static final Token whileTok = new Token(TokenType.While, "0");
    public static final Token leftBraceTok = new Token(TokenType.LeftBrace, "0");
    public static final Token rightBraceTok = new Token(TokenType.RightBrace, "0");
    public static final Token leftBracketTok = new Token(TokenType.LeftBracket, "0");
    public static final Token rightBracketTok = new Token(TokenType.RightBracket, "0");
    public static final Token leftParenTok = new Token(TokenType.LeftParen, "0");
    public static final Token rightParenTok = new Token(TokenType.RightParen, "0");
    public static final Token semicolonTok = new Token(TokenType.Semicolon, "0");
    public static final Token commaTok = new Token(TokenType.Comma, "0");
    public static final Token assignTok = new Token(TokenType.Assign, "0");
    public static final Token eqeqTok = new Token(TokenType.Equals, "0");
    public static final Token ltTok = new Token(TokenType.Less, "0");
    public static final Token lteqTok = new Token(TokenType.LessEqual, "0");
    public static final Token gtTok = new Token(TokenType.Greater, "0");
    public static final Token gteqTok = new Token(TokenType.GreaterEqual, "0");
    public static final Token notTok = new Token(TokenType.Not, "0");
    public static final Token noteqTok = new Token(TokenType.NotEqual, "0");
    public static final Token plusTok = new Token(TokenType.Plus, "0");
    public static final Token minusTok = new Token(TokenType.Minus, "0");
    public static final Token multiplyTok = new Token(TokenType.Multiply, "0");
    public static final Token divideTok = new Token(TokenType.Divide, "0");
    public static final Token reminderTok = new Token(TokenType.Reminder, "0");
    public static final Token addAssignTok = new Token(TokenType.AddAssign, "0");
    public static final Token subAssignTok = new Token(TokenType.SubAssign, "0");
    public static final Token multAssignTok = new Token(TokenType.MultAssign, "0");
    public static final Token divAssignTok = new Token(TokenType.DivAssign, "0");
    public static final Token remAssignTok = new Token(TokenType.RemAssign, "0");
    public static final Token incrementTok = new Token(TokenType.Increment, "0");
    public static final Token decrementTok = new Token(TokenType.Decrement, "0");
    public static final Token andTok = new Token(TokenType.And, "0");
    public static final Token orTok = new Token(TokenType.Or, "0");

    // 추가
    public static final Token charTok = new Token(TokenType.Char, "0");
    public static final Token doubleTok = new Token(TokenType.Double, "0");
    public static final Token forTok = new Token(TokenType.For, "0");
    public static final Token doTok = new Token(TokenType.Do, "0");
    public static final Token gotoTok = new Token(TokenType.Goto, "0");
    public static final Token switchTok = new Token(TokenType.Switch, "0");
    public static final Token caseTok = new Token(TokenType.Case, "0");
    public static final Token breakTok = new Token(TokenType.Break, "0");
    public static final Token DefaultTok = new Token(TokenType.Default, "0");
    public static final Token colonTok = new Token(TokenType.Colon, "0");

    private TokenType type;
    private String value = "";
    private int lineNum = 0;
    private int columnNum = 0;

    private Token(TokenType t, String v) {
        type = t;
        value = v;
        if (t.compareTo(TokenType.Eof) < 0) {
            int ti = t.ordinal();
            reserved[ti] = t.getTokenName();
            token[ti] = this;
        }
    }

    private Token(TokenType t, String v, int lineNum, int columnNum) {
        type = t;
        value = v;
        this.lineNum = lineNum;
        this.columnNum = columnNum;
    }

    public TokenType type() {
        return type;
    }

    public String value() {
        return value;
    }

    public static Token keyword(String name, int lineNum, int columnNum) {
        char ch = name.charAt(0);
        if (ch >= 'A' && ch <= 'Z')
            return mkIdentTok(name, lineNum, columnNum);
        for (int i = 0; i < KEYWORDS; i++)
            if (name.equals(reserved[i]))
                return mkDefaultToken(token[i], lineNum, columnNum);
        return mkIdentTok(name, lineNum, columnNum);
    } // keyword

    public static Token mkIdentTok(String name, int lineNum, int columnNum) {
        return new Token(TokenType.Identifier, name, lineNum, columnNum);
    }

    public static Token mkIntLiteral(String name, int lineNum, int columnNum) {
        return new Token(TokenType.IntLiteral, name, lineNum, columnNum);
    }

    public static Token mkCharLiteral(String name, int lineNum, int columnNum) {
        return new Token(TokenType.CharLiteral, name, lineNum, columnNum);
    }

    public static Token mkStringLiteral(String name, int lineNum, int columnNum) {
        return new Token(TokenType.StringLiteral, name, lineNum, columnNum);
    }

    public static Token mkDoubleLiteral(String name, int lineNum, int columnNum) {
        return new Token(TokenType.DoubleLiteral, name, lineNum, columnNum);
    }

    public static Token mkDefaultToken(Token token, int lineNum, int columnNum) {
        return new Token(token.type, token.value, lineNum, columnNum);
    }

    public String toString(String fileName) {
        return "Token -----> \t" + type.getTokenName()
                + " ( " + type.getTokenNumber() + ", "
                + (value.equals("0") ? " " : value)
                + ", " + fileName
                + ", " + lineNum
                + ", " + (columnNum - value.length() + 1)
                + " )";
    } // toString

    public static void main(String[] args) {
        // test
        System.out.println(eofTok);
        System.out.println(whileTok);
    }
} // Token
