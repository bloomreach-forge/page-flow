
[//]: # (  Copyright 2023 Bloomreach, Inc. (https://www.bloomreach.com/)  )
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

## Page Flow Module

### Introduction

**Page Flow Module** provides a generic way to define and manage states of pages through Page Flow Documents
in Hippo CMS authoring application, and a flexible way to implement *Page Flow Interactions* in Hippo CMS delivery-tier
application.

What does it mean by *Page Flow Interactions*?

It is an application interaction style that comprises multiple steps for users to navigate, view or fill in the forms
to complete a business-meaningful action.

Suppose a visitor is coming from a link in a marketing campagin E-Mail message. The link can possibly lead the visitor
to the landing page first. If the visitor feels okay with the landing page content, then the visitor might want to
follow the next page link. The navigation might lead to multiple step pages such as selecting a product, entering
visitor's information, and so on.

**Page Flow Module** provides a *solution* to manage and implement this kind of *multi-step-pages* user interactions
very easily in Hipppo CMS.

### Why Page Flow Module?

Why should Page Flow Module be introduced then? Can developers not use
[Enterprise Forms](https://www.onehippo.org/library/enterprise/enterprise-features/enterprise-forms/enterprise-forms.html)
or something similar?

If you asked this as well, that's really a good question!

You can use [Enterprise Forms](https://www.onehippo.org/library/enterprise/enterprise-features/enterprise-forms/enterprise-forms.html)
or something similar for that kind of *multi-step* interactions, but that's not *multi-step-pages* actually
because that kind of *multi-step* interaction solutions are working in an ```HstComponent``` window level,
not in multiple, totally-separated HST pages.

The *multi-step* interaction solution in an ```HstComponent``` window level has the following disadvantages:

- It is not possible to use a different HST page layout in a step page because the solution works in an ```HstComponent``` window level.
- It is not so flexible for developers to implement each step page because all the interactions must be configured or implemented
in the specific *multi-step* interaction framework such as [Enterprise Forms](https://www.onehippo.org/library/enterprise/enterprise-features/enterprise-forms/enterprise-forms.html).
- Business users cannot configure different
[Relevance](https://www.onehippo.org/library/enterprise/enterprise-features/targeting/targeting.html)
personalization on each step HST page separately (e.g, setting a different banner above the form in a step)
because the solution resides only in single ```HstComponent``` window.
- Business users cannot configure a conversion goal in a specific step HST page (e.g, final application form page step)
for an [Experiment](https://www.onehippo.org/library/end-user-manual/experiments/experiments.html)
because the solution resides only in single ```HstComponent``` window.

**Page Flow Module** provides solutions for all those problems listed above.

### Features

- Generic Page Flow Definitions as documents in Hippo CMS authoring application.
- Generic Page Flow Handling Runtime, which uses [Spring Statemachine](https://projects.spring.io/spring-statemachine/) library under the hood.
- Generic HST SiteMap Item Handler for automatic Page Flow resolution and redirection.
- Generic ChannelInfo interface for easy creation of campaign channel.
- Generic JCR Observation Event Listener to refresh Page Flow definitions automatically.

### Module Overview

#### **pageflow-core** JAR module

- Generic APIs for both Page Flow definitions and web application runtime,
  leverating [Spring Statemachine](https://projects.spring.io/spring-statemachine/) library under the hood.

#### **pageflow-hst** JAR module

- HST-2 specific ```PageFlowControl``` implementation
- HST-2 specific ```SiteMapItemHandler``` implementation,
```org.onehippo.forge.pageflow.hst.sitemapitemhandler.PageFlowControlHstSiteMapItemHandler```,
handling active ```PageFlow``` and automatic redirection based on the states of ```PageFlow```.
- Default ```ChannelInfo``` interface for Channel Manager.
- Generic JCR Observation Event Listener implementation to invalidate cached Page Flow definitions.

#### **pageflow-repository** JAR module

- Page Flow Module specific JCR namespace definition
- Page Flow Definition document namespace definition

#### **pageflow-cms** JAR module

- Page Flow Definition document editor plugins
