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
package org.onehippo.forge.pageflow.core.def;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Page State Definition abstraction which is part of a Page Flow Definition.
 */
public interface PageStateDefinition extends Serializable {

    /**
     * Return the identifier of the <code>PageStateDefinition</code>.
     * @return the identifier of the <code>PageStateDefinition</code>
     */
    public String getId();

    /**
     * Return the (human readable) name of the <code>PageStateDefinition</code>.
     * @return the (human readable) name of the <code>PageStateDefinition</code>
     */
    public String getName();

    /**
     * Return the logical path info of the <code>PageStateDefinition</code>.
     * <p>
     * This {@code path} info must be translated into a physical URI or client-side route information by an application.
     * In the definition level, this {@code path} info doesn't imply any physical translations by itself.
     * @return the logical path info of the <code>PageStateDefinition</code>
     */
    public String getPath();

    /**
     * Return an unmodifiable metadata map of the <code>PageStateDefinition</code>.
     * @return an unmodifiable metadata map of the <code>PageStateDefinition</code>
     */
    public Map<String, String> getMetadata();

    /**
     * Return an unmodifiable list of the <code>PageStateDefinition</code> level {@link PageTransitionDefinition}s.
     * @return an unmodifiable list of the <code>PageStateDefinition</code> level {@link PageTransitionDefinition}s
     */
    public List<PageTransitionDefinition> getPageTransitionDefinitions();

    /**
     * Compares the specified object with this <code>PageStateDefinition</code> for equality.
     * @param o the object to be compared for equality with this <code>PageStateDefinition</code>
     * @return true if the specified object is equal to this <code>PageStateDefinition</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>PageStateDefinition</code>.
     * @return the hash code value for this <code>PageStateDefinition</code>
     */
    int hashCode();

}
