package widget.kubernetes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.github.shoothzj.dev.module.config.KubernetesConfig
import navigationContext

@Composable
fun KubernetesInstanceScreen() {
    val k8sConfig: KubernetesConfig = navigationContext.value as KubernetesConfig
    Text(text = "kubernetes ${k8sConfig.name}", fontSize = 40.sp)
}