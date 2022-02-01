package org.uwmdiscord.core.module;

import org.uwmdiscord.core.Config;
import org.uwmdiscord.core.logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Registrar {

    public static List<String> getClassNamesDebug() throws URISyntaxException {
        String packageName = "org.uwmdiscord";
        String name = "." + packageName;
        name = name.replace('.', '/');
        // Get a File object for the package
        URL url = Registrar.class.getResource(name);
        File directory = new File(url.toURI());
        Logger.deepDebug(directory.getAbsolutePath());
        if (directory.exists()) {
            List<File> allFiles = getFilesInDirectory(directory);
            return allFiles.stream().map(file -> {
                Logger.deepDebug(file.getAbsolutePath());
                String path = packageName + file.getAbsolutePath().replace("\\", ".").split(packageName)[1];
                path = path.substring(0, path.length() - ".class".length());
                return path;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<File> getFilesInDirectory(File file) {
        if(file.isDirectory()) {
            List<File> directores = Arrays.stream(Objects.requireNonNull(file.listFiles())).filter(File::isDirectory).collect(Collectors.toList());
            List<File> files = Arrays.stream(Objects.requireNonNull(file.listFiles())).filter(f -> f.getName().endsWith(".class") && !f.getName().contains("$")).collect(Collectors.toList());
            List<File> recursiveFiles = directores.stream().map(Registrar::getFilesInDirectory).reduce((fs, acc) -> {
                acc.addAll(fs);
                return acc;
            }).orElse(new ArrayList<>());
            files.addAll(recursiveFiles);
            return files;
        } else return new ArrayList<>();
    }

    private static ArrayList<String> getClassNames() throws URISyntaxException, IOException {
        // Get a File object for the package
        File fileDir = new File(Registrar.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        if(Config.IS_DEBUG) return (ArrayList<String>) getClassNamesDebug();
        ZipInputStream zip = new ZipInputStream(new FileInputStream(fileDir));

        ArrayList<String> classNames = new ArrayList<>();
        ZipEntry entry = zip.getNextEntry();
        while (entry != null) {
            if (!entry.isDirectory()
                    && entry.getName().endsWith(".class")
                    && !entry.getName().contains("$")) {
                String className = entry.getName().replace('/', '.');
                classNames.add(className.substring(0, className.length() - ".class".length()));
            }
            entry = zip.getNextEntry();
        }
        return classNames;
    }

    public static List<Class<?>> getAllClasses() throws URISyntaxException, IOException {
        ArrayList<String> commandClassNames = getClassNames();
        return commandClassNames.stream().map(className -> {
            try {
                Logger.deepDebug("Got class " + className);
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                Logger.warning("Class: " + className + " does not exist, not loading.");
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
