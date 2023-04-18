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

/**
 * Page Flow Definition abstraction.
 */
public interface PageFlowDefinition extends Serializable {

    /**
     * Return the identifier of a <code>PageFlowDefinition</code>.
     * @return the identifier of a <code>PageFlowDefinition</code>
     */
    public String getId();

    /**
     * Return the (human readable) name of a <code>PageFlowDefinition</code>.
     * @return the (human readable) name of a <code>PageFlowDefinition</code>
     */
    public String getName();

    /**
     * Return the optional UUID of a <code>PageFlowDefinition</code>, possibly reflecting the backend storage level
     * UUID such as JCR.
     * @return the optional UUID of a <code>PageFlowDefinition</code>, possibly reflecting the backend storage level
     * UUID such as JCR
     */
    public String getUuid();

    /**
     * Return an unmodifiable list of {@link PageStateDefinition}s of this <code>PageFlowDefinition</code>.
     * @return an unmodifiable list of {@link PageStateDefinition}s of this <code>PageFlowDefinition</code>
     */
    public List<PageStateDefinition> getPageStateDefinitions();

    /**
     * Return an unmodifiable list of the global {@link PageTransitionDefinition}s of this <code>PageFlowDefinition</code>.
     * <p>
     * The global <code>PageTransitionDefinition</code>s in the <code>PageFlowDefinition</code> level should be
     * registered as <code>PageTransition</code> in each <code>PageState</code> at runtime
     * even if a <code>PageStateDefinition</code> does not define the same <code>PageTransitionDefinition</code> explicitly.
     * <p>
     * This global <code>PageTransitionDefinition</code>s are useful if you want to define a common <code>PageTransitionDefinition</code>
     * that should be applied to each <code>PageStateDefinition</code> without having to define it in each <code>PageStateDefinition</code> level.
     * @return an unmodifiable list of the global {@link PageTransitionDefinition}s of this <code>PageFlowDefinition</code>
     */
    public List<PageTransitionDefinition> getPageTransitionDefinitions();

    /**
     * Compares the specified object with this <code>PageFlowDefinition</code> for equality.
     * @param o the object to be compared for equality with this <code>PageFlowDefinition</code>
     * @return <tt>true</tt> if the specified object is equal to this <code>PageFlowDefinition</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>PageFlowDefinition</code>.
     * @return the hash code value for this <code>PageFlowDefinition</code>
     */
    int hashCode();

}
