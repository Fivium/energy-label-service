<#include '../../layout.ftl'>

<@common.standardProductForm title="Boiler combination heaters">

    <@govukSelect.select path="form.declaredLoadProfile" options=loadProfile/>

    <@govukSelect.select path="form.efficiencyRating" options=efficiencyRating/>
    <@govukSelect.select path="form.waterHeatingEfficiencyRating" options=secondaryEfficiencyRating/>

    <@govukTextInput.textInput path="form.heatOutput"/>

    <@govukTextInput.textInput path="form.soundPowerLevelIndoors"/>

    <@govukRadios.radioYesNo path="form.offPeak"/>
</@common.standardProductForm>
