package com.bullhornsdk.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.bullhornsdk.data.model.entity.core.standard.Candidate;
import com.bullhornsdk.data.model.entity.core.standard.JobOrder;
import com.bullhornsdk.data.model.entity.core.standard.Note;
import com.bullhornsdk.data.model.entity.core.type.BullhornEntity;
import com.bullhornsdk.data.model.parameter.SearchParams;
import com.bullhornsdk.data.model.parameter.standard.ParamFactory;
import com.bullhornsdk.data.model.response.list.ListWrapper;

/**
 * Tests the "query" request
 * 
 * @author magnus.palm
 * 
 */

public class TestStandardBullhornApiRestSearch extends BaseTest {
	private final Logger log = Logger.getLogger(TestStandardBullhornApiRestSearch.class);

	private String query = "id:1*";

	private SearchParams searchParams;

	public TestStandardBullhornApiRestSearch() {
		super();
		this.searchParams = ParamFactory.searchParams();
		searchParams.setCount(20);

	}

	@Test
	public void testSearchCandidate() {

		ListWrapper<Candidate> wrapper = bullhornApiRest.search(Candidate.class, query, null, searchParams);

		runAssertions("ListWrapper<Candidate>", wrapper);

	}

	@Test
	public void testSearchNote() {
		String customQuery = "noteID:" + testEntities.getNoteId();
		ListWrapper<Note> wrapper = bullhornApiRest.search(Note.class, customQuery, null, searchParams);

		runAssertions("ListWrapper<Note>", wrapper);

	}

	@Test
	public void testSearchJobOrder() {

		ListWrapper<JobOrder> wrapper = bullhornApiRest.search(JobOrder.class, query, null, searchParams);

		runAssertions("ListWrapper<JobOrder>", wrapper);

	}

	// @Test
	public void getSearchMetaData() {
		String url = bullhornApiRest.getRestUrl();
		url = url + "meta/{entityType}?BhRestToken={bhRestToken}&meta=full&fields=*";
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("bhRestToken", bullhornApiRest.getBhRestToken());
		urlParams.put("entityType", "Note");
		// String jsonValue = bullhornApiRest.performGetRequest(url, String.class, urlParams);

	}

	private <T extends BullhornEntity> void runAssertions(String wrapperName, ListWrapper<T> wrapper) {
		assertNotNull(wrapperName + " is null", wrapper);
		assertNotNull(wrapperName + ".data is null", wrapper.getData());
		assertTrue(wrapperName + ".data is empty", wrapper.getData() != null && wrapper.getData().size() > 0);
		assertTrue(wrapperName + ".data.size() is not equal to count", wrapper.getData().size() == wrapper.getCount());
	}

}