<#include "../include/imports.ftl">

<div>
  <h1><@fmt.message key="campaign.title" var="title"/>${title?html}</h1>
  <p><@fmt.message key="campaign.text" var="text"/>${text?html}</p>
  <#if !hstRequest.requestContext.channelManagerPreviewRequest>
    <p>
      [This text can be edited
      <a href="http://localhost:8080/cms/?1&path=/content/documents/administration/labels/campaign-general" target="_blank">here</a>.]
    </p>
  </#if>
</div>
<div>
  <@hst.include ref="main-container"/>
</div>