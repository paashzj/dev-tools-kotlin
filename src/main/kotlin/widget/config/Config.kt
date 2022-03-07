/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package widget.config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp
import module.ConfigEnum
import widget.component.RowPaddingButton

val idx = mutableStateOf(ConfigEnum.Kubernetes)

@Composable
fun Config() {
    Column {
        Row {
            Text(R.strings.config, fontSize = 40.sp)
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Kubernetes
                },
            ) {
                Text(text = "kubernetes")
            }
            // vm application
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Lvs
                },
            ) {
                Text(text = "lvs")
            }
            // container application
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Bookkeeper
                },
            ) {
                Text(text = "bookkeeper")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Cassandra
                },
            ) {
                Text(text = "cassandra")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Minio
                },
            ) {
                Text(text = "minio")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.MySql
                },
            ) {
                Text(text = "mysql")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Nginx
                },
            ) {
                Text(text = "nginx")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Pulsar
                },
            ) {
                Text(text = "pulsar")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.Redis
                },
            ) {
                Text(text = "redis")
            }
            RowPaddingButton(
                onClick = {
                    idx.value = ConfigEnum.ZooKeeper
                },
            ) {
                Text(text = "zookeeper")
            }
        }
        when (idx.value) {
            ConfigEnum.Kubernetes -> {
                ConfigKubernetes()
            }
            ConfigEnum.Lvs -> {
                ConfigLvs()
            }
            ConfigEnum.Nginx -> {
                ConfigNginx()
            }
            ConfigEnum.ZooKeeper -> {
                ConfigZooKeeper()
            }
            ConfigEnum.Pulsar -> {
                ConfigPulsar()
            }
            ConfigEnum.Bookkeeper -> {
                ConfigBookkeeper()
            }
            ConfigEnum.Cassandra -> {
                ConfigCassandra()
            }
            ConfigEnum.MySql -> {
                ConfigMysql()
            }
            ConfigEnum.Minio -> {
                ConfigMinio()
            }
            ConfigEnum.Redis -> {
                ConfigRedis()
            }
        }
    }
}
