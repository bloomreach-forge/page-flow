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

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;

public class CampaignPlanComponent extends AbstractCampaignComponent {

    private static final String PLAN_SINGLE = "single";
    private static final String PLAN_FAMILY = "family";

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final String planName = StringUtils.trim(request.getParameter("plan"));

        final PageFlow pageFlow = getPageFlow();
        final CampaignModel campaignModel = (CampaignModel) pageFlow.getAttribute(CampaignConstants.DEFAULT_MODEL_NAME);
        campaignModel.setPlanName(planName);

        if (StringUtils.equals(PLAN_SINGLE, planName)) {
            pageFlow.sendEvent(CampaignConstants.EVENT_SINGLE_PLAN_SELECTED);
        } else if (StringUtils.equals(PLAN_FAMILY, planName)) {
            pageFlow.sendEvent(CampaignConstants.EVENT_FAMILY_PLAN_SELECTED);
        }
    }

}
