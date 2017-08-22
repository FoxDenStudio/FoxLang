package net.foxdenstudio.foxlangy.structure;

import java.util.HashMap;

/**
 * This will reference an actual class in the compiled code
 */
public class FoxScript {

    private final HashMap<String, FoxFunction> functionList;

    public FoxScript() {
        this.functionList = new HashMap<>();
    }

    public void add(final String name, final FoxFunction foxFunction) {
        this.functionList.put(name, foxFunction);
    }

    public HashMap<String, FoxFunction> getFunctionList() {
        return this.functionList;
    }
}
