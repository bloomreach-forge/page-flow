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
package org.onehippo.forge.pageflow.core.def;

import java.io.Serializable;

/**
 * Page Transition Definition abstraction, defining the source event name and target <code>PageStateDefinition</code>'s ID.
 */
public interface PageTransitionDefinition extends Serializable {

    /**
     * Return the source event name causing this page transition.
     * @return the source event name causing this page transition
     */
    public String getEvent();

    /**
     * Return the target <code>PageStateDefinition</code>'s ID.
     * @return the target <code>PageStateDefinition</code>'s ID
     */
    public String getTargetPageStateDefinitionId();

    /**
     * Compares the specified object with this <code>PageTransitionDefinition</code> for equality.
     * @param o the object to be compared for equality with this <code>PageTransitionDefinition</code>
     * @return <tt>true</tt> if the specified object is equal to this <code>PageTransitionDefinition</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>PageTransitionDefinition</code>.
     * @return the hash code value for this <code>PageTransitionDefinition</code>
     */
    int hashCode();

}
