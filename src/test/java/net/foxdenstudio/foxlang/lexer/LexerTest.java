package net.foxdenstudio.foxlang.lexer;

import net.foxdenstudio.foxlang.lexer.tokens.Token;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LexerTest {

    private Lexer lexer;

    @Before
    public void setUp() throws Exception {
        final Path test1Path = Paths.get("scripts/test1.fl");
        if (Files.notExists(test1Path)) {
            Files.createDirectories(test1Path.getParent());
            Files.createFile(test1Path);
        }
        this.lexer = new Lexer(Files.readAllBytes(test1Path));
    }

    @Test
    public void tokenize() throws Exception {
        long startTime = System.currentTimeMillis();
        final ArrayList<Token> tokenize = this.lexer.tokenize();
        final long lengthOfTimeMilli = System.currentTimeMillis() - startTime;
        System.out.println("Tokenizing " + tokenize.size() + " tokens took " + lengthOfTimeMilli + " milliseconds!");
    }
}