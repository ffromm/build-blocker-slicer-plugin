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

import hudson.model.FreeStyleProject;
import hudson.plugins.buildblocker.BuildBlockerProperty;
import org.jvnet.hudson.test.HudsonTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Test the get and set values methods of the slicer spec
 */
public class BuildBlockerSlicerTest extends HudsonTestCase {

    public void testGetValues() throws Exception {
        FreeStyleProject project1 = this.createFreeStyleProject("project1");
        BuildBlockerSlicer.BuildBlockerSlicerSpec spec = new BuildBlockerSlicer.BuildBlockerSlicerSpec();
        List<String> values = spec.getValues(project1);
        assertNotNull(values);
        assertEquals(0, values.size());

        BuildBlockerProperty buildBlockerProperty = new BuildBlockerProperty();
        String blockingJobs = "blockingJob\nnextBlockingJob";
        buildBlockerProperty.setBlockingJobs(blockingJobs);
        buildBlockerProperty.setUseBuildBlocker(true);
        project1.addProperty(buildBlockerProperty);

        values = spec.getValues(project1);
        assertNotNull(values);
        assertEquals(1, values.size());
        assertEquals(blockingJobs, values.get(0));
    }

    public void testSetValues() throws Exception {
        FreeStyleProject project1 = this.createFreeStyleProject("project1");
        List<String> set = new ArrayList<String>();

        BuildBlockerSlicer.BuildBlockerSlicerSpec spec = new BuildBlockerSlicer.BuildBlockerSlicerSpec();
        boolean changed = spec.setValues(project1, set);
        assertFalse(changed);
        assertNull(project1.getProperty(BuildBlockerProperty.class));

        String blockingJobs = "blockingJob\nnextBlockingJob";
        set.add(blockingJobs);

        changed = spec.setValues(project1, set);
        assertTrue(changed);

        BuildBlockerProperty buildBlockerProperty = project1.getProperty(BuildBlockerProperty.class);
        assertNotNull(buildBlockerProperty);
        assertEquals(blockingJobs, buildBlockerProperty.getBlockingJobs());
        assertTrue(buildBlockerProperty.isUseBuildBlocker());
    }
}
