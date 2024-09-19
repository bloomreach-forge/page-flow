/*
 *  Copyright 2024 Bloomreach, Inc. (https://www.bloomreach.com/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.onehippo.forge.pageflow.cms.editor.field;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Event Definition Information model.
 */
public class EventDefInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String label;

    public EventDefInfo() {
    }

    public EventDefInfo(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EventDefInfo)) {
            return false;
        }

        EventDefInfo that = (EventDefInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(label).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("label", label).toString();
    }
}
