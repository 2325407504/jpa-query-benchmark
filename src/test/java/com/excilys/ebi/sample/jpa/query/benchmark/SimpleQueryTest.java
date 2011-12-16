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

import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class SimpleQueryTest extends AbstractQueryTest {

	@Test
	public void testJPQL() throws InterruptedException, ExecutionException {
		asserts(jpqlRepository.getSongsByArtistName("Katatonia"));
	}

	@Test
	public void testQueryDsl() throws InterruptedException, ExecutionException {
		asserts(queryDslRepository.getSongsByArtistName("Katatonia"));
	}

	@Test
	public void testNamedQuery() throws InterruptedException, ExecutionException {
		asserts(namedQueryRepository.getSongsByArtistName("Katatonia"));
	}

	@Test
	public void testJPACriteria() throws InterruptedException, ExecutionException {
		asserts(jpaCriteriaRepository.getSongsByArtistName("Katatonia"));
	}

	@Test
	public void testSpringDataJPARepository() throws InterruptedException, ExecutionException {
		asserts(springDataJPARepository.getSongsByArtistName("Katatonia"));
	}

	@Test
	public void testSpringDataJPAQueryDslRepository() throws InterruptedException, ExecutionException {
		asserts(springDataJPAQueryDslRepository.getSongsByArtistName("Katatonia"));
	}
}
