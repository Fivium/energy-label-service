package uk.gov.beis.els.categories.ventilationunits.model;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.google.common.collect.ImmutableList;
import java.util.List;
import uk.gov.beis.els.categories.common.Category;
import uk.gov.beis.els.categories.common.CategoryItem;
import uk.gov.beis.els.categories.ventilationunits.controller.VentilationUnitsController;
import uk.gov.beis.els.mvc.ReverseRouter;

public class VentilationUnitCategory implements Category {
  // TODO these could be beans defined in a configuration class
  public static final Category GET = new VentilationUnitCategory();

  private static List<CategoryItem> subCategories = new ImmutableList.Builder<CategoryItem>()
      .add(new CategoryItem(
          "UNIDIRECTIONAL_VENTILATION_UNITS",
          "Unidirectional ventilation units",
          ReverseRouter.route(on(VentilationUnitsController.class).renderUnidirectionalVentilationUnits(null))))
      .add(new CategoryItem(
          "BIDIRECTIONAL_VENTILATION_UNITS",
          "Bidirectional ventilation units",
          ReverseRouter.route(on(VentilationUnitsController.class).renderBidirectionalVentilationUnits(null))))
      .build();

  private VentilationUnitCategory(){}

  @Override
  public String getCategoryQuestionText() {
    return "What type of ventilation unit do you need a label for?";
  }

  @Override
  public String getNoSelectionErrorMessage() {
    return "Select a type of ventilation unit";
  }

  @Override
  public String getCommonProductGuidanceText() {
    return "You must display the label so that it’s easy to see and clearly related to the product. It must be at least 75mm x 150mm when printed.";
  }

  @Override
  public List<CategoryItem> getCategoryItems() {
    return subCategories;
  }

}
