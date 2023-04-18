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
package org.onehippo.forge.pageflow.core.rt.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageFlowStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSessionPageFlowStore implements PageFlowStore {

    private static Logger log = LoggerFactory.getLogger(HttpSessionPageFlowStore.class);

    private static final String PAGE_FLOW_MAP_ATTR = HttpSessionPageFlowStore.class.getName() + ".pageFlowMap";

    @Override
    public PageFlow getPageFlow(HttpServletRequest request, String flowId) throws PageFlowException {
        Map<String, PageFlow> pageFlowMap = getPageFlowMap(request, false);

        if (pageFlowMap == null) {
            return null;
        }

        return pageFlowMap.get(flowId);
    }

    @Override
    public boolean storePageFlow(HttpServletRequest request, String flowId, PageFlow pageFlow)
            throws PageFlowException {
        Map<String, PageFlow> pageFlowMap = getPageFlowMap(request, true);
        pageFlowMap.put(flowId, pageFlow);
        return true;
    }

    @Override
    public boolean removePageFlow(HttpServletRequest request, String flowId) throws PageFlowException {
        Map<String, PageFlow> pageFlowMap = getPageFlowMap(request, false);

        if (pageFlowMap == null) {
            return false;
        }

        pageFlowMap.remove(flowId);
        return true;
    }

    private Map<String, PageFlow> getPageFlowMap(HttpServletRequest request, boolean create) {
        final HttpSession session = request.getSession(create);

        if (session == null) {
            return null;
        }

        Map<String, PageFlow> pageFlowMap = (Map<String, PageFlow>) session.getAttribute(PAGE_FLOW_MAP_ATTR);

        if (pageFlowMap == null && create) {
            pageFlowMap = new HashMap<>();
            session.setAttribute(PAGE_FLOW_MAP_ATTR, pageFlowMap);
        }

        return pageFlowMap;
    }
}
