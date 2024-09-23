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
package org.onehippo.forge.pageflow.demo.campaign;

public class CampaignConstants {

    public static final String DEFAULT_MODEL_NAME = "campaignModel";

    public static final String EVENT_START_QUOTE = "start.quote";
    public static final String EVENT_SINGLE_PLAN_SELECTED = "single.plan.selected";
    public static final String EVENT_FAMILY_PLAN_SELECTED = "family.plan.selected";
    public static final String EVENT_FAMILY_DEPENDENTS_FILLED = "family.dependents.filled";
    public static final String EVENT_APPLICATION_REVIEWED = "application.reviewed";
    public static final String EVENT_APPLICATION_SUBMITTED = "application.submitted";
    public static final String EVENT_PAYMENT_ACCEPTED = "payment.accepted";
    public static final String EVENT_PAYMENT_REJECTED = "payment.rejected";
    public static final String EVENT_CANCEL_REQUESTED = "cancel.requested";

    private CampaignConstants() {
    }
}
