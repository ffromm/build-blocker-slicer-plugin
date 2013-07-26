/*
 * The MIT License
 *
 * Copyright (c) 2004-2011, Sun Microsystems, Inc., Frederik Fromm
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.build_blocker_slicer;

import configurationslicing.UnorderedStringSlicer;
import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.plugins.buildblocker.BuildBlockerProperty;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration slicer for the build blocker plugin
 */
@Extension
public class BuildBlockerSlicer extends UnorderedStringSlicer<AbstractProject<?,?>> {
    public static final String DISABLED = "(disabled)";

    public BuildBlockerSlicer() {
        super(new BuildBlockerSlicerSpec());
    }

    public static class BuildBlockerSlicerSpec extends UnorderedStringSlicerSpec<AbstractProject<?,?>> {

        @Override
        public String getName() {
            return "Build Blocker Slicer";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getUrl() {
            return "buildblocker";  //To change body of implemented methods use File | Settings | File Templates.
        }

        @SuppressWarnings("unchecked")
        @Override
        public List getWorkDomain() {
            return (List) Jenkins.getInstance().getAllItems(AbstractProject.class);
        }

        @Override
        public List<String> getValues(AbstractProject<?, ?> item) {
            List<String> content = new ArrayList<String>();
            BuildBlockerProperty property = item.getProperty(BuildBlockerProperty.class);
            if(property != null && StringUtils.isNotEmpty(property.getBlockingJobs())) {
                content.add(property.getBlockingJobs());
            }
            return content;
        }

        @Override
        public String getName(AbstractProject<?, ?> item) {
            return item.getFullName();
        }

        @Override
        public boolean setValues(AbstractProject<?, ?> item, List<String> set) {
            if(set.isEmpty() || StringUtils.isEmpty(set.get(0))) {
                return false;
            }

            String blockingJobsExpressions = set.get(0);
            BuildBlockerProperty property = item.getProperty(BuildBlockerProperty.class);

            if(property == null) {
                property = new BuildBlockerProperty();

                try {
                    item.addProperty(property);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            property.setBlockingJobs(blockingJobsExpressions);
            property.setUseBuildBlocker(true);

            try {
                item.save();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        public String getDefaultValueString() {
            return DISABLED;
        }


    }
}
