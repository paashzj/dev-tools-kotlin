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

package com.github.shoothzj.dev.constant;

public class K8sCmdConst {

    public static final String GET_NODE_LIST = "kubectl get node -o wide";

    public static final String GET_NODE_LIST_GREP = GET_NODE_LIST + LinuxCmdConst.GREP;

    public static final String DESCRIBE_NODE = "kubectl describe node %s";

    public static final String GET_STATEFUL_SET_LIST = "kubectl get statefulset -o wide -n %s";

    public static final String GET_DEPLOY_LIST = "kubectl get deploy -o wide -n %s";

    public static final String GET_SERVICE_LIST = "kubectl get service -o wide -n %s";

    public static final String GET_POD_LIST = "kubectl get pod -o wide -n %s";

    public static final String GET_POD_LIST_GREP = GET_POD_LIST + LinuxCmdConst.GREP;

    public static final String DESCRIBE_POD = "kubectl describe pod %s -n %s";

}
