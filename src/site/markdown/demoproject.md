
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

## Demo Project

### Build and Run

You can build and install the module locally first in the project root folder.

```bash
$ mvn clean install
```

And you can build and run the Demo project:

```bash
$ cd demo
$ mvn clean verify && mvn -Pcargo.run
```

For testing, visit http://localhost:8080/site/campaign1.

### Demo Page Flow Definition

In the demo project, the Page Flow definition for the channel (http://localhost:8080/site/campaign1) is defined
in the following document:

> ![Demo Page Flow Definition 1](images/demoflowdef1.png "Demo Page Flow Definition 1")

The above Page Flow Definition document defines all the page states, events and transitions in the document editor.

It is actually represents the following Finite State Machine diagram:

> ![Demo Finite State Machine 1](images/demoflowfsm1.png "Demo Finite State Machine 1")

- Visitor starts the journey by visiting the **Landing Page** page state.
- On "start.quote" event (usually an event is triggerd by a user's action in the step page), it would
be transitioned to **Plan Selection** page state.
- On "family.plan.selected" event, it would be transitioned to **Dependents Entry** page state, which would be
  transitioned to **Application Entry** page state afterward.
  Or on "single.plan.selected" event, it would be transitioned to **Application Entry** page state directly.
- On "application.submitted" event, it would be transitioned to **Review** page state.
- On "application.reviewed" event, it would be transitioned to **Payment** page state.
- On "payment.accepted" event, it would be transitioned to **Confirmation** page state, which leads to **Final End** state.
  Or on "payment.rejected" event, it would stay in the **Payment** page state.
- **Application Entry**, **Review** and **Payment** page states could be transitioned back to the **Landing Page** page state
  on "cancel.requested" event.

### Example Page Flow Implementation in Demo Project

The demo project includes multiple pages and [HST Components](https://github.com/bloomreach-forge/page-flow/tree/master/demo/site/src/main/java/org/onehippo/forge/pageflow/demo/campaign/components)
depending on the Page Flow Definition shown above.
When you visit http://localhost:8080/site/campaign1, you may start a Page Flow instance with the following first step:

#### 1. Page State: Landing Page

In this page state, when you click on "Start!" button, it posts the form to the HstComponent's ```#doAction()```
method which could trigger a page transition by sending an event.

> ![Page Step 1](images/demostep1.png "Page Step 1")

#### 2. Page State: Plan Selection

In this page state, when you click on "Next" button after selecting a plan, it posts the form to the HstComponent's
```#doAction()``` method which could trigger a page transition by sending an event.

> ![Page Step 2](images/demostep2.png "Page Step 2")

#### 3. Page State: Enter Dependents

In this page state, when you click on "Next" button after adding more than one dependent's first name and last name,
it posts the form to the HstComponent's ```#doAction()``` method which could trigger a page transition by sending an event.

> ![Page Step 3](images/demostep3.png "Page Step 3")

#### 4. Page State: Application Form

In this page state, when you click on "Next" button after entering application information,
it posts the form to the HstComponent's ```#doAction()``` method which could trigger a page transition by sending an event.
If you didn't enter any required inputs, then it wouldn't trigger a transition, resulting in staying in the same page.

When you click on "Cancel" button, the HstComponent will send the "cancel.requested" event, which results in going back to
the first page state.

> ![Page Step 4](images/demostep4.png "Page Step 4")

#### 5. Page State: Review

In this page state, when you click on "Next" button after reviewing the inputs,
it posts the form to the HstComponent's ```#doAction()``` method which could trigger a page transition by sending an event.

When you click on "Cancel" button, the HstComponent will send the "cancel.requested" event, which results in going back to
the first page state.

The "Next from client-side" button is placed for demonstration purpose. The client-side JavaScript action on the button's click,
makes an AJAX call on an [HST Resource URL](https://www.onehippo.org/library/concepts/component-development/serve-a-dynamic-resource.html)
to send an event which could trigger a page flow transition, and make a page redirection from the client-side based on the result.

> ![Page Step 5](images/demostep5.png "Page Step 5")

#### 6. Page State: Payment Form

In this page state, when you click on "Pay Now!" button after entering the inputs,
it posts the form to the HstComponent's ```#doAction()``` method which could trigger a page transition by sending an event.

When you click on "Cancel" button, the HstComponent will send the "cancel.requested" event, which results in going back to
the first page state.

> ![Page Step 6](images/demostep6.png "Page Step 6")

#### 7. Page State: Confirmation

In this page state, you completed the Page Flow instance!

> ![Page Step 7](images/demostep7.png "Page Step 7")
