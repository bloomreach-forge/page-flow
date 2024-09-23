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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

public class DefaultPageFlow implements PageFlow {

    private static final long serialVersionUID = 1L;

    private static final String ATTRIBUTE_MAP_VAR_NAME = DefaultPageFlow.class.getName() + ".attributeMap";

    private final String id;

    private final StateMachine<PageState, String> stateMachine;

    private boolean started;

    private boolean stopped;

    public DefaultPageFlow(final String id, final StateMachine<PageState, String> stateMachine) {
        this.id = id;
        this.stateMachine = stateMachine;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void start() throws PageFlowException {
        if (started) {
            throw new IllegalStateException("Page flow already started.");
        }

        stateMachine.start();
        started = true;
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void stop() throws PageFlowException {
        if (stopped) {
            throw new IllegalStateException("Page flow already stopped.");
        }

        stateMachine.stop();
        stopped = true;
    }

    @Override
    public boolean isComplete() throws PageFlowException {
        return stateMachine.isComplete();
    }

    @Override
    public PageState getPageState() throws PageFlowException {
        State<PageState, String> state = stateMachine.getState();

        if (state != null) {
            return state.getId();
        }

        return null;
    }

    @Override
    public void sendEvent(String event) throws PageFlowException {
        if (isStopped()) {
            throw new IllegalStateException("Page flow is already stopped.");
        }

        stateMachine.sendEvent(event);
    }

    @Override
    public List<PageState> getPageStates() throws PageFlowException {
        Collection<State<PageState, String>> states = stateMachine.getStates();
        List<PageState> pageStates = new LinkedList<>();
        states.forEach(s -> pageStates.add(s.getId()));

        Collections.sort(pageStates, new Comparator<PageState>() {
            @Override
            public int compare(PageState state1, PageState state2) {
                final int index1 = state1.getIndex();
                final int index2 = state2.getIndex();

                if (index1 < index2) {
                    return -1;
                } else if (index1 > index2) {
                    return 1;
                }

                return 0;
            }
        });

        return pageStates;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getAttribute(String name) throws PageFlowException {
        Map<String, Object> attributeMap = (Map<String, Object>) stateMachine.getExtendedState().getVariables()
                .get(ATTRIBUTE_MAP_VAR_NAME);

        if (attributeMap != null) {
            return attributeMap.get(name);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAttribute(String name, Object model) throws PageFlowException {
        if (isStopped()) {
            throw new IllegalStateException("Page flow is already stopped.");
        }

        Map<String, Object> attributeMap = (Map<String, Object>) stateMachine.getExtendedState().getVariables()
                .get(ATTRIBUTE_MAP_VAR_NAME);

        if (attributeMap == null) {
            attributeMap = new LinkedHashMap<>();
            stateMachine.getExtendedState().getVariables().put(ATTRIBUTE_MAP_VAR_NAME, attributeMap);
        }

        attributeMap.put(name, model);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getAttributeMap() throws PageFlowException {
        Map<String, Object> attributeMap = (Map<String, Object>) stateMachine.getExtendedState().getVariables()
                .get(ATTRIBUTE_MAP_VAR_NAME);

        if (attributeMap == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(attributeMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DefaultPageFlow)) {
            return false;
        }

        DefaultPageFlow that = (DefaultPageFlow) o;
        return Objects.equals(stateMachine, that.stateMachine);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stateMachine).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("stateMachine", stateMachine).toString();
    }
}
