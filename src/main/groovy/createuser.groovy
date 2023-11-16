import hudson.model.User
import hudson.security.*
import jenkins.model.*

class UserManager {
    static Set allUsers() {
        return User.getAll()
    }

    static void createUser(String userName, String password) {
        if (!userExists(userName)) {
            Jenkins instance = Jenkins.getInstanceOrNull()

            def realm = new HudsonPrivateSecurityRealm(false, false, null)

            realm.createAccount(userName, password)
            instance.setSecurityRealm(realm)
            instance.save()
        }
    }

    static Boolean userExists(userName) {
        return User.get(userName) != null
    }

    static void deleteUser(String userId) {
        if (userExists(userId)) {
            User u = User.get(userId)
            u.delete()
        }
    }
}

def mgr = new UserManager()
mgr.createUser("test", "user")

mgr.deleteUser("test")

println(mgr.userExists("yxliu"))

for (user in mgr.allUsers()) {
    println(user.id)
}

