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
package org.onehippo.forge.pageflow.core.rt.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.rt.Errors;
import org.onehippo.forge.pageflow.core.rt.PageState;

public class DefaultPageState implements PageState {

    private static final long serialVersionUID = 1L;

    private final String id;

    private final String name;

    private final String path;

    private final int index;

    private final Map<String, String> metadata;

    private Map<String, Errors> errorsMap;

    public DefaultPageState(final String id, final String name, final String path, final int index,
            final Map<String, String> metadata) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.index = index;
        this.metadata = metadata;
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
    public String getPath() {
        return path;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Map<String, String> getMetadata() {
        if (metadata == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(metadata);
    }

    @Override
    public boolean isErrorsEmpty() {
        return (errorsMap == null || errorsMap.isEmpty());
    }

    @Override
    public Errors addErrors(String name, Errors errors) {
        if (errorsMap == null) {
            errorsMap = new LinkedHashMap<>();
        }

        return errorsMap.put(name, errors);
    }

    @Override
    public void addAllErrors(Map<String, Errors> errorsMap) {
        if (this.errorsMap == null) {
            this.errorsMap = new LinkedHashMap<>();
        }

        this.errorsMap.putAll(errorsMap);
    }

    @Override
    public Errors removeErrors(String name) {
        if (errorsMap != null) {
            return errorsMap.remove(name);
        }

        return null;
    }

    @Override
    public Map<String, Errors> getErrorsMap() {
        if (errorsMap == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(errorsMap);
    }

    @Override
    public void clearAllErrors() {
        if (errorsMap != null) {
            errorsMap.clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultPageState)) {
            return false;
        }

        DefaultPageState that = (DefaultPageState) o;

        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(path, that.path)
                && (index == that.index) && Objects.equals(metadata, that.metadata)
                && Objects.equals(errorsMap, that.errorsMap);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(path).append(index).append(metadata)
                .append(errorsMap).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("path", path)
                .append("index", index).append("metadata", metadata).append("errorsMap", errorsMap).toString();
    }
}
