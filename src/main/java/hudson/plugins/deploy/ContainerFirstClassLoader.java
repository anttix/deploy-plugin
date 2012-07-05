package hudson.plugins.deploy;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import org.apache.tools.ant.AntClassLoader;

/**
 * A class loader with child-first semantics, loading classes from a list
 * of "lib" directories filled with .jar files.
 *
 * Used for deploying to containers that need access to container
 * specific "connector classes" (e.g. JBoss).
 *
 * @author Antti Andreimann
 *
 */

public class ContainerFirstClassLoader extends AntClassLoader {
    FilenameFilter jarFilter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".jar");
        }
    };

    public ContainerFirstClassLoader(List<File> libDirs, ClassLoader parent) {
        super(parent, false);

        // Go through libDirs and add all JAR-s found
        for (File dir : libDirs)
            addAllJars(dir);
    }

    /**
     * Add all JARs in a directory (recursive).
     */
    protected void addAllJars(File dir) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (f.isFile() && f.getName().endsWith(".jar")) {
                    // addPathFile(f);
                    addPathComponent(f);
                } else if (f.isDirectory()) {
                    addAllJars(f);
                }
            }
        }
    }
}
