package com.example.speech.repository;

import com.example.speech.model.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface SpeechRepository extends JpaRepository<Speech, Long> {

  @Query(value = "SELECT * from speechdb.speech WHERE author LIKE %:author%", nativeQuery = true)
  List<Speech> findByAuthor(String author);

  @Query(value = "SELECT * from speechdb.speech WHERE date BETWEEN :start and :end", nativeQuery = true)
  List<Speech> findByDateRange(Date start, Date end);

  @Query(value = "SELECT * from speechdb.speech WHERE actual_speech LIKE %:speech%", nativeQuery = true)
  List<Speech> findByActualSpeech(String speech);
}
