package net.foxdenstudio.foxlang.compiler;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class JVMClassBuilderTest {
    private JVMClassBuilder jvmClassBuilder;

    @Before
    public void setUp() throws Exception {
        this.jvmClassBuilder = new JVMClassBuilder("test1");
    }

    @Test
    public void generateByteCode() throws Exception {
        try (final FileOutputStream fileOutputStream = new FileOutputStream("scripts/test1.flc")) {
            for (byte b : this.jvmClassBuilder.generateByteCode()) {
                fileOutputStream.write(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}