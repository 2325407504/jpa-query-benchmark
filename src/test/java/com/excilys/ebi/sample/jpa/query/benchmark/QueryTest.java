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

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.IRepository;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.ISpringDataJPARepository;
import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("JPARepositoryTest-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(dbType = DBType.H2)
@BenchmarkOptions(benchmarkRounds = 10000, warmupRounds = 10000)
public class QueryTest {

	protected Random random = new Random();

	@Rule
	public MethodRule benchmarkRun = new BenchmarkRule();

	@Resource(name = "JPQLRepository")
	protected IRepository jpqlRepository;

	@Resource(name = "JPACriteriaRepository")
	protected IRepository jpaCriteriaRepository;

	@Resource(name = "NamedQueryRepository")
	protected IRepository namedQueryRepository;

	@Resource(name = "QueryDslRepository")
	protected IRepository queryDslRepository;

	@Autowired
	protected ISpringDataJPARepository springDataJPARepository;

	@Resource(name = "SpringDataJPAQueryDslRepository")
	protected IRepository springDataJPAQueryDslRepository;

	private interface Asserts {
		void asserts(List<Song> songs);
	}

	private static final Asserts ASSERTS_1 = new Asserts() {
		@Override
		public void asserts(List<Song> songs) {
			Assert.assertTrue(songs.size() == 12);
			Assert.assertEquals("Consternation", songs.get(0).getTitle());
			Assert.assertEquals("The Itch", songs.get(11).getTitle());
		}
	};

	private static final Asserts ASSERTS_2 = new Asserts() {
		@Override
		public void asserts(List<Song> songs) {
			Assert.assertTrue(songs.size() == 10);
			Assert.assertEquals("Clawmaster", songs.get(2).getTitle());
			Assert.assertEquals("Traces Of Liberty", songs.get(9).getTitle());
		}
	};

	private static final Asserts ASSERTS_3 = new Asserts() {
		@Override
		public void asserts(List<Song> songs) {
			Assert.assertTrue(songs.size() == 6);
			Assert.assertEquals("359°", songs.get(0).getTitle());
			Assert.assertEquals("The Nearing Grave", songs.get(5).getTitle());
		}
	};

	private static final Asserts[] ASSERTS = new Asserts[] { ASSERTS_1, ASSERTS_2, ASSERTS_3 };

	private void test(IRepository repository) {
		int i = random.nextInt(3);
		List<Song> songs = null;
		if (random.nextBoolean()) {
			String artistName = null;
			switch (i) {
			case 0:
				artistName = "Katatonia";
				break;
			case 1:
				artistName = "Ghost Brigade";
				break;
			case 2:
				artistName = "Long Distance Calling";
				break;
			}
			songs = repository.getSongsByArtistNameOrderBySongTitle(artistName);

		} else {
			int song = 0;
			switch (i) {
			case 0:
				song = 2;
				break;
			case 1:
				song = 17;
				break;
			case 2:
				song = 24;
				break;
			}

			songs = repository.getSongsBySameArtistOrderBySongTitle(song);
		}

		ASSERTS[i].asserts(songs);
	}

	@Test
	public void testJPQL() throws InterruptedException, ExecutionException {
		test(jpqlRepository);
	}

	@Test
	public void testQueryDsl() throws InterruptedException, ExecutionException {
		test(queryDslRepository);
	}

	@Test
	public void testNamedQuery() throws InterruptedException, ExecutionException {
		test(namedQueryRepository);
	}

	@Test
	public void testJPACriteria() throws InterruptedException, ExecutionException {
		test(jpaCriteriaRepository);
	}

	@Test
	public void testSpringDataJPARepository() throws InterruptedException, ExecutionException {
		test(springDataJPARepository);
	}

	@Test
	public void testSpringDataJPAQueryDslRepository() throws InterruptedException, ExecutionException {
		test(springDataJPAQueryDslRepository);
	}
}
