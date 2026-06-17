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

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DefaultErrorItem}.
 */
class DefaultErrorItemTest {

    /** Minimal in-memory ResourceBundle backed by a ListResourceBundle. */
    private static ResourceBundle testBundle() {
        return new java.util.ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] {
                        { "err.required", "Field is required" },
                        { "err.pattern",  "Invalid format: {0}" },
                };
            }
        };
    }

    @Test
    void getCodeReturnsConstructorValue() {
        DefaultErrorItem item = new DefaultErrorItem(null, Locale.ENGLISH, "err.required");

        assertEquals("err.required", item.getCode());
    }

    @Test
    void getMessageFallsBackToCodeWhenNoBundleAndNoDefault() {
        DefaultErrorItem item = new DefaultErrorItem(null, Locale.ENGLISH, "err.required");

        assertEquals("err.required", item.getMessage());
    }

    @Test
    void getMessageUsesDefaultMessageWhenNoBundleAndNullCode() {
        DefaultErrorItem item = new DefaultErrorItem(null, Locale.ENGLISH, null, null, "fallback");

        assertEquals("fallback", item.getMessage());
    }

    @Test
    void getMessageLooksUpCodeInBundle() {
        DefaultErrorItem item = new DefaultErrorItem(testBundle(), Locale.ENGLISH, "err.required");

        assertEquals("Field is required", item.getMessage());
    }

    @Test
    void getMessageFormatsArgumentsWhenPresent() {
        DefaultErrorItem item = new DefaultErrorItem(testBundle(), Locale.ENGLISH, "err.pattern",
                new Object[]{ "ABC123" });

        assertEquals("Invalid format: ABC123", item.getMessage());
    }

    @Test
    void getMessageReturnsNullCodeMessageWhenCodeNullAndBundlePresent() {
        DefaultErrorItem item = new DefaultErrorItem(testBundle(), Locale.ENGLISH, null, null, "my-default");

        assertEquals("my-default", item.getMessage());
    }

    @Test
    void equalsSymmetricForSameValues() {
        DefaultErrorItem a = new DefaultErrorItem(null, Locale.ENGLISH, "code", null, "msg");
        DefaultErrorItem b = new DefaultErrorItem(null, Locale.ENGLISH, "code", null, "msg");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equalsReflexive() {
        DefaultErrorItem a = new DefaultErrorItem(null, Locale.ENGLISH, "code");

        assertEquals(a, a);
    }

    @Test
    void equalsFalseWhenCodeDiffers() {
        DefaultErrorItem a = new DefaultErrorItem(null, Locale.ENGLISH, "code1");
        DefaultErrorItem b = new DefaultErrorItem(null, Locale.ENGLISH, "code2");

        assertNotEquals(a, b);
    }

    @Test
    void equalsFalseForNull() {
        assertNotEquals(null, new DefaultErrorItem(null, Locale.ENGLISH, "c"));
    }

    @Test
    void toStringNotNull() {
        assertNotNull(new DefaultErrorItem(null, Locale.ENGLISH, "c").toString());
    }
}
