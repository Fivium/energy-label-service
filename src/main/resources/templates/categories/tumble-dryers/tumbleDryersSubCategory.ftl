<#include '../../layout.ftl'>

<#assign title="What type of tumble dryer do you need a label for?">

<@defaultPage pageTitle=title>
  <@form.govukForm submitUrl>

    <@govukRadios.radio path="form.subCategory" radioItems=subCategories label=title legendHeadingClass="govuk-fieldset__legend--xl"/>

    <@govukButton.button buttonText="Continue" buttonClass="govuk-button"/>
  </@form.govukForm>

</@defaultPage>