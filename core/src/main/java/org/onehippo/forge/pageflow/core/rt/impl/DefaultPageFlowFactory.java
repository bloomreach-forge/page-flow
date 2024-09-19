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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.pageflow.core.PageFlowException;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinition;
import org.onehippo.forge.pageflow.core.def.PageStateDefinition;
import org.onehippo.forge.pageflow.core.def.PageTransitionDefinition;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageFlowFactory;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.util.CollectionUtils;

public class DefaultPageFlowFactory implements PageFlowFactory {

    private static Logger log = LoggerFactory.getLogger(DefaultPageFlowFactory.class);

    @Override
    public PageFlow createPageFlow(HttpServletRequest request, PageFlowDefinition pageFlowDef)
            throws PageFlowException {
        try {
            final List<PageStateDefinition> pageStateDefinitions = pageFlowDef.getPageStateDefinitions();

            if (pageStateDefinitions.isEmpty()) {
                throw new PageFlowException("Page flow definition has no page states: " + pageFlowDef);
            }

            final Map<String, PageState> pageStateMap = new LinkedHashMap<>();
            final Map<String, List<PageTransitionDefinition>> pageTransitionsMap = new LinkedHashMap<>();

            int pageStateIndex = 0;

            for (PageStateDefinition pageStateDef : pageStateDefinitions) {
                final String pageStateDefId = pageStateDef.getId();
                final String pageStateName = pageStateDef.getName();

                if (pageStateMap.containsKey(pageStateDefId)) {
                    throw new PageFlowException(
                            "Duplicate page state id, '" + pageStateDefId + "' in page flow: " + pageFlowDef);
                }

                final Map<String, String> defMetadata = pageStateDef.getMetadata();
                final Map<String, String> metadata = (CollectionUtils.isEmpty(defMetadata) ? null
                        : new HashMap<>(defMetadata));

                final PageState pageState = new DefaultPageState(pageStateDefId, pageStateName, pageStateDef.getPath(),
                        pageStateIndex++, metadata);
                pageStateMap.put(pageState.getId(), pageState);

                final List<PageTransitionDefinition> pageTransList = new ArrayList<>();

                for (PageTransitionDefinition pageTransDef : pageStateDef.getPageTransitionDefinitions()) {
                    pageTransList.add(pageTransDef);
                }

                pageTransitionsMap.put(pageState.getId(), pageTransList);
            }

            final Builder<PageState, String> builder = StateMachineBuilder.builder();

            builder.configureConfiguration().withConfiguration().autoStartup(false)
                    .beanFactory(new StaticListableBeanFactory());

            final StateConfigurer<PageState, String> stateConfigurer = builder.configureStates().withStates();
            boolean initialSet = false;

            for (PageState pageState : pageStateMap.values()) {
                if (!initialSet) {
                    stateConfigurer.initial(pageState);
                    initialSet = true;
                } else {
                    stateConfigurer.state(pageState);
                }
            }

            for (Map.Entry<String, List<PageTransitionDefinition>> entry : pageTransitionsMap.entrySet()) {
                final String pageStateId = entry.getKey();
                final PageState pageState = pageStateMap.get(pageStateId);

                for (PageTransitionDefinition pageTransDef : entry.getValue()) {
                    final String transEvent = StringUtils.trim(pageTransDef.getEvent());

                    if (StringUtils.isEmpty(transEvent)) {
                        log.warn(
                                "Ignoring page transition definition as its event is blank on transition definition: {}, in flow definition: {}.",
                                pageTransDef, pageFlowDef);
                        continue;
                    }

                    final String targetPageStateDefId = StringUtils.trim(pageTransDef.getTargetPageStateDefinitionId());

                    if (StringUtils.isEmpty(targetPageStateDefId)) {
                        log.warn(
                                "Ignoring page transition definition as its target ID is blank on transition definition: {}, in flow definition: {}.",
                                pageTransDef, pageFlowDef);
                        continue;
                    }

                    final PageState targetPageState = pageStateMap.get(targetPageStateDefId);

                    if (targetPageState == null) {
                        log.warn(
                                "Ignoring page transition definition as its target page state is not found for transition definition: {}, in flow definition: {}.",
                                pageTransDef, pageFlowDef);
                        continue;
                    }

                    log.debug("Registering page transtion on '{}' from '{}' to '{}'.", transEvent, pageState,
                            targetPageState);

                    builder.configureTransitions().withExternal().event(transEvent).source(pageState)
                            .target(targetPageState);
                }
            }

            final StateMachine<PageState, String> stateMachine = builder.build();
            return new DefaultPageFlow(pageFlowDef.getId(), stateMachine);
        } catch (Exception e) {
            throw new PageFlowException("Failed to create page flow.", e);
        }
    }
}
