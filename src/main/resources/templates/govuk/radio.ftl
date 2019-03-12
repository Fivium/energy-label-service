<#import '/spring.ftl' as spring>
<#import 'fieldset.ftl' as radioFieldset>
<#import 'details.ftl' as furtherGuidance>

<#--GOVUK Radio-->
<#--https://design-system.service.gov.uk/components/radios/-->
<#--If hidden content exists within a list of dynamic options then need to split the radio macros in your form template-->
<#--If using a split radio group then you need to wrap the radio group in a conditional class and data-module div in the form template so that the show/hide content works across radio items-->
<#--Setting splitList to true against each radio item in a split group will ignore the above mentioned div in the macro.-->
<#--If splitList is false the radio group is considered to not have hidden content and the radio grouping will work as standard.-->
<#macro radio path radioItems label="" inline=true hiddenContentId="" splitList=false>
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local errorList=spring.status.errorMessages>
  <#local fieldName=spring.status.expression>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <@radioFieldset.fieldset legendHeading=label legendHeadingClass="govuk-fieldset__legend--m" mandatory=mandatory>
      <#if hasError>
        <span id="${id}-error" class="govuk-error-message">
          ${errorList?join(" ")}
        </span>
      </#if>
      <#if splitList=false>
        <div class="govuk-radios govuk-radios--conditional <#if inline>govuk-radios--inline</#if>" data-module="radios">
      </#if>
        <#list radioItems?keys as item>
          <#assign isSelected = spring.stringStatusValue == item>
          <div class="govuk-radios__item">
          <input class="govuk-radios__input" id="${id}-${item}" name="${fieldName}" type="radio" value="${item}" <#if isSelected>checked="checked"</#if> <#if hiddenContentId?has_content>data-aria-controls="${hiddenContentId}"</#if>>
            <label class="govuk-label govuk-radios__label" for="${id}-${item}">
              ${radioItems[item]}
            </label>
          </div>
        </#list>
        <#if hiddenContentId?has_content>
          <div class="govuk-radios__conditional govuk-radios__conditional--hidden" id="${hiddenContentId}">
            <#nested/>
          </div>
        </#if>
      <#if splitList=false>
        </div>
      </#if>
    </@radioFieldset.fieldset>
  </div>
</#macro>

<#macro radioYesNo path label="" inline=true hiddenQuestionsWithYesSelected=false hiddenQuestionsWithNoSelected=false>
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>
  <#local errorList=spring.status.errorMessages>
  <#local fieldName=spring.status.expression>
  <#local displayValue=spring.status.displayValue>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <@radioFieldset.fieldset legendHeading=label legendHeadingClass="govuk-fieldset__legend--m" mandatory=true>
      <#if hasError>
        <span id="${id}-error" class="govuk-error-message">
          ${errorList?join(" ")}
        </span>
      </#if>
      <div class="govuk-radios govuk-radios--conditional <#if inline>govuk-radios--inline</#if>" data-module="radios">
        <div class="govuk-radios__item">
          <input class="govuk-radios__input" id="${id}" name="${fieldName}" type="radio" value="true"<#if displayValue == "true"> checked="checked"</#if> <#if hiddenQuestionsWithYesSelected=true>data-aria-controls="hidden-content-with-yes-selected-${id}"</#if>>
          <label class="govuk-label govuk-radios__label" for="${id}">
            Yes
          </label>
        </div>
        <#if hiddenQuestionsWithYesSelected=true>
          <div class="govuk-radios__conditional govuk-radios__conditional--hidden" id="hidden-content-with-yes-selected-${id}">
            <#nested/>
          </div>
        </#if>
        <div class="govuk-radios__item">
          <input class="govuk-radios__input" id="no-label-${id}" name="${fieldName}" type="radio" value="false"<#if displayValue == "false"> checked="checked"</#if> <#if hiddenQuestionsWithNoSelected=true>data-aria-controls="hidden-content-with-no-selected-${id}"</#if>>
          <label class="govuk-label govuk-radios__label" for="no-label-${id}">
            No
          </label>
        </div>
        <#if hiddenQuestionsWithNoSelected=true>
          <div class="govuk-radios__conditional govuk-radios__conditional--hidden" id="hidden-content-with-no-selected-${id}">
            <#nested/>
          </div>
        </#if>
      </div>
    </@radioFieldset.fieldset>
  </div>
</#macro>