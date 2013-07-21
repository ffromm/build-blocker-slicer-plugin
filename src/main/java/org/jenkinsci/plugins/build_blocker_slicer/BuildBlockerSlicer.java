package org.jenkinsci.plugins.build_blocker_slicer;

import configurationslicing.UnorderedStringSlicer;
import hudson.Extension;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jet
 * Date: 7/12/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Extension
public class BuildBlockerSlicer extends UnorderedStringSlicer {
    public BuildBlockerSlicer() {
        super(new BuildBlockerSlicerSpec());
    }

    public static class BuildBlockerSlicerSpec extends UnorderedStringSlicerSpec {

        @Override
        public String getName() {
            return "BuildBlocker Slicer";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getUrl() {
            return "buildblocker";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public List getWorkDomain() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public List<String> getValues(Object o) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getName(Object o) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean setValues(Object o, List<String> strings) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getDefaultValueString() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


    }
}
