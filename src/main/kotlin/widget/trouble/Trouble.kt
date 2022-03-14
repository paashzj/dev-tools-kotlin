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

package widget.trouble

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp
import module.TroubleEnum
import widget.component.RowPaddingButton
import widget.trouble.bookkeeper.TroubleBookkeeper
import widget.trouble.cassandra.TroubleCassandra
import widget.trouble.kubernetes.TroubleKubernetes
import widget.trouble.lvs.TroubleLvs
import widget.trouble.minio.TroubleMinio
import widget.trouble.mysql.TroubleMysql
import widget.trouble.nginx.TroubleNginx
import widget.trouble.pulsar.TroublePulsar
import widget.trouble.redis.TroubleRedis
import widget.trouble.zookeeper.TroubleZooKeeper

val idx = mutableStateOf(TroubleEnum.Kubernetes)

@Composable
fun TroubleShootScreen() {
    Column {
        Head(idx)
        when (idx.value) {
            TroubleEnum.Kubernetes -> {
                TroubleKubernetes()
            }
            // vm application
            TroubleEnum.Lvs -> {
                TroubleLvs()
            }
            TroubleEnum.Bookkeeper -> {
                TroubleBookkeeper()
            }
            TroubleEnum.Cassandra -> {
                TroubleCassandra()
            }
            TroubleEnum.Minio -> {
                TroubleMinio()
            }
            // container application
            TroubleEnum.Mysql -> {
                TroubleMysql()
            }
            TroubleEnum.Nginx -> {
                TroubleNginx()
            }
            TroubleEnum.Pulsar -> {
                TroublePulsar()
            }
            TroubleEnum.Redis -> {
                TroubleRedis()
            }
            TroubleEnum.ZooKeeper -> {
                TroubleZooKeeper()
            }
        }
    }
}

@Composable
fun Head(idx: MutableState<TroubleEnum>) {
    Row {
        Text(R.strings.TroubleComponent, fontSize = 40.sp)
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Kubernetes
            },
        ) {
            Text(text = "Kubernetes")
        }
        // vm application
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Lvs
            },
        ) {
            Text(text = "Lvs")
        }
        // container application
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Bookkeeper
            }
        ) {
            Text(text = "Bookkeeper")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Cassandra
            }
        ) {
            Text(text = "Cassandra")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Minio
            }
        ) {
            Text(text = "Minio")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Mysql
            },
        ) {
            Text(text = "Mysql")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Nginx
            },
        ) {
            Text(text = "Nginx")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Pulsar
            }
        ) {
            Text(text = "Pulsar")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.Redis
            }
        ) {
            Text("Redis")
        }
        RowPaddingButton(
            onClick = {
                idx.value = TroubleEnum.ZooKeeper
            },
        ) {
            Text(text = "ZooKeeper")
        }
    }
}
