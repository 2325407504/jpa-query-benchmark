package com.excilys.ebi.sample.jpa.query.benchmark;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Rule;
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
import com.excilys.ebi.sample.jpa.query.benchmark.repository.ISpringDataRepository;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("JPARepositoryTest-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet
@BenchmarkOptions(benchmarkRounds = 1000, warmupRounds = 100)
public class AbstractQueryTest {

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
	protected ISpringDataRepository springDataRepository;

	protected void asserts(List<Song> songs) {
		Assert.assertTrue(songs.size() == 12);
		Assert.assertEquals("Consternation", songs.get(0).getTitle());
		Assert.assertEquals("The Itch", songs.get(11).getTitle());
	}
}
