/*
 *  Copyright 2024 Bloomreach, Inc. (https://www.bloomreach.com/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.onehippo.forge.pageflow.core;

/**
 * Generic Page Flow Exception.
 */
public class PageFlowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PageFlowException() {
        super();
    }

    public PageFlowException(String message) {
        super(message);
    }

    public PageFlowException(Throwable nested) {
        super(nested);
    }

    public PageFlowException(String msg, Throwable nested) {
        super(msg, nested);
    }
}
