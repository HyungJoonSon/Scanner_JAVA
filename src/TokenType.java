
public enum TokenType {
    Const("const", 30), Else("else", 31), If("if", 32),
    Int("int", 33), Return("return", 34), Void("void", 35),
    While("while", 36), Char("char", 40), Double("double", 41),
    For("for", 43), Do("do", 44), Goto("goto", 45),
    Switch("switch", 46), Case("case", 47), Break("break", 48),
    Default("default", 49), Eof("<<EOF>>", 29),
    Not("!", 0), NotEqual("!=", 1), Reminder("%", 2),
    RemAssign("%=", 3), Identifier("%ident", 4), IntLiteral("%i_lit", 5),
    And("%%", 6), LeftParen("(", 7), RightParen(")", 8),
    Multiply("*", 9), MultAssign("*=", 10), Plus("+", 11),
    Increment("++", 12), AddAssign("+=", 13), Comma(",", 14),
    Minus("-", 15), Decrement("--", 16), SubAssign("-=", 17),
    Divide("/", 18), DivAssign("/=", 19), Semicolon(";", 20),
    Less("<", 21), LessEqual("<=", 22), Assign("=", 23),
    Equals("==", 24), Greater(">", 25), GreaterEqual(">=", 26),
    LeftBracket("[", 27), RightBracket("]", 28), LeftBrace("{", 37), Or("||", 38),
    RightBrace("}", 39), Colon(":", 50), CharLiteral("%c_lit", 51),
    StringLiteral("%s_lit", 52), DoubleLiteral("%d_lit", 53), Document("document", 54);

    // Continue("continue", 50); String("string",42),

    private final String name;
    private final int number;

    private TokenType(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getTokenName() {
        return this.name;
    }

    public String getTokenNumber() {
        return Integer.toString(this.number);
    }
}
