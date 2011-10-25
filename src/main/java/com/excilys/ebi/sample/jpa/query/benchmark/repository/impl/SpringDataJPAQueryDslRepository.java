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

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.ebi.sample.jpa.query.benchmark.model.QSong;
import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;
import com.excilys.ebi.sample.jpa.query.benchmark.repository.IRepository;

@Repository("SpringDataJPAQueryDslRepository")
@Transactional(readOnly = true)
public class SpringDataJPAQueryDslRepository extends QueryDslRepositorySupport implements IRepository {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}

	@Override
	public List<Song> getSongsByArtistName(String name) {
		QSong song = QSong.song;
		return from(song).where(song.artist.name.eq(name)).orderBy(song.title.asc()).list(song);
	}

	@Override
	public List<Song> getSongsBySameArtist(Integer songId) {
		QSong song1 = QSong.song;
		QSong song2 = new QSong("song2");
		return from(song1).innerJoin(song1.artist.songs, song2).where(song1.id.eq(songId)).orderBy(song2.title.asc()).list(song2);
	}
}
