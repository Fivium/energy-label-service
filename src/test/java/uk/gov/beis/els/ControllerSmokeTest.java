package uk.gov.beis.els;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
    "app.show_start_page=true"
})
public class ControllerSmokeTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerSmokeTest.class);

  private MockMvc mockMvc;

  @Autowired
  public RequestMappingHandlerMapping requestMappingHandlerMapping;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void abstractSetUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();
  }

  @Test
  public void testAllGets() throws Exception {
    List<String> getRoutes = requestMappingHandlerMapping.getHandlerMethods().keySet().stream()
        .filter(r -> r.getMethodsCondition().getMethods().contains(RequestMethod.GET))
        .map(r -> (String) r.getPatternsCondition().getPatterns().toArray()[0])
        .collect(Collectors.toList());

    for (String route : getRoutes) {
      LOGGER.info("GET: {}", route);
      mockMvc.perform(get(route))
          .andExpect(status().isOk());
    }

  }

}
