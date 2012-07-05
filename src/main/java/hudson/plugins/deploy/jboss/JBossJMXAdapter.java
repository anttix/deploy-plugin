package hudson.plugins.deploy.jboss;

import hudson.plugins.deploy.ContainerFirstClassLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for 5.x and later JBoss adapters.
 *
 * @author Antti Andreimann
 */
public abstract class JBossJMXAdapter extends JBossAdapter {
    public final String home;

    transient private ClassLoader cl;

    protected JBossJMXAdapter(String url, String password, String userName, String home) {
        super(url, password, userName);
        this.home = home;
    }

    @Override
    protected ClassLoader getClassLoader() {
        if(cl != null)
            return cl;

        final ClassLoader parent = super.getClassLoader();

        if(home == null)
            return parent;

        File jbossHome = new File(home);
        if(!jbossHome.isDirectory())
            return parent;

        List <File> libDirs = new ArrayList<File>();
        for(String dir : getLibDirNames())
            libDirs.add(new File(jbossHome, dir));

        cl = new ContainerFirstClassLoader(libDirs, parent);

        return cl;
    }

    public abstract String [] getLibDirNames();
}
