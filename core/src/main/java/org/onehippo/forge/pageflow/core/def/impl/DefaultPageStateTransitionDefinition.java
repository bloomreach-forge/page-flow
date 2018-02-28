/*
 * Copyright 2018 Hippo B.V. (http://www.onehippo.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onehippo.forge.pageflow.core.def.impl;

import org.onehippo.forge.pageflow.core.def.PageTransitionDefinition;

public class DefaultPageStateTransitionDefinition implements PageTransitionDefinition {

    private static final long serialVersionUID = 1L;

    private String event;

    private String targetPageStateDefinitionId;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTargetPageStateDefinitionId() {
        return targetPageStateDefinitionId;
    }

    public void setTargetPageStateDefinitionId(String targetPageStateDefinitionId) {
        this.targetPageStateDefinitionId = targetPageStateDefinitionId;
    }

}
