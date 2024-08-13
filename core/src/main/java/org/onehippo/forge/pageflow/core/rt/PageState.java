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
import java.util.Map;

/**
 * Page State runtime abstraction, which is part of a {@link PageFlow}.
 */
public interface PageState extends Serializable {

    /**
     * Return the identifier of this <code>PageState</code>.
     * @return the identifier of this <code>PageState</code>
     */
    public String getId();

    /**
     * Return the (human readable) name of this <code>PageState</code>.
     * @return the (human readable) name of this <code>PageState</code>
     */
    public String getName();

    /**
     * Return the logical path info of the <code>PageState</code>.
     * <p>
     * This {@code path} info must be translated into a physical URI or client-side route information by an application.
     * In the definition level, this {@code path} info doesn't imply any physical translations by itself.
     * @return the logical path info of the <code>PageState</code>
     */
    public String getPath();

    /**
     * Return the internal index, which is the same as definition order in the backend, of the <code>PageState</code>.
     * @return the internal index, which is the same as definition order in the backend, of the <code>PageState</code>
     */
    public int getIndex();

    /**
     * Return an unmodifiable metadata map of the <code>PageState</code>.
     * @return an unmodifiable metadata map of the <code>PageState</code>
     */
    public Map<String, String> getMetadata();

    /**
     * Return true if there's no application error at runtime in this <code>PageState</code>.
     * <p>
     * The application errors can be added, retrieved and removed by applications for an easier application development.
     * @return true if there's no application error at runtime in this <code>PageState</code>
     */
    public boolean isErrorsEmpty();

    /**
     * Add an application specific {@link Errors} by the {@code name} to this <code>PageState</code>.
     * <p>
     * The {@code name} can be used by applications to distinguish the sources of errors. e.g, field name.
     * @param name application specific source name of the errors.
     * @param errors {@link Errors} object
     * @return the previous {@link Errors} value associated with {@code name}, or null if there was no mapping for the {@code name}
     */
    public Errors addErrors(String name, Errors errors);

    /**
     * Add an application specific map of {@link Errors}, each entry of which is paired with {@code name} and {@link Errors}
     * object to this <code>PageState</code>.
     * @param errorsMap an application specific map of {@link Errors}, each entry of which is paired with {@code name}
     * and {@link Errors} object
     */
    public void addAllErrors(Map<String, Errors> errorsMap);

    /**
     * Remove the application specific {@link Errors} by the {@code name} from this <code>PageState</code>.
     * <p>
     * The {@code name} can be used by applications to distinguish the sources of errors. e.g, field name.
     * @param name application specific source name of the errors.
     * @return the previous {@link Errors} value associated with {@code name}, or null if there was no mapping for the {@code name}
     */
    public Errors removeErrors(String name);

    /**
     * Return an application specific map of {@link Errors}, each entry of which is paired with {@code name} and {@link Errors}
     * object in this <code>PageState</code>.
     * @return an application specific map of {@link Errors}, each entry of which is paired with {@code name} and {@link Errors}
     * object in this <code>PageState</code>
     */
    public Map<String, Errors> getErrorsMap();

    /**
     * Clear all the application specific {@link Errors} in this <code>PageState</code>.
     */
    public void clearAllErrors();

    /**
     * Compares the specified object with this <code>PageState</code> for equality.
     * @param o the object to be compared for equality with this <code>PageState</code>
     * @return <tt>true</tt> if the specified object is equal to this <code>PageState</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>PageState</code>.
     * @return the hash code value for this <code>PageState</code>
     */
    int hashCode();

}
