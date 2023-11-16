import jenkins.model.*
import hudson.util.VersionNumber

class PluginCheck {

    String key
    VersionNumber pluginVersion
    def internalPlugin

    PluginCheck(String artifactId, String version) {
        key = artifactId
        pluginVersion = new VersionNumber(version)
        internalPlugin = new ArrayList<String>(Jenkins.getInstanceOrNull().pluginManager.plugins).find { x -> x.shortName == this.key }
    }

    Boolean getEnabled(){
        return internalPlugin.isEnabled();
    }

    void disable(){
        internalPlugin.disable();
    }

    String shortName() {
        return internalPlugin.shortName
    }

    String displayName() {
        return internalPlugin.displayName
    }

    String installedVersion() {
        return internalPlugin.version
    }

    Boolean isInstalled() {
        return internalPlugin != null && !internalPlugin.isOlderThan(pluginVersion)
    }

    void install() {
        Jenkins.getInstanceOrNull().updateCenter.getPlugin(key).deploy()
    }
}

def pluginCheck = new PluginCheck("jobConfigHistory", "2.0")

if (!pluginCheck.isInstalled()) {
    pluginCheck.install()
} else {
    println(pluginCheck.isInstalled() ? "Installed" : "Not Installed")
    println(pluginCheck.shortName())
    println(pluginCheck.installedVersion())
}