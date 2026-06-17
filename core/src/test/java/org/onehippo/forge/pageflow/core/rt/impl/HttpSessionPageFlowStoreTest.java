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
package org.onehippo.forge.pageflow.core.rt.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link HttpSessionPageFlowStore}.
 */
@ExtendWith(MockitoExtension.class)
class HttpSessionPageFlowStoreTest {

    private HttpSessionPageFlowStore store;
    private MockHttpServletRequest request;
    private MockHttpSession session;

    @Mock
    private PageFlow mockPageFlow;

    @BeforeEach
    void setUp() {
        store = new HttpSessionPageFlowStore();
        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        request.setSession(session);
    }

    @Test
    void getPageFlowReturnsNullWhenNotStored() throws Exception {
        assertNull(store.getPageFlow(request, "flow1"));
    }

    @Test
    void storeAndRetrievePageFlow() throws Exception {
        boolean stored = store.storePageFlow(request, "flow1", mockPageFlow);

        assertTrue(stored);
        assertSame(mockPageFlow, store.getPageFlow(request, "flow1"));
    }

    @Test
    void getPageFlowReturnsNullForDifferentFlowId() throws Exception {
        store.storePageFlow(request, "flow1", mockPageFlow);

        assertNull(store.getPageFlow(request, "flow2"));
    }

    @Test
    void removePageFlowMakesGetReturnNull() throws Exception {
        store.storePageFlow(request, "flow1", mockPageFlow);
        boolean removed = store.removePageFlow(request, "flow1");

        assertTrue(removed);
        assertNull(store.getPageFlow(request, "flow1"));
    }

    @Test
    void removePageFlowReturnsTrueWhenSessionExistsAndFlowAbsent() throws Exception {
        // The session exists (set in setUp), but the flow was never stored — map is null.
        // getPageFlowMap(request, false) returns null (no map created yet), so the method
        // returns false when the page-flow map has not been created.
        boolean removed = store.removePageFlow(request, "nonexistent");

        assertFalse(removed);
    }

    @Test
    void getPageFlowReturnsNullWhenNoSessionExists() throws Exception {
        // request with no session already created; get without create returns null
        MockHttpServletRequest noSessionRequest = new MockHttpServletRequest();
        // no session set — getSession(false) returns null

        assertNull(store.getPageFlow(noSessionRequest, "flow1"));
    }

    @Test
    void removePageFlowReturnsFalseWhenNoSession() throws Exception {
        MockHttpServletRequest noSessionRequest = new MockHttpServletRequest();

        assertFalse(store.removePageFlow(noSessionRequest, "flow1"));
    }

    @Test
    void storeCreatesSessionIfAbsent() throws Exception {
        MockHttpServletRequest noSessionRequest = new MockHttpServletRequest();
        // session is null before store
        assertNull(noSessionRequest.getSession(false));

        store.storePageFlow(noSessionRequest, "flow1", mockPageFlow);

        assertNotNull(noSessionRequest.getSession(false));
        assertSame(mockPageFlow, store.getPageFlow(noSessionRequest, "flow1"));
    }
}
