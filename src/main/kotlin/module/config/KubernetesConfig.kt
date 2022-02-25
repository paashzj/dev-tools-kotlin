package module.config

class KubernetesConfig {
    var name: String
    val host: String
    val port: Int
    val sshStep: SshStep

    constructor(name: String, host: String, port: Int, username: String, password: String, rootPassword: String?) {
        this.name = name
        this.host = host
        this.port = port
        if (rootPassword == null) {
            this.sshStep = SshStep(username, password, null, null)
        } else {
            this.sshStep = SshStep(username, password, "root", rootPassword)
        }
    }
}