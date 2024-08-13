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
package org.onehippo.forge.pageflow.core.rt.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.rt.ErrorItem;
import org.onehippo.forge.pageflow.core.rt.Errors;

public class DefaultErrors implements Errors {

    private static final long serialVersionUID = 1L;

    private List<ErrorItem> items;

    public DefaultErrors() {
    }

    public DefaultErrors(ErrorItem...itemArray) {
        if (itemArray != null && itemArray.length > 0) {
            items = new LinkedList<>();

            for (ErrorItem errorItem : itemArray) {
                items.add(errorItem);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return (items == null || items.isEmpty());
    }

    @Override
    public boolean addItem(ErrorItem item) {
        if (items == null) {
            items = new LinkedList<>();
        }

        return items.add(item);
    }

    @Override
    public boolean removeItem(ErrorItem item) {
        if (items == null) {
            return items.remove(item);
        }

        return false;
    }

    @Override
    public List<ErrorItem> getItems() {
        if (items == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(items);
    }

    @Override
    public void clear() {
        if (items != null) {
            items.clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultErrors)) {
            return false;
        }

        DefaultErrors that = (DefaultErrors) o;

        if (!Objects.equals(items, that.items)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(items).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("items", items).toString();
    }
}
