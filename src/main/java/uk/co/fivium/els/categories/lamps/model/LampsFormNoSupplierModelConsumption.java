package uk.co.fivium.els.categories.lamps.model;

import javax.validation.constraints.NotBlank;
import uk.co.fivium.els.categories.internetlabelling.model.InternetLabellingForm;
import uk.co.fivium.els.model.meta.FieldPrompt;

public class LampsFormNoSupplierModelConsumption extends InternetLabellingForm {

  @FieldPrompt("Energy efficiency class of the application")
  @NotBlank(message = "Select an energy efficiency class")
  private String efficiencyRating;

  @FieldPrompt("What type of label should be generated?")
  @NotBlank(message = "Select what type of label should be generated")
  private String templateType;

  public String getEfficiencyRating() {
    return efficiencyRating;
  }

  public void setEfficiencyRating(String efficiencyRating) {
    this.efficiencyRating = efficiencyRating;
  }

  public String getTemplateType() {
    return templateType;
  }

  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }
}
