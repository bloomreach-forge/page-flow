/*
 *  Copyright 2023 Bloomreach, Inc. (https://www.bloomreach.com/)
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
package org.onehippo.forge.pageflow.cms.editor.field;

import java.util.Iterator;
import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.repository.util.JcrUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceFacade;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ExternalDocumentServiceFacade} implementation to retrieve/select Page Flow Transition Source Event.
 */
public class TransitionSourceEventDefServiceFacade implements ExternalDocumentServiceFacade<EventDefInfo> {

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(TransitionSourceEventDefServiceFacade.class);

    @Override
    public ExternalDocumentCollection<EventDefInfo> searchExternalDocuments(ExternalDocumentServiceContext context,
            String queryString) {
        SimpleExternalDocumentCollection<EventDefInfo> collection = new SimpleExternalDocumentCollection<>();
        final boolean queryStringSet = StringUtils.isNotBlank(queryString);

        try {
            final Node transNode = context.getContextModel().getNode();
            final Node variant = findPageFlowDocumentVariantNode(transNode);

            if (variant.hasNode("pageflow:eventdef")) {
                for (NodeIterator nodeIt = variant.getNodes("pageflow:eventdef"); nodeIt.hasNext();) {
                    Node eventDefNode = nodeIt.nextNode();

                    if (eventDefNode != null) {
                        final String eventName = JcrUtils.getStringProperty(eventDefNode, "pageflow:name", null);
                        final String eventLabel = JcrUtils.getStringProperty(eventDefNode, "pageflow:value", null);

                        if (StringUtils.isNotBlank(eventName)) {
                            if (!queryStringSet || StringUtils.containsIgnoreCase(eventName, queryString)
                                    || StringUtils.containsIgnoreCase(eventLabel, queryString)) {
                                collection.add(new EventDefInfo(eventName, eventLabel));
                            }
                        }
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("Faield to search event definitions.", e);
        }

        return collection;
    }

    @Override
    public ExternalDocumentCollection<EventDefInfo> getFieldExternalDocuments(ExternalDocumentServiceContext context) {
        SimpleExternalDocumentCollection<EventDefInfo> collection = new SimpleExternalDocumentCollection<>();

        try {
            final Node transNode = context.getContextModel().getNode();
            final String eventName = JcrUtils.getStringProperty(transNode, "pageflow:event", null);

            if (StringUtils.isNotBlank(eventName)) {
                for (Iterator<? extends EventDefInfo> it = searchExternalDocuments(context, null).iterator(); it.hasNext(); ) {
                    EventDefInfo eventDef = it.next();

                    if (StringUtils.equals(eventName, eventDef.getName())) {
                        collection.add(eventDef);
                        break;
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("Failed to get pageflow:event property.", e);
        }

        return collection;
    }

    @Override
    public void setFieldExternalDocuments(ExternalDocumentServiceContext context,
            ExternalDocumentCollection<EventDefInfo> eventDefs) {
        if (eventDefs.getSize() > 0) {
            EventDefInfo eventDef = eventDefs.iterator().next();

            try {
                final Node transNode = context.getContextModel().getNode();
                transNode.setProperty("pageflow:event", eventDef.getName());
            } catch (RepositoryException e) {
                log.error("Failed to set pageflow:event property.", e);
            }
        }
    }

    @Override
    public String getDocumentDescription(ExternalDocumentServiceContext context, EventDefInfo eventDef, Locale locale) {
        return "";
    }

    @Override
    public String getDocumentIconLink(ExternalDocumentServiceContext context, EventDefInfo eventDef, Locale locale) {
        return "skin/images/icons/mail-48.png";
    }

    @Override
    public String getDocumentTitle(ExternalDocumentServiceContext context, EventDefInfo eventDef, Locale locale) {
        return StringUtils.defaultString(eventDef.getLabel(), eventDef.getName());
    }

    private Node findPageFlowDocumentVariantNode(final Node baseNode) throws RepositoryException {
        Node curNode = baseNode;

        while (curNode != null) {
            if (curNode.isNodeType("pageflow:pageflow")) {
                return curNode;
            }

            curNode = curNode.getParent();
        }

        throw new IllegalStateException("Cannot find pageflow:pageflow variant node from " + baseNode.getPath());
    }
}
