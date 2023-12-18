package ee.balder.filters;

import ee.balder.filters.config.TestPostgresConfig;
import ee.balder.filters.domain.Criteria;
import ee.balder.filters.domain.CriteriaCondition;
import ee.balder.filters.domain.CriteriaType;
import ee.balder.filters.domain.Filter;
import ee.balder.filters.repository.FilterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = {TestPostgresConfig.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class FilterIntegrationTest {

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	private FilterRepository filterRepository;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@AfterEach
	public void teardown() {
		filterRepository.deleteAll();
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

		filterRepository.delete(filter);

		mockMvc.perform(get("/filter/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	private static Filter testFilter() {
		var filter = new Filter();
		filter.setName("TEST_FILTER");
		filter.setCriteria(List.of(new Criteria(CriteriaType.AMOUNT, CriteriaCondition.MORE, "0")));
		return filter;
	}

}
