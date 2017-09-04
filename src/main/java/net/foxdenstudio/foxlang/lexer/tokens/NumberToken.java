package net.foxdenstudio.foxlang.lexer.tokens;

public class NumberToken extends Token {
    private final long numberData;

    public NumberToken(long numberData) {
        this.numberData = numberData;
    }

    public long getNumberData() {
        return numberData;
    }

    @Override
    public String toString() {
        return "NumberToken{" +
                "numberData=" + numberData +
                '}';
    }
}
