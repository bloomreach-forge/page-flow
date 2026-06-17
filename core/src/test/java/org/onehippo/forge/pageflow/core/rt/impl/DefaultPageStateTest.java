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

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.onehippo.forge.pageflow.core.rt.Errors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link DefaultPageState}.
 */
class DefaultPageStateTest {

    private DefaultPageState pageState;

    @BeforeEach
    void setUp() {
        pageState = new DefaultPageState("P1", "Page 1", "/page1", 0,
                Map.of("meta", "value"));
    }

    @Test
    void gettersReturnConstructorValues() {
        assertEquals("P1", pageState.getId());
        assertEquals("Page 1", pageState.getName());
        assertEquals("/page1", pageState.getPath());
        assertEquals(0, pageState.getIndex());
        assertEquals("value", pageState.getMetadata().get("meta"));
    }

    @Test
    void getMetadataEmptyWhenNull() {
        DefaultPageState s = new DefaultPageState("P2", "P2", "/p2", 1, null);

        assertTrue(s.getMetadata().isEmpty());
    }

    @Test
    void isErrorsEmptyTrueByDefault() {
        assertTrue(pageState.isErrorsEmpty());
    }

    @Test
    void addErrorsMakesNotEmpty() {
        Errors errors = mock(Errors.class);
        pageState.addErrors("field1", errors);

        assertFalse(pageState.isErrorsEmpty());
        assertEquals(errors, pageState.getErrorsMap().get("field1"));
    }

    @Test
    void removeErrorsByNameRemovesEntry() {
        Errors errors = mock(Errors.class);
        pageState.addErrors("field1", errors);
        Errors removed = pageState.removeErrors("field1");

        assertEquals(errors, removed);
        assertTrue(pageState.isErrorsEmpty());
    }

    @Test
    void removeErrorsReturnsNullWhenNoneAdded() {
        assertNull(pageState.removeErrors("noSuchField"));
    }

    @Test
    void addAllErrorsMergesAllEntries() {
        Errors e1 = mock(Errors.class);
        Errors e2 = mock(Errors.class);
        pageState.addAllErrors(Map.of("f1", e1, "f2", e2));

        assertEquals(2, pageState.getErrorsMap().size());
    }

    @Test
    void clearAllErrorsEmptiesMap() {
        pageState.addErrors("f", mock(Errors.class));
        pageState.clearAllErrors();

        assertTrue(pageState.isErrorsEmpty());
    }

    @Test
    void getErrorsMapIsUnmodifiable() {
        pageState.addErrors("f", mock(Errors.class));

        assertThrows(UnsupportedOperationException.class,
                () -> pageState.getErrorsMap().clear());
    }

    @Test
    void equalsSymmetricForSameValues() {
        DefaultPageState a = new DefaultPageState("P1", "Page 1", "/page1", 0, null);
        DefaultPageState b = new DefaultPageState("P1", "Page 1", "/page1", 0, null);

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsFalseWhenIdDiffers() {
        DefaultPageState other = new DefaultPageState("P9", "Page 1", "/page1", 0, null);

        assertNotEquals(pageState, other);
    }

    @Test
    void equalsReflexive() {
        assertEquals(pageState, pageState);
    }

    @Test
    void equalsFalseForNull() {
        assertNotEquals(null, pageState);
    }

    @Test
    void toStringContainsId() {
        assertTrue(pageState.toString().contains("P1"));
    }
}
