package widget.config

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfigGroupDeploy(
    editNamespace: MutableState<String>,
    editDeployName: MutableState<String>,
) {
    Column {
        ConfigItemString(editNamespace, R.strings.namespace)
        ConfigItemString(editDeployName, R.strings.deployName)
    }
}
