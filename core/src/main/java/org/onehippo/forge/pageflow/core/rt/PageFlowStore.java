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

import jakarta.servlet.http.HttpServletRequest;

import org.onehippo.forge.pageflow.core.PageFlowException;

/**
 * Backend Page Flow Store abstraction.
 * <p>
 * The backend could be anything that can store {@link PageFlow} instances and read {@link PageFlow} instances
 * back. For example, in web application environment, one of the simplest implementation could use <code>javax.servlet.http.HttpSession</code>
 * for a visitor.
 */
public interface PageFlowStore {

    /**
     * Find and retrieve the {@link PageFlow} instance by the identifier of the {@link PageFlow} ({@code flowId})
     * from the backend storage. Return null if not found.
     * @param request <code>HttpServletRequest</code> object
     * @param flowId the identifier of the {@link PageFlow}
     * @return the {@link PageFlow} instance by the identifier of the {@link PageFlow} ({@code flowId}) from the
     * backend storage, or null if not found
     * @throws PageFlowException if any exception occurs
     */
    public PageFlow getPageFlow(HttpServletRequest request, String flowId) throws PageFlowException;

    /**
     * Store the {@code pageFlow} into the backend storage by the {@code flowId}.
     * @param request <code>HttpServletRequest</code> object
     * @param flowId the identifier of the {@link PageFlow}
     * @param pageFlow {@link PageFlow} instance
     * @return true if stored
     * @throws PageFlowException if any exception occurs
     */
    public boolean storePageFlow(HttpServletRequest request, String flowId, PageFlow pageFlow) throws PageFlowException;

    /**
     * Remove the associated {@code pageFlow} from the backend storage by the {@code flowId}.
     * @param request <code>HttpServletRequest</code> object
     * @param flowId the identifier of the {@link PageFlow}
     * @return true if removed
     * @throws PageFlowException if any exception occurs
     */
    public boolean removePageFlow(HttpServletRequest request, String flowId) throws PageFlowException;

}
