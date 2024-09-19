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

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.pageflow.core.rt.PageFlow;
import org.onehippo.forge.pageflow.demo.campaign.CampaignConstants;
import org.onehippo.forge.pageflow.demo.campaign.model.CampaignModel;

public class CampaignPaymentFormComponent extends AbstractCampaignComponent {

    @Override
    public void doAction(HstRequest request, HstResponse response) throws HstComponentException {
        final String action = request.getParameter("action");

        final PageFlow pageFlow = getPageFlow();
        final CampaignModel campaignModel = (CampaignModel) pageFlow.getAttribute(CampaignConstants.DEFAULT_MODEL_NAME);

        if (StringUtils.equalsIgnoreCase("cancel", action)) {
            pageFlow.sendEvent(CampaignConstants.EVENT_CANCEL_REQUESTED);
            return;
        }

        final boolean validated = validateInputs(request, campaignModel);

        if (validated) {
            final boolean processed = processCreditCardPayment(campaignModel);

            if (processed) {
                pageFlow.sendEvent(CampaignConstants.EVENT_PAYMENT_ACCEPTED);
            } else {
                pageFlow.sendEvent(CampaignConstants.EVENT_PAYMENT_REJECTED);
            }
        }
    }

    private boolean validateInputs(HstRequest request, CampaignModel campaignModel) {
        final String cardNumber = StringUtils.trim(request.getParameter("cardnum"));
        if (StringUtils.isEmpty(cardNumber)) {
            return false;
        }
        campaignModel.setCardNumber(cardNumber);

        final String cardType = StringUtils.trim(request.getParameter("cardtype"));
        if (StringUtils.isEmpty(cardType)) {
            return false;
        }
        campaignModel.setCardType(cardType);

        final boolean acknowledged = BooleanUtils.toBoolean(request.getParameter("acknowledged"));
        if (!acknowledged) {
            return false;
        }
        campaignModel.setAcknowledged(acknowledged);

        return true;
    }

    private boolean processCreditCardPayment(CampaignModel campaignModel) {
        // Suppose you submit this application to a backend service.
        return true;
    }
}
