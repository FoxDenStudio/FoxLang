package net.foxdenstudio.foxlang.lexer;

import net.foxdenstudio.foxlang.lexer.tokens.NumberToken;
import net.foxdenstudio.foxlang.lexer.tokens.SymbolToken;
import net.foxdenstudio.foxlang.lexer.tokens.Token;
import net.foxdenstudio.foxlang.lexer.tokens.WordToken;

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
        while ((token = lex()) != null) {
            tokenList.add(token);
        }
        return tokenList;
    }

    private Token lex() {

        while (position < dataLength && Character.isWhitespace(data[position])) {
            position++;
        }

        if (position == dataLength) {
            return null;
        }

        if (Character.isLetter(data[position]) || data[position] == (byte) '_') {
            return word();
        }
        if (Character.isDigit(data[position])) {
            return number();
        }

        switch (data[position++]) {
            case (byte) '=':
                if (data[position + 1] == (byte) '=') {
                    position++;
                    return new SymbolToken(SymbolToken.Symbol.COMPARATIVE_EQUALS);
                }
                return new SymbolToken(SymbolToken.Symbol.EQUALS);
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
        }
        return null;
    }

    private Token number() {
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isDigit((char) data[position])) {
            stringBuilder.append((char) data[position++]);
        }
        return new NumberToken(Long.parseLong(stringBuilder.toString()));
    }

    private Token word() {
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isLetterOrDigit(data[position]) || data[position] == (byte) '_') {
            stringBuilder.append((char) data[position++]);
        }
        return new WordToken(stringBuilder.toString());
    }
}
