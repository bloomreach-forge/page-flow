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
package org.onehippo.forge.pageflow.core.rt;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.onehippo.forge.pageflow.core.PageFlowException;

/**
 * Page Flow runtime abstraction.
 */
public interface PageFlow extends Serializable {

    /**
     * Return the identifier of the <code>PageFlow</code> instance.
     * @return the identifier of the <code>PageFlow</code> instance
     */
    public String getId();

    /**
     * Return true if this <code>PageFlow</code> instance has started.
     * @return true if this <code>PageFlow</code> instance has started
     */
    public boolean isStarted();

    /**
     * Start this <code>PageFlow</code> instance.
     * @throws PageFlowException if it can't start
     */
    public void start() throws PageFlowException;

    /**
     * Return true if this <code>PageFlow</code> instance has stopped.
     * @return true if this <code>PageFlow</code> instance has stopped
     */
    public boolean isStopped();

    /**
     * Stop this <code>PageFlow</code> instance.
     * @throws PageFlowException if it can't stop
     */
    public void stop() throws PageFlowException;

    /**
     * Return true if this <code>PageFlow</code> instance is completed.
     * @return true if this <code>PageFlow</code> instance is completed
     * @throws PageFlowException if any exception occurs
     */
    public boolean isComplete() throws PageFlowException;

    /**
     * Return the current {@link PageState} instance in the <code>PageFlow</code>.
     * @return the current {@link PageState} instance in the <code>PageFlow</code>
     * @throws PageFlowException if any exception occurs
     */
    public PageState getPageState() throws PageFlowException;

    /**
     * Send an event to the <code>PageFlow</code>, which might cause a Page Transition as a result.
     * @param event event
     * @throws PageFlowException if any exception occurs
     */
    public void sendEvent(String event) throws PageFlowException;

    /**
     * Return an unmodifiable list of the {@link PageState} of this <code>PageFlow</code>.
     * @return an unmodifiable list of the {@link PageState} of this <code>PageFlow</code>
     * @throws PageFlowException if any exception occurs
     */
    public List<PageState> getPageStates() throws PageFlowException;

    /**
     * Find and return the attribute model object of this <code>PageFlow</code> by the {@code name}.
     * Return null if not found.
     * @param name attribute name
     * @return the attribute model object of this <code>PageFlow</code> by the {@code name}, null if not found
     * @throws PageFlowException if any exception occurs
     */
    public Object getAttribute(String name) throws PageFlowException;

    /**
     * Set the attribute by the {@code name} on this <code>PageFlow</code>.
     * @param name attribute name
     * @param model attribute model object
     * @throws PageFlowException if any exception occurs
     */
    public void setAttribute(String name, Object model) throws PageFlowException;

    /**
     * Return an unmodifiable attributes map of this <code>PageFlow</code>.
     * @return an unmodifiable attributes map of this <code>PageFlow</code>
     * @throws PageFlowException if any exception occurs
     */
    public Map<String, Object> getAttributeMap() throws PageFlowException;

    /**
     * Compares the specified object with this <code>PageFlow</code> for equality.
     * @param o the object to be compared for equality with this <code>PageFlow</code>
     * @return <tt>true</tt> if the specified object is equal to this <code>PageFlow</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>PageFlow</code>.
     * @return the hash code value for this <code>PageFlow</code>
     */
    int hashCode();

}
