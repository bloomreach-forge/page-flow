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

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DefaultPageStateDefinition}.
 */
class DefaultPageStateDefinitionTest {

    private DefaultPageStateDefinition def;

    @BeforeEach
    void setUp() {
        def = new DefaultPageStateDefinition("S1", "State 1", "/state1", Map.of("key", "val"));
    }

    @Test
    void gettersReturnConstructorValues() {
        assertEquals("S1", def.getId());
        assertEquals("State 1", def.getName());
        assertEquals("/state1", def.getPath());
        assertEquals("val", def.getMetadata().get("key"));
    }

    @Test
    void getMetadataReturnsEmptyMapWhenNull() {
        DefaultPageStateDefinition noMeta = new DefaultPageStateDefinition("S2", "S2", "/s2", null);

        assertTrue(noMeta.getMetadata().isEmpty());
    }

    @Test
    void getPageTransitionDefinitionsEmptyByDefault() {
        DefaultPageStateDefinition s = new DefaultPageStateDefinition("S3", "S3", "/s3", null);

        assertTrue(s.getPageTransitionDefinitions().isEmpty());
    }

    @Test
    void addTransitionDefinitionAppearsInList() {
        DefaultPageTransitionDefinition t = new DefaultPageTransitionDefinition("go", "S2");
        def.addPageTransitionDefinition(t);

        assertEquals(1, def.getPageTransitionDefinitions().size());
        assertEquals("go", def.getPageTransitionDefinitions().get(0).getEvent());
    }

    @Test
    void removeTransitionDefinitionSucceeds() {
        DefaultPageTransitionDefinition t = new DefaultPageTransitionDefinition("go", "S2");
        def.addPageTransitionDefinition(t);

        assertTrue(def.removePageTransitionDefinition(t));
        assertTrue(def.getPageTransitionDefinitions().isEmpty());
    }

    @Test
    void removeTransitionDefinitionReturnsFalseWhenNoneAdded() {
        DefaultPageTransitionDefinition t = new DefaultPageTransitionDefinition("go", "S2");

        assertFalse(def.removePageTransitionDefinition(t));
    }

    @Test
    void clearTransitionDefinitionsEmptiesList() {
        def.addPageTransitionDefinition(new DefaultPageTransitionDefinition("go", "S2"));
        def.clearPageTransitionDefinitions();

        assertTrue(def.getPageTransitionDefinitions().isEmpty());
    }

    @Test
    void equalsSymmetricForSameValues() {
        DefaultPageStateDefinition a = new DefaultPageStateDefinition("S1", "State 1", "/state1",
                Collections.singletonMap("key", "val"));
        DefaultPageStateDefinition b = new DefaultPageStateDefinition("S1", "State 1", "/state1",
                Collections.singletonMap("key", "val"));

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsFalseForDifferentId() {
        DefaultPageStateDefinition other = new DefaultPageStateDefinition("S9", "State 1", "/state1",
                Collections.singletonMap("key", "val"));

        assertNotEquals(def, other);
    }

    @Test
    void equalsReflexive() {
        assertEquals(def, def);
    }

    @Test
    void toStringContainsId() {
        assertTrue(def.toString().contains("S1"));
    }

    @Test
    void getPageTransitionDefinitionsIsUnmodifiable() {
        def.addPageTransitionDefinition(new DefaultPageTransitionDefinition("go", "S2"));

        assertThrows(UnsupportedOperationException.class,
                () -> def.getPageTransitionDefinitions().clear());
    }
}
