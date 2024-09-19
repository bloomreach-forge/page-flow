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

import java.io.IOException;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.onehippo.forge.pageflow.core.PageFlowException;

/**
 * Page Flow Control abstraction, which provides applications with an easier way to retrieve the current {@link PageFlow}
 * instance from the <code>HttpServletRequest</code>, to send redirection to a specific {@link PageState} and to
 * complete a {@link PageFlow}.
 * <p>
 * For example, an application can use this interface like the following example:
 * <pre>
 * final PageFlowControl flowControl = PageFlowControl.getDefault(request);
 * final PageFlow pageFlow = flowControl.getPageFlow(request);
 * ...
 * </pre>
 */
public interface PageFlowControl {

    /**
     * The servlet context or request attribute name by which a {@link PageFlowControl} instance is stored
     * for an application.
     */
    public static final String PAGE_FLOW_CONTROL_ATTR_NAME = PageFlowControl.class.getName() + ".pageflowcontrol";

    /**
     * The request parameter name by which the identifier of a {@link PageFlow} could be inferred if provided.
     */
    public static final String PAGE_FLOW_ID_PROP_NAME = "pageflowid";

    public static final String PAGE_FLOW_ID_ATTR_NAME = PageFlowControl.class.getName() + "." + PAGE_FLOW_ID_PROP_NAME;

    public static PageFlowControl getDefault(ServletRequest request) {
        PageFlowControl control = (PageFlowControl) request.getAttribute(PAGE_FLOW_CONTROL_ATTR_NAME);

        if (control != null) {
            return control;
        }

        control = (PageFlowControl) request.getServletContext().getAttribute(PAGE_FLOW_CONTROL_ATTR_NAME);

        if (control != null) {
            return control;
        }

        return null;
    }

    public PageFlow getPageFlow(HttpServletRequest request) throws PageFlowException;

    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, PageState pageState)
            throws PageFlowException, IOException, IllegalStateException;

    public void completePageFlow(HttpServletRequest request, PageFlow pageFlow) throws PageFlowException;

}
