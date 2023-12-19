package ee.balder.filters;

import ee.balder.filters.config.TestPostgresConfig;
import ee.balder.filters.domain.Criteria;
import ee.balder.filters.domain.CriteriaCondition;
import ee.balder.filters.domain.CriteriaType;
import ee.balder.filters.domain.Filter;
import ee.balder.filters.repository.FilterRepository;
import ee.balder.filters.util.FixtureUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = {TestPostgresConfig.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class FilterIntegrationTest {

    private static final String CREATE_FILTER_REQUEST = FixtureUtils.fixture("fixtures/CreateFilterRequest.json");

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private FilterRepository filterRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        filterRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void queryFilters() throws Exception {
        var filter = filterRepository.save(testFilter());

        mockMvc.perform(get("/filter/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(filter.getName())))
                .andExpect(jsonPath("$[0].criteria", hasSize(1)))
                .andExpect(jsonPath("$[0].criteria[0].type", is("AMOUNT")))
                .andExpect(jsonPath("$[0].criteria[0].condition", is("MORE")))
                .andExpect(jsonPath("$[0].criteria[0].compareValue", is("0")));

        mockMvc.perform(get(String.format("/filter/%s", filter.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(filter.getName())))
                .andExpect(jsonPath("$.criteria", hasSize(1)));

        filterRepository.delete(filter);

        mockMvc.perform(get("/filter/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void saveFilter() throws Exception {
        mockMvc.perform(post("/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CREATE_FILTER_REQUEST))
                .andExpect(status().isOk());

        Assertions.assertEquals(1, filterRepository.findAll().size());

        var filter = filterRepository.findAll().iterator().next();
        Assertions.assertEquals("TEST_FILTER", filter.getName());
        Assertions.assertEquals(2, filter.getCriteria().size());
        Assertions.assertEquals(CriteriaType.TITLE, filter.getCriteria().get(0).getType());
        Assertions.assertEquals(CriteriaCondition.CONTAINS, filter.getCriteria().get(0).getCondition());
        Assertions.assertEquals("Wasd", filter.getCriteria().get(0).getCompareValue());
        Assertions.assertEquals(CriteriaType.DATE, filter.getCriteria().get(1).getType());
        Assertions.assertEquals(CriteriaCondition.FROM, filter.getCriteria().get(1).getCondition());
        Assertions.assertEquals("24/10/2020", filter.getCriteria().get(1).getCompareValue());
    }

    @Test
    void saveFilterInvalid() throws Exception {
        mockMvc.perform(post("/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

        Assertions.assertEquals(0, filterRepository.findAll().size());
    }

    private static Filter testFilter() {
        var filter = new Filter();
        filter.setName("TEST_FILTER");
        filter.setCriteria(List.of(new Criteria(CriteriaType.AMOUNT, CriteriaCondition.MORE, "0")));
        return filter;
    }

}
