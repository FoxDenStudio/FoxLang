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
        return symbol;
    }

    @Override
    public String toString() {
        return "SymbolToken{" +
                "symbol=" + symbol +
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
        CLOSE_SQUARE_BRACKET("]", "Close Square Bracket");

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
            return syntaxLook;
        }

        @Nonnull
        public String getPrettyName() {
            return prettyName;
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "syntaxLook='" + syntaxLook + '\'' +
                    ", prettyName='" + prettyName + '\'' +
                    '}';
        }
    }
}
