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
package com.excilys.ebi.sample.jpa.query.benchmark.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.ebi.sample.jpa.query.benchmark.model.Artist;
import com.excilys.ebi.sample.jpa.query.benchmark.model.Artist_;
import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;
import com.excilys.ebi.sample.jpa.query.benchmark.model.Song_;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.IRepository;

@Repository("JPACriteriaRepository")
@Transactional(readOnly = true)
public class JPACriteriaRepository implements IRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Song> getSongsByArtistName(final String name) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Song> query = builder.createQuery(Song.class);
		Root<Song> root = query.from(Song.class);

		query.where(builder.equal(root.get(Song_.artist).get(Artist_.name), name)).orderBy(builder.asc(root.get(Song_.title)));

		return em.createQuery(query).getResultList();
	}

	@Override
	public List<Song> getSongsBySameArtist(final Integer songId) {

		// select s.artist.songs from Song s where s.id=?

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Song> query = builder.createQuery(Song.class);

		Root<Song> song1 = query.from(Song.class);
		Join<Artist, Song> artistSongs = song1.join(Song_.artist).join(Artist_.songs);

		query.select(artistSongs).where(builder.equal(song1.get(Song_.id), songId)).orderBy(builder.asc(artistSongs.get(Song_.title)));
		return em.createQuery(query).getResultList();
	}
}
