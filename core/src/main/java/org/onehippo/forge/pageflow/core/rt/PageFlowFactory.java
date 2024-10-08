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
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;

/**
 * Factory abstraction which is responsible for creating a {@link PageFlow} instance from a {@link PageFlowDefinition}.
 */
public interface PageFlowFactory {

    /**
     * Create a {@link PageFlow} instance by {@link PageFlowDefinition}.
     * @param request <code>HttpServletRequest</code> object
     * @param pageFlowDef {@link PageFlowDefinition} instance
     * @return a {@link PageFlow} instance
     * @throws PageFlowException if any exception occurs
     */
    public PageFlow createPageFlow(HttpServletRequest request, PageFlowDefinition pageFlowDef) throws PageFlowException;

}
