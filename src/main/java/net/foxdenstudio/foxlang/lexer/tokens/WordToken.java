package net.foxdenstudio.foxlang.lexer.tokens;

import javax.annotation.Nonnull;

public class WordToken extends Token {
    @Nonnull
    private final String wordData;

    public WordToken(@Nonnull final String wordData) {
        this.wordData = wordData;
    }

    @Nonnull
    public String getWordData() {
        return wordData;
    }

    @Override
    public String toString() {
        return "WordToken{" +
                "wordData=" + wordData +
                '}';
    }
}
