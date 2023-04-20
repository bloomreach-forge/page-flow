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
package org.onehippo.forge.pageflow.core.rt;

import java.io.Serializable;

/**
 * Error Item abstraction which is part of {@link Errors}.
 */
public interface ErrorItem extends Serializable {

    /**
     * Return the error code.
     * <p>
     * Application may use this error code as same as a resource bundle key for simplicity.
     * @return the error code
     */
    public String getCode();

    /**
     * Return the (human readable) error message.
     * @return the (human readable) error message
     */
    public String getMessage();

    /**
     * Compares the specified object with this <code>ErrorItem</code> for equality.
     * @param o the object to be compared for equality with this <code>ErrorItem</code>
     * @return true if the specified object is equal to this <code>ErrorItem</code>
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this <code>ErrorItem</code>.
     * @return the hash code value for this <code>ErrorItem</code>
     */
    int hashCode();

}
