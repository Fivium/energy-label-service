package uk.co.fivium.els.categories.lamps.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.lamps.model.LampSubCategory;
import uk.co.fivium.els.categories.lamps.model.LampSubCategoryForm;
import uk.co.fivium.els.categories.lamps.model.LampsForm;
import uk.co.fivium.els.categories.lamps.model.LampsFormNoSupplierModel;
import uk.co.fivium.els.categories.lamps.model.LampsFormNoSupplierModelConsumption;
import uk.co.fivium.els.categories.lamps.model.TemplateType;
import uk.co.fivium.els.categories.lamps.service.LampsService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/lamps")
public class LampsController {

  private final PdfRenderer pdfRenderer;
  private final LampsService lampsService;

  @Autowired
  public LampsController(PdfRenderer pdfRenderer, LampsService lampsService) {
    this.pdfRenderer = pdfRenderer;
    this.lampsService = lampsService;
  }

  @GetMapping("/")
  public ModelAndView renderLampSubCategories(@ModelAttribute("form") LampSubCategoryForm form) {
    return getSubCategory();
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleLampSubCategoriesSubmit(@Valid @ModelAttribute("form") LampSubCategoryForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSubCategory();
    }
    else {
      LampSubCategory subCategory = LampSubCategory.valueOf(form.getSubCategory());
      return new ModelAndView("redirect:" + subCategory.getNextSateUrl());
    }
  }

  private ModelAndView getSubCategory() {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsSubCategory");
    modelAndView.addObject("subCategories",
        Arrays.stream(LampSubCategory.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, LampSubCategory::getDisplayName))
    );
    return modelAndView;
  }

  @GetMapping("/lamps")
  public ModelAndView renderLamps(@ModelAttribute("form") LampsForm form) {
    return getLamps();
  }

  @PostMapping("/lamps")
  @ResponseBody
  public Object handleLampsSubmit(@Valid @ModelAttribute("form") LampsForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getLamps();
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  @GetMapping("/lamps-excluding-name-model")
  public ModelAndView renderLampsExNameModel(@ModelAttribute("form") LampsFormNoSupplierModel form) {
    return getLampsExNameModel();
  }

  @PostMapping("/lamps-excluding-name-model")
  @ResponseBody
  public Object handleLampsExNameModelSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModel form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModel();
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  @GetMapping("/lamps-excluding-name-model-consumption")
  public ModelAndView renderLampsExNameModelConsumption(@ModelAttribute("form") LampsFormNoSupplierModelConsumption form) {
    return getLampsExNameModelConsumption();
  }

  @PostMapping("/lamps-excluding-name-model-consumption")
  @ResponseBody
  public Object handleLampsExNameModelConsumptionSubmit(@Valid @ModelAttribute("form") LampsFormNoSupplierModelConsumption form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getLampsExNameModelConsumption();
    }
    else {
      Resource pdf = pdfRenderer.render(lampsService.generateHtml(form, LampsService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "lamps-label.pdf");
    }
  }

  private ModelAndView getLamps() {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lamps");
    addCommonObjects(modelAndView, ReverseRouter.route(on(LampsController.class).renderLamps(null)));
    return modelAndView;
  }

  private ModelAndView getLampsExNameModel() {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsExcludingNameModel");
    addCommonObjects(modelAndView, ReverseRouter.route(on(LampsController.class).renderLampsExNameModel(null)));
    return modelAndView;
  }

  private ModelAndView getLampsExNameModelConsumption() {
    ModelAndView modelAndView = new ModelAndView("categories/lamps/lampsExcludingNameModelConsumption");
    addCommonObjects(modelAndView, ReverseRouter.route(on(LampsController.class).renderLampsExNameModelConsumption(null)));
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, String submitUrl) {
    RatingClassRange efficiencyRatingRange = LampsService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    modelAndView.addObject("templateType",
        Arrays.stream(TemplateType.values())
            .collect(StreamUtils.toLinkedHashMap(Enum::name, TemplateType::getDisplayName))
    );
    modelAndView.addObject("submitUrl", submitUrl);
  }

}