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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpringStateMachineTest {

    private static Logger log = LoggerFactory.getLogger(SpringStateMachineTest.class);

    @Test
    public void testStateMachine() throws Exception {
        Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
            .withConfiguration()
                .autoStartup(false)
                .beanFactory(new StaticListableBeanFactory());

        builder.configureStates()
            .withStates()
                .initial("S0", createAction("initial-action"))
                .end("SF")
                .state("S1", createAction("S1-begin"), createAction("S1-end"))
                .state("S2", createAction("S2-begin"), createAction("S2-end"));

        builder.configureTransitions()
            .withExternal()
                .event("0to1").source("S0").target("S1");

        builder.configureTransitions()
            .withExternal()
                .event("1to2").source("S1").target("S2");

        builder.configureTransitions()
            .withExternal()
                .event("2toF").source("S2").target("SF");

        StateMachine<String, String> sm = builder.build();
        sm.start();
        assertFalse(sm.isComplete());

        assertEquals("S0", sm.getState().getId());
        assertFalse(sm.isComplete());

        sm.sendEvent("0to1");
        assertEquals("S1", sm.getState().getId());
        assertFalse(sm.isComplete());

        sm.sendEvent("1to2");
        assertEquals("S2", sm.getState().getId());
        assertFalse(sm.isComplete());

        sm.sendEvent("2toF");
        assertEquals("SF", sm.getState().getId());
        assertTrue(sm.isComplete());

        sm.stop();
    }

    private Action<String, String> createAction(final String actionName) {
        return new Action<String, String>() {
            @Override
            public void execute(StateContext<String, String> context) {
                log.debug("ACTION: {}", actionName);
            }
        };
    }
}
