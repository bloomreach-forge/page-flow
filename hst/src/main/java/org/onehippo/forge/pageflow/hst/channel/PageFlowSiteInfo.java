/*
 * Copyright 2024 Bloomreach, Inc. (https://www.bloomreach.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onehippo.forge.pageflow.hst.channel;

import org.hippoecm.hst.configuration.HstNodeTypes;
import org.hippoecm.hst.configuration.channel.ChannelInfo;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;

/**
 * Default Page Flow module based <code>ChannelInfo</code> definition,
 * allowing to configure the identifier of the default Page Flow of the channel
 * and the default resource bundle ID(s) (as comma separated string) for the channel.
 */
@FieldGroupList({
        @FieldGroup(
                titleKey = "fields.site",
                value = { "defaultPageFlowId", HstNodeTypes.GENERAL_PROPERTY_DEFAULT_RESOURCE_BUNDLE_ID }
                )
        }
)
public interface PageFlowSiteInfo extends ChannelInfo {

    /**
     * Return the identifier of the default Page Flow of this channel.
     * @return the identifier of the default Page Flow of this channel
     */
    @Parameter(
            name = "defaultPageFlowId",
            defaultValue = "",
            description = "Default Page Flow ID")
    String getDefaultPageFlowId();

    /**
     * Return the default (comma-separated) resource bundle ID(s) of this channel.
     * @return the default (comma-separated) resource bundle ID(s) of this channel
     */
    @Parameter(
            name = HstNodeTypes.GENERAL_PROPERTY_DEFAULT_RESOURCE_BUNDLE_ID,
            defaultValue = "",
            description = "Default Resource Bundle ID(s)")
    String getDefaultResourceBundleIds();

}