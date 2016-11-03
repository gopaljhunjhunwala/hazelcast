/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet2.impl;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.Operation;

import java.io.IOException;

class InitPlanOperation extends Operation {

    private String engineName;
    private ExecutionPlan plan;

    public InitPlanOperation() {
    }

    public InitPlanOperation(String engineName, ExecutionPlan plan) {
        this.engineName = engineName;
        this.plan = plan;
    }

    @Override
    public void run() throws Exception {
        JetService service = getService();
        ExecutionContext executionContext = service.getExecutionContext(engineName);
        executionContext.executePlan(plan).get();
    }

    @Override
    protected void writeInternal(ObjectDataOutput out) throws IOException {
        super.writeInternal(out);

        out.writeUTF(engineName);
        out.writeObject(plan);
    }

    @Override
    protected void readInternal(ObjectDataInput in) throws IOException {
        super.readInternal(in);

        engineName = in.readUTF();
        plan = in.readObject();
    }
}
