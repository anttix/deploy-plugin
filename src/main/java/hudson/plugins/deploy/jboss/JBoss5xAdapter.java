package hudson.plugins.deploy.jboss;

import hudson.Extension;
import hudson.plugins.deploy.ContainerAdapterDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Kohsuke Kawaguchi
 */
public class JBoss5xAdapter extends JBossJMXAdapter {
    @DataBoundConstructor
    public JBoss5xAdapter(String url, String password, String userName, String home) {
        super(url, password, userName, home);
    }

    public String getContainerId() {
        return "jboss5x";
    }

    @Override
    public String [] getLibDirNames() {
        return new String [] { "lib", "common/lib" };
    }

    @Extension
    public static final class DescriptorImpl extends ContainerAdapterDescriptor {
        public String getDisplayName() {
            return "JBoss 5.x";
        }
    }
}
