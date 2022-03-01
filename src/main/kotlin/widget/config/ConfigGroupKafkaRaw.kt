package widget.config

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@Composable
fun ConfigGroupKafkaRaw(
    editKafkaHost: MutableState<String>,
    editKafkaPort: MutableState<String>,
) {
    Column {
        ConfigItemHost(editKafkaHost, "kafka host", mutableStateOf(""))
        ConfigItemPort(editKafkaPort, "kafka port", mutableStateOf(""))
    }
}