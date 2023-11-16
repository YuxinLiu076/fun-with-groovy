import jenkins.model.*

class PluginManager {
    Set pluginIds

    PluginManager() {
        File whiteList = new File("/var/jenkins_home/userContent/whitelist.txt")
        pluginIds = whiteList.readLines()
    }

    Set installedPluginIds() {
        return (Set) (Jenkins.getInstanceOrNull().pluginManager.plugins.shortName)
    }

    Set installedNotInWhitelist() {
        return installedPluginIds().minus(pluginIds).sort()
    }

    Set whitelistedNotInstalled() {
        return pluginIds.minus(installedPluginIds())
    }

    void installWhitelist() {
        for (pluginId in whitelistedNotInstalled()) {
            PluginCheck plugin = new PluginCheck(pluginId, ".9")
            plugin.install()
        }
    }

    void disableNotWhitelisted() {
        for (pluginId in installedNotInWhitelist()) {
            PluginCheck plugin = new PluginCheck(pluginId, ".9")

            if (plugin.enabled) {
                println('enabled, disabling')
                plugin.disable()
            }
        }
    }
}

def pluginManager = new PluginManager()
pluginManager.installWhitelist()