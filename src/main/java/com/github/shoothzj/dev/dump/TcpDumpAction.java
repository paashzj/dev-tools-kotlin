package com.github.shoothzj.dev.dump;

import com.github.shoothzj.dev.storage.StorageSettings;

public class TcpDumpAction {

    private String getTcpDumpPath(String vmArch) {
        String tcpDumpPath = "";
        if (vmArch.startsWith("x86")) {
            tcpDumpPath = StorageSettings.getConfig().getTcpDumpX86FilePath();
        } else if (vmArch.startsWith("aarch64")) {
            tcpDumpPath = StorageSettings.getConfig().getTcpDumpArmFilePath();
        }
        return tcpDumpPath;
    }
}
