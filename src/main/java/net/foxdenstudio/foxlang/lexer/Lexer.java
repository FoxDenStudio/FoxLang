package net.foxdenstudio.foxlang.lexer;

import net.foxdenstudio.foxlang.lexer.tokens.*;

import java.util.ArrayList;

public final class Lexer {

    private final byte[] data;
    private final int dataLength;

    private int position = 0;

    public Lexer(final byte[] data) {
        this.data = data;
        this.dataLength = data.length;
    }

    public ArrayList<Token> tokenize() {
        final ArrayList<Token> tokenList = new ArrayList<>();
        Token token;
        while ((token = this.lex()) != null) {
            tokenList.add(token);
        }
        return tokenList;
    }

    private Token lex() {

        while (this.position < this.dataLength && Character.isWhitespace(this.data[this.position])) {
            this.position++;
        }

        if (this.position == this.dataLength) {
            return null;
        }

        if (Character.isLetter(this.data[this.position]) || this.data[this.position] == (byte) '_') {
            return this.word();
        }
        if (Character.isDigit(this.data[this.position])) {
            return this.number();
        }

        switch (this.data[this.position++]) {
            case (byte) '=':
                if (this.data[this.position + 1] == (byte) '=') {
                    this.position++;
                    return new SymbolToken(SymbolToken.Symbol.COMPARATIVE_EQUALS);
                }
                return new SymbolToken(SymbolToken.Symbol.EQUALS);
            case (byte) '+':
                if (this.data[this.position + 1] == (byte) '+') {
                    this.position++;
                    return new SymbolToken(SymbolToken.Symbol.INCREMENT);
                }
                return new SymbolToken(SymbolToken.Symbol.PLUS);
            case (byte) '*':
                return new SymbolToken(SymbolToken.Symbol.MULTIPLY);
            case (byte) '/':
                // TODO Start comment if double slash
                return new SymbolToken(SymbolToken.Symbol.DIVIDE);
            case (byte) '"':
                return this.string();
            case (byte) '.':
                return new SymbolToken(SymbolToken.Symbol.PERIOD);
            case (byte) '(':
                return new SymbolToken(SymbolToken.Symbol.OPEN_PARENTHESIS);
            case (byte) ')':
                return new SymbolToken(SymbolToken.Symbol.CLOSE_PARENTHESIS);
            case (byte) '{':
                return new SymbolToken(SymbolToken.Symbol.OPEN_CURLY_BRACE);
            case (byte) '}':
                return new SymbolToken(SymbolToken.Symbol.CLOSE_CURLY_BRACE);
            case (byte) '[':
                return new SymbolToken(SymbolToken.Symbol.OPEN_SQUARE_BRACKET);
            case (byte) ']':
                return new SymbolToken(SymbolToken.Symbol.CLOSE_SQUARE_BRACKET);
            case (byte) ';':
                return new SymbolToken(SymbolToken.Symbol.SEMICOLON);
            case (byte) ':':
                return new SymbolToken(SymbolToken.Symbol.COLON);
            case (byte) ',':
                return new SymbolToken(SymbolToken.Symbol.COMMA);
            case (byte) '<':
                return new SymbolToken(SymbolToken.Symbol.OPEN_TRIANGLE_BRACKET);
            case (byte) '>':
                return new SymbolToken(SymbolToken.Symbol.CLOSE_TRIANGLE_BRACKET);
        }
        return null;
    }

    private Token string() {
        StringBuilder stringBuilder = new StringBuilder();
        while ((char) this.data[this.position] != '"') {
            if (this.data[this.position] == '\\') {
                this.position++;
            }
            stringBuilder.append((char) this.data[this.position++]);
        }
        this.position++;
        return new StringToken(stringBuilder.toString());
    }

    private Token number() {
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isDigit((char) this.data[this.position])) {
            stringBuilder.append((char) this.data[this.position++]);
        }
        return new NumberToken(Long.parseLong(stringBuilder.toString()));
    }

    private Token word() {
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isLetterOrDigit(this.data[this.position]) || this.data[this.position] == (byte) '_') {
            stringBuilder.append((char) this.data[this.position++]);
        }
        return new WordToken(stringBuilder.toString());
    }
}
