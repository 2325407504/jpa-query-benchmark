package com.excilys.ebi.sample.jpa.query.benchmark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.IRepository;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.ISpringDataRepository;
import com.excilys.utils.spring.test.dbunit.DataSet;
import com.excilys.utils.spring.test.dbunit.DataSetTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("JPARepositoryTest-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet
public class AbstractQueryTest {

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

	protected static final int WARM_UP_ITERATIONS = 50000;

	protected static final int TEST_ITERATIONS = 10000;

	protected static final int THREAD_POOL_SIZE = 4;

	protected ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

	protected void run(Callable<List<Song>> callable, String testName, Logger logger) throws InterruptedException, ExecutionException {

		// warm up
		invokeCallable(callable, WARM_UP_ITERATIONS);

		// run
		Long start = System.nanoTime();
		invokeCallable(callable, TEST_ITERATIONS);

		Long end = System.nanoTime();
		logger.info("{} {}ms", testName, (end - start) / 1000000);
	}

	protected void invokeCallable(Callable<List<Song>> callable, int times) throws InterruptedException, ExecutionException {

		Collection<Callable<List<Song>>> callables = new ArrayList<Callable<List<Song>>>();
		for (int i = 0; i < times; i++) {
			callables.add(callable);
		}

		List<Future<List<Song>>> results = executorService.invokeAll(callables);

		for (Future<List<Song>> result : results) {
			List<Song> songs = result.get();
			Assert.assertTrue(songs.size() == 12);
			Assert.assertEquals("Consternation", songs.get(0).getTitle());
			Assert.assertEquals("The Itch", songs.get(11).getTitle());
		}
	}
}
