/*
 * Copyright 2018 Hippo B.V. (http://www.onehippo.com)
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
package org.onehippo.forge.pageflow.core.rt;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.onehippo.forge.pageflow.core.PageFlowException;

public interface PageFlow extends Serializable {

    public boolean isStarted();

    public void start() throws PageFlowException;

    public boolean isStopped();

    public void stop() throws PageFlowException;

    public boolean isComplete() throws PageFlowException;

    public PageState getPageState() throws PageFlowException;

    public void sendEvent(String event) throws PageFlowException;

    public List<PageState> getPageStates() throws PageFlowException;

    public Object getModel(String name) throws PageFlowException;

    public void setModel(String name, Object model) throws PageFlowException;

    public Map<String, Object> getModelMap() throws PageFlowException;

    boolean equals(Object o);

    int hashCode();

}
