package uk.co.fivium.els.model;

import javax.validation.constraints.NotBlank;

public class ProductCategoryForm {

  @NotBlank
  private String category;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}