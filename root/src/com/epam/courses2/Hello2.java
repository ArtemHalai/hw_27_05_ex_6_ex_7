package com.epam.courses2;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class Hello2 {

    public static void main(String[] args) throws Exception {

        File sourceFile = File.createTempFile("Hello", ".java");
        sourceFile.deleteOnExit();

        String classname = sourceFile.getName().split("\\.")[0];
        String sourceCode = "public class " + classname + "{ public void hello() { System.out.print(\"Hello\");}}";

        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.close();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File parentDirectory = sourceFile.getParentFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

    }
}