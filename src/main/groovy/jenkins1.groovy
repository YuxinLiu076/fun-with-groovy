import jenkins.model.*
import java.util.logging.Logger

Logger logger = Logger.getLogger("")
logger.info "Executing init script"

// remove remember me at login
Jenkins.getInstanceOrNull().setDisableRememberMe(true)
// add a message at the main panel
Jenkins.getInstanceOrNull().setSystemMessage('<h1>Jenkins Server - Automating Jenkins with Groovy</h1>')
Jenkins.getInstanceOrNull().save()

logger.info "Init script complete"
