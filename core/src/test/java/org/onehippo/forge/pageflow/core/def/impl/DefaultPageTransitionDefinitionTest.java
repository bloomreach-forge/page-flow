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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DefaultPageTransitionDefinition}.
 */
class DefaultPageTransitionDefinitionTest {

    @Test
    void getEventAndTargetReturnConstructorValues() {
        DefaultPageTransitionDefinition def = new DefaultPageTransitionDefinition("next", "pageB");

        assertEquals("next", def.getEvent());
        assertEquals("pageB", def.getTargetPageStateDefinitionId());
    }

    @Test
    void equalsTrueForSameEventAndTarget() {
        DefaultPageTransitionDefinition a = new DefaultPageTransitionDefinition("e1", "t1");
        DefaultPageTransitionDefinition b = new DefaultPageTransitionDefinition("e1", "t1");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsFalseWhenEventDiffers() {
        DefaultPageTransitionDefinition a = new DefaultPageTransitionDefinition("e1", "t1");
        DefaultPageTransitionDefinition b = new DefaultPageTransitionDefinition("e2", "t1");

        assertNotEquals(a, b);
    }

    @Test
    void equalsFalseWhenTargetDiffers() {
        DefaultPageTransitionDefinition a = new DefaultPageTransitionDefinition("e1", "t1");
        DefaultPageTransitionDefinition b = new DefaultPageTransitionDefinition("e1", "t2");

        assertNotEquals(a, b);
    }

    @Test
    void equalsFalseForNull() {
        DefaultPageTransitionDefinition a = new DefaultPageTransitionDefinition("e1", "t1");

        assertNotEquals(null, a);
    }

    @Test
    void equalsFalseForDifferentType() {
        DefaultPageTransitionDefinition a = new DefaultPageTransitionDefinition("e1", "t1");

        assertNotEquals("string", a);
    }

    @Test
    void equalsReflexive() {
        DefaultPageTransitionDefinition a = new DefaultPageTransitionDefinition("e1", "t1");

        assertEquals(a, a);
    }

    @Test
    void toStringContainsEventAndTarget() {
        DefaultPageTransitionDefinition def = new DefaultPageTransitionDefinition("go", "dest");
        String s = def.toString();

        assertTrue(s.contains("go"), "toString should contain event");
        assertTrue(s.contains("dest"), "toString should contain target");
    }

    @Test
    void nullEventAndTargetAreAccepted() {
        DefaultPageTransitionDefinition def = new DefaultPageTransitionDefinition(null, null);

        assertNull(def.getEvent());
        assertNull(def.getTargetPageStateDefinitionId());
    }
}
