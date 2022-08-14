package com.example.speech.repository;

import com.example.speech.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Long> {

 @Query(value = "SELECT * from speechdb.author WHERE firstname = :firstname AND lastname = :lastname",
   nativeQuery = true)
 Author findIfExist(String firstname, String lastname);

}
