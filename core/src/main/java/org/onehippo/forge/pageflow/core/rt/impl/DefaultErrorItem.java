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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.rt.ErrorItem;

public class DefaultErrorItem implements ErrorItem {

    private static final long serialVersionUID = 1L;

    private final ResourceBundle defaultResourceBundle;
    private final Locale defaultLocale;
    private String code;
    private Object[] arguments;
    private String defaultMessage;

    public DefaultErrorItem(final ResourceBundle defaultResourceBundle, final Locale defaultLocale, final String code) {
        this(defaultResourceBundle, defaultLocale, code, null);
    }

    public DefaultErrorItem(final ResourceBundle defaultResourceBundle, final Locale defaultLocale, final String code,
            final Object[] arguments) {
        this(defaultResourceBundle, defaultLocale, code, arguments, null);
    }

    public DefaultErrorItem(final ResourceBundle defaultResourceBundle, final Locale defaultLocale, final String code,
            final Object[] arguments, final String defaultMessage) {
        this.defaultResourceBundle = defaultResourceBundle;
        this.defaultLocale = defaultLocale;
        this.code = code;
        this.arguments = arguments;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        if (defaultResourceBundle == null) {
            return (defaultMessage != null) ? defaultMessage : code;
        }

        if (code != null) {
            final String msg = StringUtils.defaultString(defaultResourceBundle.getString(code));

            if (arguments == null || arguments.length == 0) {
                return msg;
            } else {
                final MessageFormat messageFormat = new MessageFormat((msg != null ? msg : ""), defaultLocale);
                return messageFormat.format(arguments);
            }
        } else {
            return defaultMessage;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultErrorItem)) {
            return false;
        }

        DefaultErrorItem that = (DefaultErrorItem) o;

        if (!Objects.equals(defaultResourceBundle, that.defaultResourceBundle)) {
            return false;
        }

        if (!Objects.equals(defaultLocale, that.defaultLocale)) {
            return false;
        }

        if (!Objects.equals(code, that.code)) {
            return false;
        }

        if (!Arrays.equals(arguments, that.arguments)) {
            return false;
        }

        if (!Objects.equals(defaultMessage, that.defaultMessage)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(defaultResourceBundle).append(defaultLocale).append(code).append(arguments)
                .append(defaultMessage).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("defaultResourceBundle", defaultResourceBundle)
                .append("defaultLocale", defaultLocale).append("code", code).append("arguments", arguments)
                .append("defaultMessage", defaultMessage).toString();
    }
}
