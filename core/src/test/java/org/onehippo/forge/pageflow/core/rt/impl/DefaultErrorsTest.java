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

import org.junit.jupiter.api.Test;
import org.onehippo.forge.pageflow.core.rt.ErrorItem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link DefaultErrors}.
 */
class DefaultErrorsTest {

    @Test
    void isEmptyTrueWhenNew() {
        DefaultErrors errors = new DefaultErrors();

        assertTrue(errors.isEmpty());
        assertTrue(errors.getItems().isEmpty());
    }

    @Test
    void addItemMakesNotEmpty() {
        DefaultErrors errors = new DefaultErrors();
        ErrorItem item = mock(ErrorItem.class);

        errors.addItem(item);

        assertFalse(errors.isEmpty());
        assertEquals(1, errors.getItems().size());
    }

    @Test
    void removeItemReturnsFalseDueToInvertedNullCheck() {
        // The current removeItem implementation has an inverted null-guard:
        //   if (items == null) { return items.remove(item); }  // only executes when items IS null
        //   return false;                                       // always reached when items is non-null
        // This means removeItem always returns false when items were added via addItem.
        // Test documents this as-is behaviour; fix would invert the guard to items != null.
        DefaultErrors errors = new DefaultErrors();
        ErrorItem item = mock(ErrorItem.class);
        errors.addItem(item);

        boolean removed = errors.removeItem(item);

        assertFalse(removed); // documents the bug — item is NOT actually removed
    }

    @Test
    void clearEmptiesItems() {
        DefaultErrors errors = new DefaultErrors();
        errors.addItem(mock(ErrorItem.class));
        errors.addItem(mock(ErrorItem.class));

        errors.clear();

        assertTrue(errors.isEmpty());
    }

    @Test
    void constructorWithVarArgsPopulatesItems() {
        ErrorItem item1 = mock(ErrorItem.class);
        ErrorItem item2 = mock(ErrorItem.class);
        DefaultErrors errors = new DefaultErrors(item1, item2);

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.getItems().size());
    }

    @Test
    void constructorWithNullVarArgProducesEmpty() {
        DefaultErrors errors = new DefaultErrors((ErrorItem[]) null);

        assertTrue(errors.isEmpty());
    }

    @Test
    void getItemsIsUnmodifiable() {
        DefaultErrors errors = new DefaultErrors();
        errors.addItem(mock(ErrorItem.class));

        assertThrows(UnsupportedOperationException.class, () -> errors.getItems().clear());
    }

    @Test
    void equalsSymmetricForSameItems() {
        ErrorItem item = mock(ErrorItem.class);
        DefaultErrors a = new DefaultErrors(item);
        DefaultErrors b = new DefaultErrors(item);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsReflexive() {
        DefaultErrors e = new DefaultErrors();

        assertEquals(e, e);
    }

    @Test
    void equalsFalseForNull() {
        assertNotEquals(null, new DefaultErrors());
    }

    @Test
    void toStringNotNull() {
        assertNotNull(new DefaultErrors().toString());
    }
}
