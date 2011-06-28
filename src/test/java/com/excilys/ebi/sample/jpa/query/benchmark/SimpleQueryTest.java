/*
 * Copyright 2010-2011 eBusiness Information, Groupe Excilys (www.excilys.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.excilys.ebi.sample.jpa.query.benchmark;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;

public class SimpleQueryTest extends AbstractQueryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleQueryTest.class);

	@Test
	public void testJPQL() throws InterruptedException, ExecutionException {

		run(new Callable<List<Song>>() {
			@Override
			public List<Song> call() {
				return jpqlRepository.getSongsByArtistName("Katatonia");
			}
		}, "JPQL", LOGGER);
	}

	@Test
	public void testQueryDsl() throws InterruptedException, ExecutionException {

		run(new Callable<List<Song>>() {
			@Override
			public List<Song> call() {
				return queryDslRepository.getSongsByArtistName("Katatonia");
			}
		}, "QueryDsl", LOGGER);
	}

	@Test
	public void testNamedQuery() throws InterruptedException, ExecutionException {

		run(new Callable<List<Song>>() {
			@Override
			public List<Song> call() {
				return namedQueryRepository.getSongsByArtistName("Katatonia");
			}
		}, "Named Query", LOGGER);
	}

	@Test
	public void testJPACriteria() throws InterruptedException, ExecutionException {

		run(new Callable<List<Song>>() {
			@Override
			public List<Song> call() {
				return jpaCriteriaRepository.getSongsByArtistName("Katatonia");
			}
		}, "JPA Criteria", LOGGER);
	}

	@Test
	public void testSpringDataRepository() throws InterruptedException, ExecutionException {

		run(new Callable<List<Song>>() {
			@Override
			public List<Song> call() {
				return springDataRepository.getSongsByArtistName("Katatonia");
			}
		}, "Spring Data JPA", LOGGER);
	}
}
