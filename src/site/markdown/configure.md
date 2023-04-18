
[//]: # (  Copyright 2018 Hippo B.V. (http://www.onehippo.com)  )
[//]: # (  )
[//]: # (  Licensed under the Apache License, Version 2.0 (the "License");  )
[//]: # (  you may not use this file except in compliance with the License.  )
[//]: # (  You may obtain a copy of the License at  )
[//]: # (  )
[//]: # (       http://www.apache.org/licenses/LICENSE-2.0  )
[//]: # (  )
[//]: # (  Unless required by applicable law or agreed to in writing, software  )
[//]: # (  distributed under the License is distributed on an "AS IS" BASIS,  )
[//]: # (  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  )
[//]: # (  See the License for the specific language governing permissions and  )
[//]: # (  limitations under the License.  )

## Configuration

### Introduction

Configurations include two parts:

1. Configure **Page Flow HstSiteMapItemHandler ID** (```defaultpageflowcontrolhandler``` by default)
  for a mount or sitemap item.
1. Configure **Page Flow Definition ID** for a channel, mount or sitemap item.
1. (Optional) Configure **Page Flow Channel Info** for a channel.

**Page Flow HstSiteMapItemHandler ID** (```defaultpageflowcontrolhandler``` by default) must be configured
in either mount level or sitemap item level. So, Page Flow Module can handle automatic initiation, redirection, etc.
for a visitor based on the visitor's Page Flow instance lifecycle.

Also, **Page Flow Definition ID** must be configured in a channel, mount or sitemap item level.
So, Page Flow Module will initialize and manage a Page Flow instance based on the configured Page Flow Definition ID.
\
### Configure **Page Flow HstSiteMapItemHandler ID** (```defaultpageflowcontrolhandler``` by default) for a mount or sitemap item

One ore more [HstSiteMapItemHandler](https://www.onehippo.org/library/concepts/hst-configuration-model/advanced/sitemapitem-handlers.html)s
can be configured in either mount level (```@hst:defaultsitemapitemhandlerids``` property) or sitemap item level (```@hst:sitemapitemhandlerids``` property).

It could be very convenient to configure the ```HstSiteMapItemHandler``` ID value in the mount level
because the same ```HstSiteMapItemHandler```s will be applied to all the resolved sitemap items under the same resolved mount.
So, you don't need to configure the ```HstSiteMapItemHandler``` ID for each sitemap item configuration again and again.
This is extremely useful when you have a channel that serves a Page Flow Module driven request processing.
e.g, "Identity Protection Product Campaign Microsite Channel".

However, if you want to control it in more granular level by having some sitemap items for other page serving,
and having some other sitemap items for Page Flow Module driven request processing, then you might want to configure
the the ```HstSiteMapItemHandler``` ID value in each sitemap item level for some selected sitemap items. e.g, "/campaign1", "/campaign1/_any_", ...
In this case, make sure that you configured the **Page Flow HstSiteMapItemHandler ID** on all the possible sitemap items
for Page Flow Module driven requests.

#### Scenario 1: Configure **Page Flow HstSiteMapItemHandler ID** in Mount Level

Suppose you have an HST Mount configuration at ```/hst:hst/hst:hosts/dev-localhost/localhost/hst:root/campaign1```
which is associated with a channel called "Campaign 1".

If you want the channel to be controlled by the Page Flow module, then simply add
the registered HstSiteMapItemHandler name to the ```@hst:defaultsitemapitemhandlerids``` of the mount configuration like the following:

For example,

```
/campaign1:
  jcr:primaryType: hst:mount
  hst:defaultsitemapitemhandlerids: [defaultpageflowcontrolhandler]
  ...
```

Now, every request coming along to this mount will be controlled by **Page Flow Module**.

#### Scenario 2: Configure **Page Flow HstSiteMapItemHandler ID** in SiteMap Item Level

You can configure the ```@hst:sitemapitemhandlerids``` property on each sitemap item separately in this scenario.
If you set the property on specific sitemap items, any requests onto the sitemap items will be controlled by **Page Flow Module**.
Otherwise, Page Flow module wouldn't do anything for the other sitemap items.

For example,

```
/payment:
  jcr:primaryType: hst:sitemapitem
  hst:sitemapitemhandlerids: [defaultpageflowcontrolhandler]
  ...
```

### Configure **Page Flow Definition ID** for a channel, mount or sitemap item

Once you published a **Page Flow Definition** document like shown in the [Demo Project](demoproject.html),
you can associate the **Page Flow Definition ID** value (the **Flow ID** field value in the editor) to a channel,
a mount or a sitemap item.

The default **PageFlowControl** implementation (```org.onehippo.forge.pageflow.hst.sitemapitemhandler.DefaultHstPageFlowControl```) resolves the
current **Page Flow Definition ID** in the following order:

1. If the current servlet request has a non-blank string attribute by name, ```org.onehippo.forge.pageflow.core.rt.PageFlowControl.PAGE_FLOW_ID_ATTR_NAME```,
   then it is used.
1. Else if the current ```HttpSession``` has a non-blank string attribute by name, ```org.onehippo.forge.pageflow.core.rt.PageFlowControl.PAGE_FLOW_ID_ATTR_NAME```,
   then it is used.
1. Else If the current resolved sitemap item returns a non-blank value for a parameter named ```pageflowid```, it is used.
1. Else if the current resolved mount is associated with a channel, and if the ```ChannelInfo``` class of the channel is type of ```org.onehippo.forge.pageflow.hst.channel.PageFlowSiteInfo```,
   and if the channel info object returns a non-blank value on ```PageFlowSiteInfo#getDefaultPageFlowId()```,
   then it is used.
   If the ```ChannelInfo``` is not null but is not type of ```org.onehippo.forge.pageflow.hst.channel.PageFlowSiteInfo```,
   then the channel property named ```pageflowid``` will be read. If the value is non-blank, it is used.
1. Else if the resolved mount's mount property or normal property named ```pageflowid``` will be read. If not blank, it is used.
1. Otherwise, it will fail to resolve the current **Page Flow Definition ID**, and as a result all the Page Flow Module
driven request processing will fail.

### (Optional) Configure **Page Flow Channel Info** for a channel

When you configure a Page Flow Definition for a dedicated channel, you might also want to configure the Page Flow Definition
through the Channel Setting window like the following:

> ![Page Flow Channel Info](images/flowchninfo.png "Page Flow Channel Info")

So, you can simply copy **Flow ID** field value in the Page Flow Definition document and paste it into the
**Default Page Flow ID** input shown above.

**Page Flow Module** provides a default [Channel Info](https://www.onehippo.org/library/concepts/channels/define-channel-configuration-parameters.html): ```org.onehippo.forge.pageflow.hst.channel.PageFlowSiteInfo```

To use this Channel Info, you can configure your channel configuration like the following:

```
/hst:channel:
  jcr:primaryType: hst:channel
  hst:channelinfoclass: org.onehippo.forge.pageflow.hst.channel.PageFlowSiteInfo
  ...
```

