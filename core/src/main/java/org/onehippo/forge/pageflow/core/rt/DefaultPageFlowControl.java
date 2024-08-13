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
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default generic {@link PageFlowControl} implementation in a web application.
 */
public class DefaultPageFlowControl implements PageFlowControl {

    private static Logger log = LoggerFactory.getLogger(DefaultPageFlowControl.class);

    private PageFlowDefinitionRegistry pageFlowDefinitionRegistry;
    private PageFlowFactory pageFlowFactory;
    private PageFlowStore pageFlowStore;

    protected DefaultPageFlowControl() {
    }

    @Override
    public PageFlow getPageFlow(HttpServletRequest request) throws PageFlowException {
        String flowId = findPageFlowId(request);

        if (StringUtils.isEmpty(flowId)) {
            return null;
        }

        return getPageFlow(request, flowId);
    }

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, PageState pageState)
            throws PageFlowException, IOException, IllegalStateException {
        final StringBuilder locBuilder = new StringBuilder(80);
        locBuilder.append(request.getContextPath());

        final String path = StringUtils.trim(pageState.getPath());

        if (StringUtils.isNotEmpty(path)) {
            if (path.charAt(0) == '/') {
                locBuilder.append(path);
            } else {
                locBuilder.append('/').append(path);
            }
        }

        final String location = locBuilder.toString();

        if (!Objects.equals(location, request.getRequestURI())) {
            response.sendRedirect(location);
        }
    }

    @Override
    public void completePageFlow(HttpServletRequest request, PageFlow pageFlow) throws PageFlowException {
        try {
            pageFlow.stop();
        } catch (Exception e) {
            log.warn("Page flow was probably stopped.", e);
        }

        PageFlowStore store = getPageFlowStore();

        if (store != null) {
            store.removePageFlow(request, pageFlow.getId());
        }
    }

    protected String findPageFlowId(HttpServletRequest request) {
        String flowId = StringUtils.trim((String) request.getAttribute(PAGE_FLOW_ID_ATTR_NAME));

        if (StringUtils.isEmpty(flowId)) {
            final HttpSession session = request.getSession(false);

            if (session != null) {
                flowId = StringUtils.trim((String) session.getAttribute(PAGE_FLOW_ID_ATTR_NAME));
            }
        }

        return flowId;
    }

    protected PageFlow getPageFlow(HttpServletRequest request, String flowId) throws PageFlowException {
        if (StringUtils.isBlank(flowId)) {
            throw new IllegalArgumentException("Blank page flow ID.");
        }

        PageFlow pageFlow = null;

        PageFlowStore store = getPageFlowStore();

        if (store != null) {
            pageFlow = store.getPageFlow(request, flowId);

            if (pageFlow != null) {
                return pageFlow;
            }
        }

        PageFlowDefinitionRegistry registry = getPageFlowDefinitionRegistry();
        PageFlowFactory factory = getPageFlowFactory();

        if (registry != null && factory != null) {
            PageFlowDefinition pageFlowDef = registry.getPageFlowDefinition(flowId);
            pageFlow = factory.createPageFlow(request, pageFlowDef);

            if (store != null) {
                store.storePageFlow(request, flowId, pageFlow);
            }
        }

        return pageFlow;
    }

    protected PageFlowDefinitionRegistry getPageFlowDefinitionRegistry() {
        return pageFlowDefinitionRegistry;
    }

    protected void setPageFlowDefinitionRegistry(PageFlowDefinitionRegistry pageFlowDefinitionRegistry) {
        this.pageFlowDefinitionRegistry = pageFlowDefinitionRegistry;
    }

    protected PageFlowFactory getPageFlowFactory() {
        return pageFlowFactory;
    }

    protected void setPageFlowFactory(PageFlowFactory pageFlowFactory) {
        this.pageFlowFactory = pageFlowFactory;
    }

    protected PageFlowStore getPageFlowStore() {
        return pageFlowStore;
    }

    protected void setPageFlowStore(PageFlowStore pageFlowStore) {
        this.pageFlowStore = pageFlowStore;
    }

}
