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
package org.onehippo.forge.pageflow.hst.sitemapitemhandler;

import java.util.Objects;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.request.SiteMapItemHandlerConfiguration;
import org.hippoecm.hst.core.sitemapitemhandler.AbstractFilterChainAwareHstSiteMapItemHandler;
import org.hippoecm.hst.core.sitemapitemhandler.HstSiteMapItemHandler;
import org.hippoecm.hst.core.sitemapitemhandler.HstSiteMapItemHandlerException;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageFlowControl;
import org.onehippo.forge.pageflow.core.rt.PageState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default generic <code>HstSiteMapItemHandler</code> implementation to integrate with Page Flow module.
 */
public class DefaultPageFlowControlHstSiteMapItemHandler extends AbstractFilterChainAwareHstSiteMapItemHandler {

    private static Logger log = LoggerFactory.getLogger(DefaultPageFlowControlHstSiteMapItemHandler.class);

    public static final String PARAM_ENABLED = "enabled";
    public static final String PARAM_AUTO_REDIRECTION_ENABLED = "auto.redirection.enabled";

    private boolean pageFlowControlSetInServletContext;

    private volatile PageFlowControl pageFlowControl;

    private SiteMapItemHandlerConfiguration siteMapItemHandlerConfiguration;

    @Override
    public void init(ServletContext servletContext, SiteMapItemHandlerConfiguration siteMapItemHandlerConfiguration)
            throws HstSiteMapItemHandlerException {
        super.init(servletContext, siteMapItemHandlerConfiguration);
        this.siteMapItemHandlerConfiguration = siteMapItemHandlerConfiguration;
    }

    @Override
    public ResolvedSiteMapItem process(ResolvedSiteMapItem resolvedSiteMapItem, HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws HstSiteMapItemHandlerException {

        final Boolean enabled = getConfigurationProperty(PARAM_ENABLED, resolvedSiteMapItem, Boolean.class,
                Boolean.TRUE);

        if (BooleanUtils.isFalse(enabled)) {
            return processDefaultResolvedSiteMapItem(request, response, filterChain, resolvedSiteMapItem);
        }

        final PageFlowControl flowControl = getPageFlowControl(request);

        if (flowControl == null) {
            return processDefaultResolvedSiteMapItem(request, response, filterChain, resolvedSiteMapItem);
        }

        if (!pageFlowControlSetInServletContext) {
            request.getServletContext().setAttribute(PageFlowControl.PAGE_FLOW_CONTROL_ATTR_NAME, flowControl);
            pageFlowControlSetInServletContext = true;
        }

        request.setAttribute(PageFlowControl.PAGE_FLOW_CONTROL_ATTR_NAME, flowControl);

        if (RequestContextProvider.get().isPreview()) {
            return processDefaultResolvedSiteMapItem(request, response, filterChain, resolvedSiteMapItem);
        }

        final Boolean autoRedirectionEnabled = getConfigurationProperty(PARAM_AUTO_REDIRECTION_ENABLED,
                resolvedSiteMapItem, Boolean.class, Boolean.TRUE);

        if (BooleanUtils.isFalse(autoRedirectionEnabled)) {
            return processDefaultResolvedSiteMapItem(request, response, filterChain, resolvedSiteMapItem);
        }

        PageFlow pageFlow = getActivePageFlow(request, flowControl);

        if (pageFlow == null) {
            return processDefaultResolvedSiteMapItem(request, response, filterChain, resolvedSiteMapItem);
        }

        final PageState pageState = pageFlow.getPageState();

        if (pageState != null && !isRequestForPageState(request, pageState)) {
            try {
                flowControl.sendRedirect(request, response, pageState);
                return null;
            } catch (Exception e) {
                log.warn("Failed to redirect to the pageState: {}", pageState, e);
            }
        }

        return processDefaultResolvedSiteMapItem(request, response, filterChain, resolvedSiteMapItem);
    }

    /**
     * Return SiteMapItemHandlerConfiguration.
     * @return SiteMapItemHandlerConfiguration
     */
    protected SiteMapItemHandlerConfiguration getSiteMapItemHandlerConfiguration() {
        return siteMapItemHandlerConfiguration;
    }

    /**
     * Get a {@link PageFlowControl} instance for this request.
     * @param request servlet request
     * @return a {@link PageFlowControl} instance for this request
     */
    protected PageFlowControl getPageFlowControl(HttpServletRequest request) {
        PageFlowControl flowControl = pageFlowControl;

        if (flowControl == null) {
            synchronized (this) {
                flowControl = pageFlowControl;

                if (flowControl == null) {
                    flowControl = createPageFlowControl(request);
                    pageFlowControl = flowControl;
                }
            }
        }

        return flowControl;
    }

    /**
     * Create a {@link PageFlowControl} for this request.
     * @param request servlet request
     * @return a {@link PageFlowControl} for this request
     */
    protected PageFlowControl createPageFlowControl(HttpServletRequest request) {
        return new DefaultHstPageFlowControl();
    }

    /**
     * Return an active {@link PageFlow} instance for this request.
     * @param request servlet request
     * @param flowControl {@link PageFlowControl} instance
     * @return an active {@link PageFlow} instance for this request
     */
    protected PageFlow getActivePageFlow(HttpServletRequest request, PageFlowControl flowControl) {
        PageFlow pageFlow = flowControl.getPageFlow(request);

        if (pageFlow != null) {
            if (pageFlow.isComplete()) {
                flowControl.completePageFlow(request, pageFlow);
                pageFlow = flowControl.getPageFlow(request);
            }

            if (!pageFlow.isStarted()) {
                pageFlow.start();
            }
        }

        return pageFlow;
    }

    /**
     * Process the current resolved sitemap item by the default behavior, without any custom behavior from Page Flow Module.
     * @param request servlet request
     * @param response servlet response
     * @param filterChain servlet filter chain
     * @param resolvedSiteMapItem resolved sitemap item
     * @return resolved sitemap item, the same meaning as {@link HstSiteMapItemHandler#process(ResolvedSiteMapItem, HttpServletRequest, HttpServletResponse)}.
     */
    protected ResolvedSiteMapItem processDefaultResolvedSiteMapItem(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain, ResolvedSiteMapItem resolvedSiteMapItem) {
        if (resolvedSiteMapItem == null) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.warn("Failed to filterChain.doFilter(...).", e);
            }
        }

        return resolvedSiteMapItem;
    }

    /**
     * Get the configuration property value on the {@code resolvedSiteMapItem}.
     * @param name configuration property name
     * @param resolvedSiteMapItem resolved sitemap item
     * @param type expected configuration value type
     * @param defaultValue default value
     * @return the configuration property value on the {@code resolvedSiteMapItem}
     */
    protected <T> T getConfigurationProperty(String name, ResolvedSiteMapItem resolvedSiteMapItem, Class<T> type,
            T defaultValue) {
        T value = null;

        try {
            if (siteMapItemHandlerConfiguration != null) {
                if (resolvedSiteMapItem != null) {
                    value = siteMapItemHandlerConfiguration.getProperty(name, resolvedSiteMapItem, type);
                } else {
                    value = siteMapItemHandlerConfiguration.getRawProperty(name, type);
                }
            }
        } catch (Exception e) {
            log.warn("Exception occurred while getting configuration property: '" + name + "'.", e);
        }

        return (value != null) ? value : defaultValue;
    }

    /**
     * Return true if the current {@code request} is for the specific {@code pageState} by comparing the {@code pathInfo}
     * of the {@code request} with the {@code path} of the {@code pageState}.
     * @param request servlet request
     * @param pageState page state
     * @return true if the current {@code request} is for the specific {@code pageState} by comparing the {@code pathInfo}
     * of the {@code request} with the {@code path} of the {@code pageState}
     */
    protected boolean isRequestForPageState(final HttpServletRequest request, final PageState pageState) {
        final String normalizedPagePath = PathUtils.normalizePath(StringUtils.defaultString(pageState.getPath()));
        final String normalizedPathInfo = PathUtils.normalizePath(StringUtils.defaultString(request.getPathInfo()));
        return Objects.equals(normalizedPagePath, normalizedPathInfo);
    }
}
