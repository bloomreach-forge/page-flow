/*
 * Copyright 2023 Bloomreach, Inc. (https://www.bloomreach.com/)
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

import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.def.PageTransitionDefinition;

/**
 * Default {@link PageTransitionDefinition} model implementation.
 */
public class DefaultPageTransitionDefinition implements PageTransitionDefinition {

    private static final long serialVersionUID = 1L;

    private final String event;

    private final String targetPageStateDefinitionId;

    public DefaultPageTransitionDefinition(final String event, final String targetPageStateDefinitionId) {
        this.event = event;
        this.targetPageStateDefinitionId = targetPageStateDefinitionId;
    }

    public String getEvent() {
        return event;
    }

    public String getTargetPageStateDefinitionId() {
        return targetPageStateDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultPageTransitionDefinition)) {
            return false;
        }

        DefaultPageTransitionDefinition that = (DefaultPageTransitionDefinition) o;

        return (Objects.equals(event, that.event)
                && Objects.equals(targetPageStateDefinitionId, that.targetPageStateDefinitionId));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(event).append(targetPageStateDefinitionId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("event", event)
                .append("targetPageStateDefinitionId", targetPageStateDefinitionId).toString();
    }
}
