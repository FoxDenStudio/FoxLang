package net.foxdenstudio.foxlang.lexer.tokens;

import javax.annotation.Nonnull;

public class SymbolToken extends Token {
    @Nonnull
    private final Symbol symbol;

    public SymbolToken(@Nonnull final Symbol symbol) {
        this.symbol = symbol;
    }

    @Nonnull
    public Symbol getSymbol() {
        return this.symbol;
    }

    @Override
    public String toString() {
        return "SymbolToken{" +
                "symbol=" + this.symbol +
                '}';
    }

    public enum Symbol {
        EQUALS("=", "Equals"),
        PERIOD(".", "Period"),
        OPEN_PARENTHESIS("(", "Open Parenthesis"),
        CLOSE_PARENTHESIS(")", "Close Parenthesis"),
        SEMICOLON(";", "Semicolon"),
        COLON(":", "Colon"),
        COMMA(",", "Comma"),
        COMPARATIVE_EQUALS("==", "Comparative Equals"),
        OPEN_CURLY_BRACE("{", "Open Curly Brace"),
        CLOSE_CURLY_BRACE("}", "Close Curly Brace"),
        OPEN_SQUARE_BRACKET("[", "Open Square Bracket"),
        CLOSE_SQUARE_BRACKET("]", "Close Square Bracket"),
        OPEN_TRIANGLE_BRACKET("<", "Open Triangle Bracket"),
        CLOSE_TRIANGLE_BRACKET(">", "Close Triangle Bracket"),
        INCREMENT("++", "Increment"),
        PLUS("+", "Plus"),
        MULTIPLY("*", "Multiply"),
        DIVIDE("/", "Divide"),
        ;

        @Nonnull
        private final String syntaxLook;
        @Nonnull
        private final String prettyName;

        Symbol(@Nonnull final String syntaxLook, @Nonnull final String prettyName) {

            this.syntaxLook = syntaxLook;
            this.prettyName = prettyName;
        }

        @Nonnull
        public String getSyntaxLook() {
            return this.syntaxLook;
        }

        @Nonnull
        public String getPrettyName() {
            return this.prettyName;
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "syntaxLook='" + this.syntaxLook + '\'' +
                    ", prettyName='" + this.prettyName + '\'' +
                    '}';
        }
    }
}
