package net.foxdenstudio.foxlang.lexer.tokens;

public class StringToken extends Token {
    private final String data;

    public StringToken(String data) {
        super();
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "StringToken{" +
                "data='" + this.data + '\'' +
                '}';
    }
}
