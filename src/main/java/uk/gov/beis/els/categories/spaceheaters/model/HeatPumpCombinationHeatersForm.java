package uk.gov.beis.els.categories.spaceheaters.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import uk.gov.beis.els.categories.common.StandardTemplateForm50Char;
import uk.gov.beis.els.categories.internetlabelling.model.InternetLabellingGroup;
import uk.gov.beis.els.model.meta.DualModeField;
import uk.gov.beis.els.model.meta.FieldPrompt;
import uk.gov.beis.els.model.meta.StaticProductText;

@StaticProductText("You must display the label at the point of sale so that it’s easy to see and clearly related to the product. It must be at least 105mm x 200mm when printed.")
public class HeatPumpCombinationHeatersForm extends StandardTemplateForm50Char {

  @FieldPrompt("Declared load profile")
  @NotBlank(message = "Select a declared load profile")
  private String declaredLoadProfile;

  @FieldPrompt("The seasonal space heating energy efficiency class")
  @NotBlank(message = "Select an energy efficiency indicator", groups = {Default.class, InternetLabellingGroup.class})
  @DualModeField
  private String spaceHeatingEfficiencyRating;

  @FieldPrompt("The water heating energy efficiency class")
  @NotBlank(message = "Select a water heating energy efficiency indicator")
  private String waterHeatingEfficiencyRating;

  @FieldPrompt("Colder climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for colder climate conditions, up to 2 digits long")
  private String colderHeatOutput;

  @FieldPrompt("Average climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for average climate conditions, up to 2 digits long")
  private String averageHeatOutput;

  @FieldPrompt("Warmer climate conditions")
  @Digits(integer = 2, fraction = 0, message = "Enter the rated heat output for warmer climate conditions, up to 2 digits long")
  private String warmerHeatOutput;

  // TODO Create Digits variant which allows optional. Auto set (optional) in prompt
  @FieldPrompt("Sound power level, indoors dB (optional)")
  @Pattern(regexp = "[0-9]{0,2}", message = "Enter the indoors sound power level, up to 2 digits long")
  private String soundPowerLevelIndoors;

  @FieldPrompt("Sound power level, outdoors dB")
  @Digits(integer = 2, fraction = 0, message = "Enter the outdoors sound power level, up to 2 digits long")
  private String soundPowerLevelOutdoors;

  @FieldPrompt("Can the heater be set to work only during off-peak hours?")
  @NotNull(message = "Specify if off-peak operation is supported")
  private Boolean offPeak;

  public String getDeclaredLoadProfile() {
    return declaredLoadProfile;
  }

  public void setDeclaredLoadProfile(String declaredLoadProfile) {
    this.declaredLoadProfile = declaredLoadProfile;
  }

  public String getSpaceHeatingEfficiencyRating() {
    return spaceHeatingEfficiencyRating;
  }

  public void setSpaceHeatingEfficiencyRating(String spaceHeatingEfficiencyRating) {
    this.spaceHeatingEfficiencyRating = spaceHeatingEfficiencyRating;
  }

  public String getWaterHeatingEfficiencyRating() {
    return waterHeatingEfficiencyRating;
  }

  public void setWaterHeatingEfficiencyRating(String waterHeatingEfficiencyRating) {
    this.waterHeatingEfficiencyRating = waterHeatingEfficiencyRating;
  }

  public String getColderHeatOutput() {
    return colderHeatOutput;
  }

  public void setColderHeatOutput(String colderHeatOutput) {
    this.colderHeatOutput = colderHeatOutput;
  }

  public String getAverageHeatOutput() {
    return averageHeatOutput;
  }

  public void setAverageHeatOutput(String averageHeatOutput) {
    this.averageHeatOutput = averageHeatOutput;
  }

  public String getWarmerHeatOutput() {
    return warmerHeatOutput;
  }

  public void setWarmerHeatOutput(String warmerHeatOutput) {
    this.warmerHeatOutput = warmerHeatOutput;
  }

  public String getSoundPowerLevelIndoors() {
    return soundPowerLevelIndoors;
  }

  public void setSoundPowerLevelIndoors(String soundPowerLevelIndoors) {
    this.soundPowerLevelIndoors = soundPowerLevelIndoors;
  }

  public String getSoundPowerLevelOutdoors() {
    return soundPowerLevelOutdoors;
  }

  public void setSoundPowerLevelOutdoors(String soundPowerLevelOutdoors) {
    this.soundPowerLevelOutdoors = soundPowerLevelOutdoors;
  }

  public Boolean getOffPeak() {
    return offPeak;
  }

  public void setOffPeak(Boolean offPeak) {
    this.offPeak = offPeak;
  }
}
