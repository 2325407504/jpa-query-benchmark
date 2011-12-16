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
package com.excilys.ebi.sample.jpa.query.benchmark.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.IRepository;

@Repository("NamedQueryRepository")
@Transactional(readOnly = true)
public class NamedQueryRepository implements IRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public List<Song> getSongsByArtistName(String name) {

		Query query = em.createNamedQuery("getSongsByArtistName");
		query.setParameter(1, name);

		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Song> getSongsBySameArtist(Integer songId) {

		Query query = em.createNamedQuery("getSongsBySameArtist");
		query.setParameter(1, songId);

		return query.getResultList();
	}
}
