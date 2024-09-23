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
package org.onehippo.forge.pageflow.demo.campaign.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;

public class CampaignQuoteComponent extends AbstractCampaignComponent {

    private static final Pattern US_ZIP_PATTERN = Pattern.compile("^\\d{5}$");

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final String zip = StringUtils.trim(request.getParameter("zip"));

        final PageFlow pageFlow = getPageFlow();
        final CampaignModel campaignModel = new CampaignModel();
        campaignModel.setZip(zip);
        pageFlow.setAttribute(CampaignConstants.DEFAULT_MODEL_NAME, campaignModel);

        if (StringUtils.isNotEmpty(zip)) {
            final Matcher m = US_ZIP_PATTERN.matcher(zip);

            if (m.matches()) {
                pageFlow.sendEvent(CampaignConstants.EVENT_START_QUOTE);
            }
        }
    }

}
