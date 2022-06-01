/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.dev.shell;

import com.github.shoothzj.dev.module.shell.ExecPodPsResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ExecPodPsResultParserTest {

    @Test
    public void parseFullTestCase1() {
        ArrayList<String> body = new ArrayList<>();
        body.add("UID          PID    PPID  C STIME TTY          TIME CMD");
        body.add("root           1       0  0 15:31 ?        00:00:03 /usr/lib/systemd/systemd --switched-root --system --deserialize 17");
        body.add("root           2       0  0 15:31 ?        00:00:00 [kthreadd]");
        body.add("root           3       2  0 15:31 ?        00:00:00 [rcu_gp]");
        body.add("root           4       2  0 15:31 ?        00:00:00 [rcu_par_gp]");
        body.add("root           6       2  0 15:31 ?        00:00:00 [kworker/0:0H-events_highpri]");
        body.add("root           8       2  0 15:31 ?        00:00:00 [kworker/u256:0-xfs-cil/nvme0n1p3]");
        body.add("root           9       2  0 15:31 ?        00:00:00 [mm_percpu_wq]");
        body.add("root          10       2  0 15:31 ?        00:00:00 [ksoftirqd/0]");
        body.add("root          11       2  0 15:31 ?        00:00:00 [rcu_sched]");
        body.add("root          12       2  0 15:31 ?        00:00:00 [migration/0]");
        body.add("root          13       2  0 15:31 ?        00:00:00 [watchdog/0]");
        body.add("root          14       2  0 15:31 ?        00:00:00 [cpuhp/0]");
        body.add("root          15       2  0 15:31 ?        00:00:00 [cpuhp/1]");
        Assert.assertEquals(13, ExecPodPsResultParser.parseFull(body).size());
    }
    @Test
    public void parseFullTestCase2() {
        ArrayList<String> body = new ArrayList<>();
        body.add("UID          PID    PPID  C STIME TTY          TIME CMD");
        body.add("root           1       0  0 15:31 ?        00:00:03 /usr/lib/systemd/systemd --switched-root --system --deserialize 17");
        List<ExecPodPsResult> podPsResults = ExecPodPsResultParser.parseFull(body);
        Assert.assertEquals(1, podPsResults.size());
        Assert.assertEquals("/usr/lib/systemd/systemd", podPsResults.get(0).getCmd());
    }
}
