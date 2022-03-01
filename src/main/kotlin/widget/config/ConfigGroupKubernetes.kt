package widget.config

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Composable
fun ConfigGroupKubernetes(
    editKubernetesHost: MutableState<String>,
    editKubernetesPort: MutableState<String>,
    editKubernetesUsername: MutableState<String>,
    editKubernetesPassword: MutableState<String>,
    editKubernetesRootPassword: MutableState<String>
) {
    Column {
        ConfigItemHost(editKubernetesHost, "config host", mutableStateOf(""))
        ConfigItemPort(editKubernetesPort, "config port", mutableStateOf(""))
        ConfigItemString(editKubernetesUsername, "ssh username")
        ConfigItemString(editKubernetesPassword, "ssh password")
        ConfigItemString(editKubernetesRootPassword, "ssh root password(if you need to switch root)")
    }
}