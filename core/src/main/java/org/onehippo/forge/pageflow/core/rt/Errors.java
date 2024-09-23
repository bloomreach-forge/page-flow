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

/**
 * Errors abstraction for an application subject (e.g, a field in a form of an application).
 */
public interface Errors extends Serializable {

    /**
     * Return true if there's no errors.
     * @return true if there's no errors
     */
    public boolean isEmpty();

    /**
     * Add an {@link ErrorItem} to this <code>Errors</code>.
     * @param item an {@link ErrorItem}
     * @return true if added
     */
    public boolean addItem(ErrorItem item);

    /**
     * Remove the given {@link ErrorItem} ({@code item}) from this <code>Errors</code>.
     * @param item an {@link ErrorItem}
     * @return true if removed
     */
    public boolean removeItem(ErrorItem item);

    /**
     * Return an unmodifiable list of {@link ErrorItem}s in this <code>Errors</code>.
     * @return an unmodifiable list of {@link ErrorItem}s in this <code>Errors</code>
     */
    public List<ErrorItem> getItems();

    /**
     * Clear out all the {@link ErrorItem}s in this <code>Errors</code>.
     */
    public void clear();

    /**
     * Compares the specified object with this <code>Errors</code> for equality.
     * @param o the object to be compared for equality with this <code>Errors</code>
     * @return true if the specified object is equal to this <code>Errors</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>Errors</code>.
     * @return the hash code value for this <code>Errors</code>
     */
    int hashCode();

}
