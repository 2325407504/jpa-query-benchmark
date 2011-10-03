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
package com.excilys.ebi.sample.jpa.query.benchmark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.ebi.sample.jpa.query.benchmark.model.Song;

@Transactional(readOnly = true)
public interface ISpringDataJPARepository extends JpaRepository<Song, Integer> {

	@Query("from Song where artist.name=? order by title")
	List<Song> getSongsByArtistName(String name);

	@Query("select s2 from Song s1 join s1.artist.songs s2 where s1.id=? order by s2.title")
	List<Song> getSongsBySameArtist(Integer songId);
}
