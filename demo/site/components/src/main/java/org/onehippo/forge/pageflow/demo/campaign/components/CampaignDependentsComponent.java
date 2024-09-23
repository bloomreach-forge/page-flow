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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;
import org.onehippo.forge.pageflow.demo.campaign.model.Dependent;

public class CampaignDependentsComponent extends AbstractCampaignComponent {

    private static final int MAX_DEPENDENTS = 7;

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        request.setAttribute("maxDependents", MAX_DEPENDENTS);
    }

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final PageFlow pageFlow = getPageFlow();

        final CampaignModel campaignModel = (CampaignModel) pageFlow.getAttribute(CampaignConstants.DEFAULT_MODEL_NAME);
        final List<Dependent> dependents = new ArrayList<>();

        for (int i = 1; i <= MAX_DEPENDENTS; i++) {
            final String firstName = StringUtils.trim(request.getParameter("dependentFirstName" + i));
            final String lastName = StringUtils.trim(request.getParameter("dependentLastName" + i));

            if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
                Dependent dependent = new Dependent(firstName, lastName);
                dependents.add(dependent);
            }
        }

        if (!dependents.isEmpty()) {
            campaignModel.setDependents(dependents);
            pageFlow.sendEvent(CampaignConstants.EVENT_FAMILY_DEPENDENTS_FILLED);
        }
    }

}
