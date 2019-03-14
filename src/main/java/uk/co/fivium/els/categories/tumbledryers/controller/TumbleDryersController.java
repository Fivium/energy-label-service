package uk.co.fivium.els.categories.tumbledryers.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.co.fivium.els.categories.tumbledryers.model.CondenserTumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.model.GasFiredTumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryerSubCategory;
import uk.co.fivium.els.categories.tumbledryers.model.TumbleDryerSubCategoryForm;
import uk.co.fivium.els.categories.tumbledryers.model.AirVentedTumbleDryersForm;
import uk.co.fivium.els.categories.tumbledryers.service.TumbleDryersService;
import uk.co.fivium.els.model.RatingClassRange;
import uk.co.fivium.els.mvc.ReverseRouter;
import uk.co.fivium.els.renderer.PdfRenderer;
import uk.co.fivium.els.service.BreadcrumbService;
import uk.co.fivium.els.util.ControllerUtils;
import uk.co.fivium.els.util.StreamUtils;

@Controller
@RequestMapping("/categories/tumble-dryers")
public class TumbleDryersController {

  private final PdfRenderer pdfRenderer;
  private final TumbleDryersService tumbleDryersService;
  private final BreadcrumbService breadcrumbService;

  @Autowired
  public TumbleDryersController(PdfRenderer pdfRenderer, TumbleDryersService tumbleDryersService, BreadcrumbService breadcrumbService) {
    this.pdfRenderer = pdfRenderer;
    this.tumbleDryersService = tumbleDryersService;
    this.breadcrumbService = breadcrumbService;
  }

  @GetMapping("/")
  public ModelAndView renderTumbleDryerSubCategories(@ModelAttribute("form") TumbleDryerSubCategoryForm form) {
    return getSubCategory();
  }

  @PostMapping("/")
  @ResponseBody
  public ModelAndView handleTumbleDryerSubCategoriesSubmit(@Valid @ModelAttribute("form") TumbleDryerSubCategoryForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return getSubCategory();
    }
    else {
      TumbleDryerSubCategory subCategory = TumbleDryerSubCategory.valueOf(form.getSubCategory());
      return new ModelAndView("redirect:" + subCategory.getNextStateUrl());
    }
  }

  private ModelAndView getSubCategory() {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/tumbleDryersSubCategory");
    modelAndView.addObject("subCategories",
      Arrays.stream(TumbleDryerSubCategory.values())
        .collect(StreamUtils.toLinkedHashMap(Enum::name, TumbleDryerSubCategory::getDisplayName))
    );
    modelAndView.addObject("submitUrl", ReverseRouter.route(on(TumbleDryersController.class).handleTumbleDryerSubCategoriesSubmit(null, ReverseRouter.emptyBindingResult())));
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Tumble dryers", ReverseRouter.route(on(TumbleDryersController.class).renderTumbleDryerSubCategories(null)));
    return modelAndView;
  }

  @GetMapping("/air-vented-tumble-dryers")
  public ModelAndView renderAirVentedTumbleDryers(@ModelAttribute("form") AirVentedTumbleDryersForm form) {
    return getAirVentedTumbleDryers(Collections.emptyList());
  }

  @PostMapping("/air-vented-tumble-dryers")
  @ResponseBody
  public Object handleAirVentedTumbleDryersSubmit(@Valid @ModelAttribute("form") AirVentedTumbleDryersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getAirVentedTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(tumbleDryersService.generateHtml(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "tumble-dryers-label.pdf");
    }
  }

  @GetMapping("/condenser-tumble-dryers")
  public ModelAndView renderCondenserTumbleDryers(@ModelAttribute("form") CondenserTumbleDryersForm form) {
    return getCondenserTumbleDryers(Collections.emptyList());
  }

  @PostMapping("/condenser-tumble-dryers")
  @ResponseBody
  public Object handleCondenserTumbleDryersSubmit(@Valid @ModelAttribute("form") CondenserTumbleDryersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getCondenserTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(tumbleDryersService.generateHtml(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "tumble-dryers-label.pdf");
    }
  }

  @GetMapping("/gas-fired-tumble-dryers")
  public ModelAndView renderGasFiredTumbleDryers(@ModelAttribute("form") GasFiredTumbleDryersForm form) {
    return getGasFiredTumbleDryers(Collections.emptyList());
  }

  @PostMapping("/gas-fired-tumble-dryers")
  @ResponseBody
  public Object handleGasFiredTumbleDryersSubmit(@Valid @ModelAttribute("form") GasFiredTumbleDryersForm form, BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return getGasFiredTumbleDryers(bindingResult.getFieldErrors());
    }
    else {
      Resource pdf = pdfRenderer.render(tumbleDryersService.generateHtml(form, TumbleDryersService.LEGISLATION_CATEGORY_CURRENT));
      return ControllerUtils.serveResource(pdf, "tumble-dryers-label.pdf");
    }
  }

  private ModelAndView getAirVentedTumbleDryers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/airVentedTumbleDryers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TumbleDryersController.class).renderAirVentedTumbleDryers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Air vented tumble dryers");
    return modelAndView;
  }

  private ModelAndView getCondenserTumbleDryers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/condenserTumbleDryers");
    RatingClassRange condensationEfficiencyRating = TumbleDryersService.LEGISLATION_CATEGORY_CURRENT.getSecondaryRatingRange();
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TumbleDryersController.class).renderCondenserTumbleDryers(null)));
    modelAndView.addObject("condensationEfficiencyRating", StreamUtils.ratingRangeToSelectionMap(condensationEfficiencyRating));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Condenser tumble dryers");
    return modelAndView;
  }

  private ModelAndView getGasFiredTumbleDryers(List<FieldError> errorList) {
    ModelAndView modelAndView = new ModelAndView("categories/tumble-dryers/gasFiredTumbleDryers");
    addCommonObjects(modelAndView, errorList, ReverseRouter.route(on(TumbleDryersController.class).renderGasFiredTumbleDryers(null)));
    breadcrumbService.pushLastBreadcrumb(modelAndView, "Gas-fired tumble dryers");
    return modelAndView;
  }

  private void addCommonObjects(ModelAndView modelAndView, List<FieldError> errorList,  String submitUrl) {
    RatingClassRange efficiencyRatingRange = TumbleDryersService.LEGISLATION_CATEGORY_CURRENT.getPrimaryRatingRange();
    modelAndView.addObject("efficiencyRating", StreamUtils.ratingRangeToSelectionMap(efficiencyRatingRange));
    ControllerUtils.addErrorSummary(modelAndView, errorList);
    modelAndView.addObject("submitUrl", submitUrl);
    breadcrumbService.addBreadcrumbToModel(modelAndView, "Tumble dryers", ReverseRouter.route(on(TumbleDryersController.class).renderTumbleDryerSubCategories(null)));
  }

}