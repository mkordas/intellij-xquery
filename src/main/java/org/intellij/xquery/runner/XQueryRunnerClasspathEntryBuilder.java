package org.intellij.xquery.runner;

import com.intellij.execution.CantRunException;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ex.ApplicationManagerEx;
import com.intellij.openapi.extensions.PluginId;

import java.io.File;

/**
* User: ligasgr
* Date: 29/09/13
* Time: 02:42
*/
class XQueryRunnerClasspathEntryBuilder {

    public String getRunnerClaspathEntry() throws CantRunException {
        final PluginId pluginId = PluginManager.getPluginByClassName(getClass().getName());
        final IdeaPluginDescriptor descriptor = PluginManager.getPlugin(pluginId);
        File classpathFile = getClasspathEntryFileIfExists(descriptor.getPath());
        return classpathFile.getAbsolutePath();
    }

    private File getClasspathEntryFileIfExists(File pluginPath) throws CantRunException {
        final char c = File.separatorChar;
        File rtJarFile = new File(pluginPath, "lib" + c + "intellij-xquery-rt.jar");
        if (rtJarFile.exists()) return rtJarFile;
        File classesDirectory = new File(pluginPath, "classes");
        if (classesDirectory.exists()) return classesDirectory;
        if (isTestRun(pluginPath)) {
            return pluginPath;
        } else {
            throw new CantRunException("Runtime classes not found");
        }
    }

    private boolean isTestRun(File pluginPath) {
        return ApplicationManagerEx.getApplicationEx().isInternal() && new File(pluginPath, "org").exists();
    }
}
