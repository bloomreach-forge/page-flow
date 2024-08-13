/*
 * Copyright 2024 Bloomreach, Inc. (https://www.bloomreach.com/)
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageStateDefinition;
import org.onehippo.forge.pageflow.core.def.PageTransitionDefinition;

/**
 * Default {@link PageFlowDefinition} model implementation.
 */
public class DefaultPageFlowDefinition implements PageFlowDefinition {

    private static final long serialVersionUID = 1L;

    private final String id;

    private final String name;

    private final String uuid;

    private List<PageStateDefinition> pageStateDefs;

    private List<PageTransitionDefinition> pageTransitionDefs;

    public DefaultPageFlowDefinition(final String id, final String name, final String uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public List<PageStateDefinition> getPageStateDefinitions() {
        if (pageStateDefs == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(pageStateDefs);
    }

    public void addPageStateDefinition(PageStateDefinition pageStateDef) {
        if (pageStateDefs == null) {
            pageStateDefs = new LinkedList<>();
        }

        pageStateDefs.add(pageStateDef);
    }

    public boolean removePageStateDefinition(PageStateDefinition pageStateDef) {
        if (pageStateDefs == null) {
            return false;
        }

        return pageStateDefs.remove(pageStateDef);
    }

    public void removeAllPageStateDefinitions() {
        if (pageStateDefs != null) {
            pageStateDefs.clear();
        }
    }

    @Override
    public List<PageTransitionDefinition> getPageTransitionDefinitions() {
        if (pageTransitionDefs == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(pageTransitionDefs);
    }

    public void addPageTransitionDefinition(PageTransitionDefinition pageTransitionDef) {
        if (pageTransitionDefs == null) {
            pageTransitionDefs = new LinkedList<>();
        }

        pageTransitionDefs.add(pageTransitionDef);
    }

    public boolean removePageTransitionDefinition(PageTransitionDefinition pageTransitionDef) {
        if (pageTransitionDefs == null) {
            return false;
        }

        return pageTransitionDefs.remove(pageTransitionDef);
    }

    public void clearPageTransitionDefinitions() {
        if (pageTransitionDefs != null) {
            pageTransitionDefs.clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultPageFlowDefinition)) {
            return false;
        }

        DefaultPageFlowDefinition that = (DefaultPageFlowDefinition) o;

        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(uuid, that.uuid)
                && Objects.equals(pageStateDefs, that.pageStateDefs);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(uuid).append(pageStateDefs).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("uuid", uuid)
                .append("pageStateDefs", pageStateDefs).toString();
    }
}
