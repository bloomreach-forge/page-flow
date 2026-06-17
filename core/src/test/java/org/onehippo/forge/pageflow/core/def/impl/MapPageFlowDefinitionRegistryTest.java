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
package org.onehippo.forge.pageflow.core.def.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MapPageFlowDefinitionRegistry}.
 */
class MapPageFlowDefinitionRegistryTest {

    private MapPageFlowDefinitionRegistry registry;
    private DefaultPageFlowDefinition flowDef1;
    private DefaultPageFlowDefinition flowDef2;

    @BeforeEach
    void setUp() throws Exception {
        registry = new MapPageFlowDefinitionRegistry();
        flowDef1 = new DefaultPageFlowDefinition("flow1", "Flow 1", "uuid-1");
        flowDef2 = new DefaultPageFlowDefinition("flow2", "Flow 2", "uuid-2");
        registry.addPageFlowDefinition(flowDef1);
        registry.addPageFlowDefinition(flowDef2);
    }

    @Test
    void getPageFlowDefinitionReturnsAddedFlow() throws Exception {
        PageFlowDefinition result = registry.getPageFlowDefinition("flow1");

        assertNotNull(result);
        assertEquals("flow1", result.getId());
    }

    @Test
    void getPageFlowDefinitionReturnsNullForUnknownId() throws Exception {
        assertNull(registry.getPageFlowDefinition("unknown"));
    }

    @Test
    void getPageFlowDefinitionThrowsForBlankId() {
        assertThrows(IllegalArgumentException.class, () -> registry.getPageFlowDefinition("  "));
    }

    @Test
    void getPageFlowDefinitionThrowsForNullId() {
        assertThrows(IllegalArgumentException.class, () -> registry.getPageFlowDefinition(null));
    }

    @Test
    void removePageFlowDefinitionRemovesById() throws Exception {
        registry.removePageFlowDefinition("flow1");

        assertNull(registry.getPageFlowDefinition("flow1"));
        assertNotNull(registry.getPageFlowDefinition("flow2"));
    }

    @Test
    void removePageFlowDefinitionByUuidRemovesCorrectFlow() throws Exception {
        registry.removePageFlowDefinitionByUuid("uuid-1");

        assertNull(registry.getPageFlowDefinition("flow1"));
        assertNotNull(registry.getPageFlowDefinition("flow2"));
    }

    @Test
    void removePageFlowDefinitionByUuidDoesNothingForUnknownUuid() throws Exception {
        // should not throw
        registry.removePageFlowDefinitionByUuid("uuid-unknown");

        assertNotNull(registry.getPageFlowDefinition("flow1"));
    }

    @Test
    void removePageFlowDefinitionByUuidDoesNothingForBlank() throws Exception {
        registry.removePageFlowDefinitionByUuid("  ");

        assertNotNull(registry.getPageFlowDefinition("flow1"));
    }

    @Test
    void clearPageFlowDefinitionsRemovesAll() throws Exception {
        registry.clearPageFlowDefinitions();

        assertNull(registry.getPageFlowDefinition("flow1"));
        assertNull(registry.getPageFlowDefinition("flow2"));
    }

    @Test
    void defaultConstructorAllowsAddingLater() throws Exception {
        MapPageFlowDefinitionRegistry empty = new MapPageFlowDefinitionRegistry();
        DefaultPageFlowDefinition def = new DefaultPageFlowDefinition("f3", "Flow 3", "uuid-3");
        empty.addPageFlowDefinition(def);

        assertEquals("f3", empty.getPageFlowDefinition("f3").getId());
    }

    @Test
    void flowWithBlankUuidIsAddedWithoutUuidIndex() throws Exception {
        // uuid is blank — UUID-keyed removal should be a no-op
        DefaultPageFlowDefinition noUuid = new DefaultPageFlowDefinition("f4", "Flow 4", "  ");
        registry.addPageFlowDefinition(noUuid);

        assertNotNull(registry.getPageFlowDefinition("f4"));
        registry.removePageFlowDefinitionByUuid("  "); // should not remove anything
        assertNotNull(registry.getPageFlowDefinition("f4"));
    }
}
