package com.cuongnghiem.springbootrecipe.repositories;

import com.cuongnghiem.springbootrecipe.model.Notes;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by cuongnghiem on 28/09/2021
 **/

public interface NotesRepository extends CrudRepository<Notes, Long> {
}
