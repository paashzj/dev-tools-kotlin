package widget.config

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfigGroupStatefulSet(
    editNamespace: MutableState<String>,
    editStatefulSetName: MutableState<String>,
) {
    Column {
        ConfigItemString(editNamespace, R.strings.namespace)
        ConfigItemString(editStatefulSetName, R.strings.statefulSetName)
    }
}
