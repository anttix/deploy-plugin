package hudson.plugins.deploy.jboss;

import hudson.plugins.deploy.ContainerFirstClassLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for 5.x and later JBoss adapters.
 *
 * @author Antti Andreimann
 */
public abstract class JBossJMXAdapter extends JBossAdapter {
    public final String home;

    transient private static Map<String, ClassLoader> classLoaders = new HashMap();

    protected JBossJMXAdapter(String url, String password, String userName, String home) {
        super(url, password, userName);
        this.home = home;
    }

    @Override
    protected ClassLoader getClassLoader() {
        final ClassLoader parent = super.getClassLoader();

        if(home == null)
            return parent;

        ClassLoader cl = classLoaders.get(home);
        if(cl != null)
            return cl;

        File jbossHome = new File(home);
        if(!jbossHome.isDirectory())
            return parent;

        List <File> libDirs = new ArrayList<File>();
        for(String dir : getLibDirNames())
            libDirs.add(new File(jbossHome, dir));

        cl = new ContainerFirstClassLoader(libDirs, parent);
        classLoaders.put(home, cl);

        return cl;
    }

    public abstract String [] getLibDirNames();
}
