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

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageStateDefinition;
import org.onehippo.forge.pageflow.core.def.impl.DefaultPageTransitionDefinition;
import org.onehippo.forge.pageflow.core.def.impl.MapPageFlowDefinitionRegistry;
import org.onehippo.forge.pageflow.core.rt.DefaultPageFlowControl;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageFlowControl;
import org.onehippo.forge.pageflow.core.rt.PageFlowFactory;
import org.onehippo.forge.pageflow.core.rt.PageFlowStore;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DefaultPageStateMachineTest {

    private static Logger log = LoggerFactory.getLogger(DefaultPageStateMachineTest.class);

    private PageFlowStore pageFlowStore = new HttpSessionPageFlowStore();
    private MapPageFlowDefinitionRegistry pageFlowDefinitionRegistry;
    private PageFlowFactory pageFlowFactory = new DefaultPageFlowFactory();

    private MockHttpServletRequest request;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        pageFlowDefinitionRegistry = new MapPageFlowDefinitionRegistry();

        DefaultPageFlowDefinition flowDef = new DefaultPageFlowDefinition("flow1", "Page Flow 1",
                UUID.randomUUID().toString());

        DefaultPageStateDefinition pageDef1 = new DefaultPageStateDefinition("P1", "Page 1", "/page1", null);
        DefaultPageStateDefinition pageDef2 = new DefaultPageStateDefinition("P2", "Page 2", "/page2", null);
        DefaultPageStateDefinition pageDef3 = new DefaultPageStateDefinition("P3", "Page 3", "/page3", null);

        DefaultPageTransitionDefinition transDef1 = new DefaultPageTransitionDefinition("P1-to-P2", "P2");
        pageDef1.addPageTransitionDefinition(transDef1);

        DefaultPageTransitionDefinition transDef2 = new DefaultPageTransitionDefinition("P2-to-P3", "P3");
        pageDef2.addPageTransitionDefinition(transDef2);

        flowDef.addPageStateDefinition(pageDef1);
        flowDef.addPageStateDefinition(pageDef2);
        flowDef.addPageStateDefinition(pageDef3);

        pageFlowDefinitionRegistry.addPageFlowDefinition(flowDef);

        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        request.setSession(session);
        request.setAttribute(PageFlowControl.PAGE_FLOW_ID_ATTR_NAME, "flow1");

        PageFlowControl pfc = new DefaultPageFlowControl() {
            {
                setPageFlowStore(pageFlowStore);
                setPageFlowDefinitionRegistry(pageFlowDefinitionRegistry);
                setPageFlowFactory(pageFlowFactory);
            }
        };

        request.setAttribute(PageFlowControl.PAGE_FLOW_CONTROL_ATTR_NAME, pfc);
    }

    @Test
    public void testPageStateMachine() throws Exception {
        assertNull(pageFlowStore.getPageFlow(request, "flow1"));

        PageFlowControl pageFlowControl = PageFlowControl.getDefault(request);
        PageFlow pageFlow = pageFlowControl.getPageFlow(request);

        if (!pageFlow.isStarted()) {
            pageFlow.start();
        }

        log.debug("PageStateMachine instance: {}", pageFlow);
        assertSame(pageFlow, pageFlowStore.getPageFlow(request, "flow1"));
        assertFalse(pageFlow.isComplete());

        PageState pageState = pageFlow.getPageState();
        log.debug("currentPageState: {}", pageState);
        assertEquals("P1", pageState.getId());
        pageFlow.setAttribute("name", "John");
        pageFlow.setAttribute("age", 33);
        assertFalse(pageFlow.isComplete());

        pageFlow.sendEvent("P1-to-P2");
        pageState = pageFlow.getPageState();
        log.debug("currentPageState: {}", pageState);
        assertEquals("P2", pageState.getId());
        assertEquals("John", pageFlow.getAttribute("name"));
        assertEquals(33, pageFlow.getAttribute("age"));
        pageFlow.setAttribute("name", "Jane");
        pageFlow.setAttribute("age", 34);
        assertFalse(pageFlow.isComplete());

        pageFlow.sendEvent("P2-to-P3");
        pageState = pageFlow.getPageState();
        log.debug("currentPageState: {}", pageState);
        assertEquals("P3", pageState.getId());
        assertEquals("Jane", pageFlow.getAttribute("name"));
        assertEquals(34, pageFlow.getAttribute("age"));
        assertFalse(pageFlow.isComplete());

        pageFlow.stop();

        try {
            pageFlow.setAttribute("name", "Paul");
            fail("Shouldn't be able to set attribute after page flow stopped.");
        } catch (IllegalStateException expected) {
        }

        assertEquals("P3", pageState.getId());
        assertEquals("Jane", pageFlow.getAttribute("name"));
        assertTrue(pageFlow.isComplete());
    }
}
