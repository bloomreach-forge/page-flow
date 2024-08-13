/*
 *  Copyright 2024 Hippo B.V. (http://www.onehippo.com)
 */
package org.onehippo.forge.pageflow.hst.def.impl;

import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;

import org.hippoecm.hst.core.jcr.EventListenersContainerListener;
import org.hippoecm.hst.core.jcr.GenericEventListener;
import org.onehippo.forge.pageflow.core.def.PageFlowDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JCR Observation Event listener to unregister Page Flow Definitions from the registry on definition content changes
 * in JCR.
 */
public class PageFlowDefinitionEventListener extends GenericEventListener implements EventListenersContainerListener {

    private static Logger log = LoggerFactory.getLogger(PageFlowDefinitionEventListener.class);

    private final PageFlowDefinitionRegistry pageFlowDefinitionRegistry;

    public PageFlowDefinitionEventListener(final PageFlowDefinitionRegistry pageFlowDefinitionRegistry) {
        this.pageFlowDefinitionRegistry = pageFlowDefinitionRegistry;
    }

    @Override
    public void onEvent(EventIterator events) {
        while (events.hasNext()) {
            final Event event = events.nextEvent();

            try {
                // figuring out to which variant (live, preview, draft) the event pertains to is too complex/costly.
                // instead, we (try to) evict the identifier from both the live and the preview cache.
                final String uuid = event.getIdentifier();
                pageFlowDefinitionRegistry.removePageFlowDefinitionByUuid(uuid);
            } catch (Exception e) {
                log.warn("Failed to handle event for JCR event '{}'.", event, e);
            }
        }
    }

    @Override
    public void onEventListenersContainerStarted() {
        // do nothing
    }

    @Override
    public void onEventListenersContainerRefreshed() {
        // event listener is reconnected: Because we might have missed changes, we need
        // to unregister everything from the registry
        pageFlowDefinitionRegistry.clearPageFlowDefinitions();
    }

    @Override
    public void onEventListenersContainerStopped() {
        // do nothing
    }
}
