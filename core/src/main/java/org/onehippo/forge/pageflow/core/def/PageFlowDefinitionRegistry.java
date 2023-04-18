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

import org.onehippo.forge.pageflow.core.PageFlowException;

/**
 * Page Flow Definition Registry abstraction which is responsible for retrieval, removing or clearing <code>PageFlowDefinition</code>
 * instances.
 */
public interface PageFlowDefinitionRegistry {

    /**
     * Retrieve the <code>PageFlowDefinition</code> by the identifier ({@code flowId}).
     * Return null if there's no <code>PageFlowDefinition</code> found by the identifier.
     * @param flowId the identifier of the <code>PageFlowDefinition</code>
     * @return the <code>PageFlowDefinition</code> identified by {@code flowId}, or null if not found.
     * @throws PageFlowException if any exception occurs
     */
    public PageFlowDefinition getPageFlowDefinition(String flowId) throws PageFlowException;

    /**
     * Remove the <code>PageFlowDefinition</code> by the optional {@code uuid} of the <code>PageFlowDefinition</code>.
     * @param uuid the optional UUID value of the <code>PageFlowDefinition</code>
     * @throws PageFlowException if any exception occurs
     */
    public void removePageFlowDefinitionByUuid(String uuid) throws PageFlowException;

    /**
     * Remove the <code>PageFlowDefinition</code> by the identifier ({@code flowId}) of the <code>PageFlowDefinition</code>.
     * @param flowId the identifier value of the <code>PageFlowDefinition</code>
     * @throws PageFlowException if any exception occurs
     */
    public void removePageFlowDefinition(String flowId) throws PageFlowException;

    /**
     * Clear all the registered <code>PageFlowDefinition</code>s in the registry.
     * @throws PageFlowException if any exception occurs
     */
    public void clearPageFlowDefinitions() throws PageFlowException;

}
