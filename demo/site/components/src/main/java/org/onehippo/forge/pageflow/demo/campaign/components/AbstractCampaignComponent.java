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
package org.onehippo.forge.pageflow.demo.campaign.components;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.forge.pageflow.core.rt.ErrorItem;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.PageFlowControl;
import org.onehippo.forge.pageflow.core.rt.impl.DefaultErrorItem;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCampaignComponent extends BaseHstComponent {

    private static Logger log = LoggerFactory.getLogger(AbstractCampaignComponent.class);

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        setPageFlowRequestAttributes(request, response);
    }

    protected PageFlow getPageFlow() {
        final HstRequestContext requestContext = RequestContextProvider.get();
        final PageFlowControl flowControl = PageFlowControl.getDefault(requestContext.getServletRequest());
        return flowControl.getPageFlow(requestContext.getServletRequest());
    }

    protected ErrorItem createErrorItem(String code) {
        return createErrorItem(code, null);
    }

    protected ErrorItem createErrorItem(String code, Object[] arguments) {
        return createErrorItem(code, arguments, null);
    }

    protected ErrorItem createErrorItem(String code, Object[] arguments, String defaultMessage) {
        final HstRequestContext requestContext = RequestContextProvider.get();
        ResourceBundle resourceBundle = null;

        final LocalizationContext localizationContext = (LocalizationContext) Config
                .get(requestContext.getServletRequest(), Config.FMT_LOCALIZATION_CONTEXT);
        if (localizationContext != null) {
            resourceBundle = localizationContext.getResourceBundle();
        }

        final Locale locale = requestContext.getPreferredLocale();

        return new DefaultErrorItem(resourceBundle, locale, code, arguments, defaultMessage);
    }

    private void setPageFlowRequestAttributes(HstRequest request, HstResponse response) throws HstComponentException {
        final PageFlow pageFlow = getPageFlow();
        request.setAttribute("pageFlow", pageFlow);

        final CampaignModel campaignModel = (CampaignModel) pageFlow.getAttribute(CampaignConstants.DEFAULT_MODEL_NAME);

        if (campaignModel != null) {
            request.setAttribute("campaignModel", campaignModel);
        }
    }
}
