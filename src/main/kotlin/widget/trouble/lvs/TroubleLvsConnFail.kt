package widget.trouble.lvs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.github.shoothzj.dev.storage.StorageLvs
import widget.component.DropdownList
import widget.config.ConfigItemHost
import widget.config.ConfigItemPort
import widget.config.ConfigItemString
import widget.trouble.TroubleShootBase
import widget.trouble.nginx.editNginxInstanceName

@Composable
fun TroubleLvsConnFail() {
    TroubleShootBase(
        content = {
            val lvsNameList = StorageLvs.getInstance().listConfigNames()
            DropdownList(lvsNameList, "lvs ${R.strings.instance}", editLvsInstanceName)
            ConfigItemHost(
                editLvsHost,
                "config host", mutableStateOf("")
            )
            ConfigItemPort(
                editLvsPort,
                "config port", mutableStateOf("")
            )
            ConfigItemString(
                editLvsUsername,
                "ssh username", singleLine = true
            )
            ConfigItemString(
                editLvsPassword,
                "ssh password", singleLine = true
            )
            ConfigItemString(
                editLvsNamespace,
                "namespace", singleLine = true
            )
            ConfigItemString(
                editLvsDeployName,
                "lvs deploy name", singleLine = true
            )
            ConfigItemHost(
                editLvsMasterHost,
                "lvs master host",
                mutableStateOf("")
            )
            ConfigItemHost(
                editLvsSlaveHost,
                "lvs slave host",
                mutableStateOf("")
            )
            if (editLvsInstanceName.value != "") {
                val lvsConfig = StorageLvs.getInstance().getConfig(editLvsInstanceName.value)
                editLvsHost.value = lvsConfig.jumpHost
                editLvsPort.value = lvsConfig.jumpPort.toString()
                editLvsUsername.value = lvsConfig.jumpUsername
                editLvsPassword.value = lvsConfig.jumpPassword
                editLvsMasterHost.value = lvsConfig.masterHost
                editLvsSlaveHost.value = lvsConfig.slaveHost
            }
        },
        result = {
        },
    )
}
