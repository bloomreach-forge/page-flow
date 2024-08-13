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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.pageflow.core.rt.Errors;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.core.rt.impl.DefaultErrors;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;

public class CampaignApplicationFormComponent extends AbstractCampaignComponent {

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final String action = request.getParameter("action");

        final PageFlow pageFlow = getPageFlow();
        pageFlow.getPageState().clearAllErrors();

        if (StringUtils.equalsIgnoreCase("cancel", action)) {
            pageFlow.sendEvent(CampaignConstants.EVENT_CANCEL_REQUESTED);
            return;
        }

        final CampaignModel campaignModel = (CampaignModel) pageFlow.getAttribute(CampaignConstants.DEFAULT_MODEL_NAME);

        final Map<String, Errors> errorsMap = readForm(request, campaignModel);

        if (!errorsMap.isEmpty()) {
            pageFlow.getPageState().addAllErrors(errorsMap);
            return;
        }

        final boolean submitted = submitApplication(campaignModel);

        if (submitted) {
            pageFlow.sendEvent(CampaignConstants.EVENT_APPLICATION_SUBMITTED);
        }
    }

    private Map<String, Errors> readForm(HstRequest request, CampaignModel campaignModel) {
        Map<String, Errors> errorsMap = new HashMap<>();

        final String email = StringUtils.trim(request.getParameter("email"));

        if (StringUtils.isEmpty(email) || !StringUtils.contains(email, "@")) {
            errorsMap.put("email", new DefaultErrors(createErrorItem("email.invalid")));
        } else {
            campaignModel.setEmail(email);
        }

        final String firstName = StringUtils.trim(request.getParameter("firstName"));

        if (StringUtils.isEmpty(firstName)) {
            errorsMap.put("firstName", new DefaultErrors(createErrorItem("firstName.invalid")));
        } else {
            campaignModel.setFirstName(firstName);
        }

        final String lastName = StringUtils.trim(request.getParameter("lastName"));

        if (StringUtils.isEmpty(lastName)) {
            errorsMap.put("lastName", new DefaultErrors(createErrorItem("lastName.invalid")));
        } else {
            campaignModel.setLastName(lastName);
        }

        final String phone = StringUtils.trim(request.getParameter("phone"));

        if (StringUtils.isEmpty(phone)) {
            errorsMap.put("phone", new DefaultErrors(createErrorItem("phone.invalid")));
        } else {
            campaignModel.setPhone(phone);
        }

        final String address = StringUtils.trim(request.getParameter("address"));

        if (StringUtils.isEmpty(address)) {
            errorsMap.put("address", new DefaultErrors(createErrorItem("address.invalid")));
        } else {
            campaignModel.setAddress(address);
        }

        final String city = StringUtils.trim(request.getParameter("city"));

        if (StringUtils.isEmpty(city)) {
            errorsMap.put("city", new DefaultErrors(createErrorItem("city.invalid")));
        } else {
            campaignModel.setCity(city);
        }

        final String state = StringUtils.trim(request.getParameter("state"));

        if (StringUtils.isEmpty(state)) {
            errorsMap.put("state", new DefaultErrors(createErrorItem("state.invalid")));
        } else {
            campaignModel.setState(state);
        }

        return errorsMap;
    }

    private boolean submitApplication(CampaignModel campaignModel) {
        // Suppose you submit this application to a backend service.
        return true;
    }
}
