/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.sample.jpa.query.benchmark;

import org.junit.Test;

public class ComplexQueryTest extends AbstractQueryTest {

	@Test
	public void testJPQL() {
		asserts(jpqlRepository.getSongsBySameArtist(2));
	}

	@Test
	public void testQueryDsl() {
		asserts(queryDslRepository.getSongsBySameArtist(2));
	}

	@Test
	public void testNamedQuery() {
		asserts(namedQueryRepository.getSongsBySameArtist(2));
	}

	@Test
	public void testJPACriteria() {
		asserts(jpaCriteriaRepository.getSongsBySameArtist(2));
	}

	@Test
	public void testSpringDataJPARepository() {
		asserts(springDataJPARepository.getSongsBySameArtist(2));
	}

	@Test
	public void testSpringDataJPAQueryDslRepository() {
		asserts(springDataJPAQueryDslRepository.getSongsBySameArtist(2));
	}
}
