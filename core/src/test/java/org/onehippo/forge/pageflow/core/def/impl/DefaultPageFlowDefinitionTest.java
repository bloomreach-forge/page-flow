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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DefaultPageFlowDefinition}.
 */
class DefaultPageFlowDefinitionTest {

    private DefaultPageFlowDefinition flowDef;
    private DefaultPageStateDefinition state1;
    private DefaultPageStateDefinition state2;

    @BeforeEach
    void setUp() {
        flowDef = new DefaultPageFlowDefinition("flow1", "Flow One", "uuid-abc");
        state1 = new DefaultPageStateDefinition("S1", "Page 1", "/page1", null);
        state2 = new DefaultPageStateDefinition("S2", "Page 2", "/page2", null);
    }

    @Test
    void gettersReturnConstructorValues() {
        assertEquals("flow1", flowDef.getId());
        assertEquals("Flow One", flowDef.getName());
        assertEquals("uuid-abc", flowDef.getUuid());
    }

    @Test
    void pageStateDefinitionsEmptyByDefault() {
        assertTrue(flowDef.getPageStateDefinitions().isEmpty());
    }

    @Test
    void addPageStateDefinitionAppearsInList() {
        flowDef.addPageStateDefinition(state1);

        assertEquals(1, flowDef.getPageStateDefinitions().size());
        assertEquals("S1", flowDef.getPageStateDefinitions().get(0).getId());
    }

    @Test
    void removePageStateDefinitionSucceeds() {
        flowDef.addPageStateDefinition(state1);
        flowDef.addPageStateDefinition(state2);

        assertTrue(flowDef.removePageStateDefinition(state1));
        assertEquals(1, flowDef.getPageStateDefinitions().size());
        assertEquals("S2", flowDef.getPageStateDefinitions().get(0).getId());
    }

    @Test
    void removePageStateDefinitionReturnsFalseWhenNoneAdded() {
        assertFalse(flowDef.removePageStateDefinition(state1));
    }

    @Test
    void removeAllPageStateDefinitionsEmptiesList() {
        flowDef.addPageStateDefinition(state1);
        flowDef.addPageStateDefinition(state2);
        flowDef.removeAllPageStateDefinitions();

        assertTrue(flowDef.getPageStateDefinitions().isEmpty());
    }

    @Test
    void pageTransitionDefinitionsEmptyByDefault() {
        assertTrue(flowDef.getPageTransitionDefinitions().isEmpty());
    }

    @Test
    void addPageTransitionDefinitionAppearsInList() {
        DefaultPageTransitionDefinition t = new DefaultPageTransitionDefinition("evt", "S2");
        flowDef.addPageTransitionDefinition(t);

        assertEquals(1, flowDef.getPageTransitionDefinitions().size());
    }

    @Test
    void removePageTransitionDefinitionSucceeds() {
        DefaultPageTransitionDefinition t = new DefaultPageTransitionDefinition("evt", "S2");
        flowDef.addPageTransitionDefinition(t);

        assertTrue(flowDef.removePageTransitionDefinition(t));
        assertTrue(flowDef.getPageTransitionDefinitions().isEmpty());
    }

    @Test
    void removePageTransitionDefinitionReturnsFalseWhenNoneAdded() {
        DefaultPageTransitionDefinition t = new DefaultPageTransitionDefinition("evt", "S2");

        assertFalse(flowDef.removePageTransitionDefinition(t));
    }

    @Test
    void clearPageTransitionDefinitionsEmptiesList() {
        flowDef.addPageTransitionDefinition(new DefaultPageTransitionDefinition("e1", "S2"));
        flowDef.addPageTransitionDefinition(new DefaultPageTransitionDefinition("e2", "S3"));
        flowDef.clearPageTransitionDefinitions();

        assertTrue(flowDef.getPageTransitionDefinitions().isEmpty());
    }

    @Test
    void equalsSymmetricForSameValues() {
        DefaultPageFlowDefinition a = new DefaultPageFlowDefinition("flow1", "Flow One", "uuid-abc");
        DefaultPageFlowDefinition b = new DefaultPageFlowDefinition("flow1", "Flow One", "uuid-abc");

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsFalseWhenIdDiffers() {
        DefaultPageFlowDefinition other = new DefaultPageFlowDefinition("flow9", "Flow One", "uuid-abc");

        assertNotEquals(flowDef, other);
    }

    @Test
    void equalsReflexive() {
        assertEquals(flowDef, flowDef);
    }

    @Test
    void equalsFalseForNull() {
        assertNotEquals(null, flowDef);
    }

    @Test
    void equalsFalseForDifferentType() {
        assertNotEquals("string", flowDef);
    }

    @Test
    void toStringContainsId() {
        assertTrue(flowDef.toString().contains("flow1"));
    }

    @Test
    void getPageStateDefinitionsIsUnmodifiable() {
        flowDef.addPageStateDefinition(state1);

        assertThrows(UnsupportedOperationException.class,
                () -> flowDef.getPageStateDefinitions().clear());
    }
}
