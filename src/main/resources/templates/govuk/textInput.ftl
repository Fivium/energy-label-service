<#import '/spring.ftl' as spring>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Input-->
<#--https://design-system.service.gov.uk/components/text-input/-->
<#macro textInput path label="" inputWidth="govuk-input--width-100">
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local mandatory=((validation[spring.status.path].mandatory)!false)>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <label class="govuk-label" for="${id}">
      ${label}
    </label>

    <#if hasError>
      <span id="${id}-error" class="govuk-error-message">
      <#list spring.status.errorMessages as errorMessage>
        ${errorMessage}<br/>
      </#list>
      </span>
    </#if>
    <#--inputWidth e.g. Fixed Width = govuk-input--width-50-->
    <#--Fluid Width = govuk-!-width-two-thirds-->
    <input class="govuk-input <#if hasError>govuk-input--error </#if> ${inputWidth}" id="${id}" name="${spring.status.expression}" type="text" value="${spring.stringStatusValue}">
  </div>
</#macro>