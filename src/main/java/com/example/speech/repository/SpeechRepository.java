package com.example.speech.repository;

import com.example.speech.model.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface SpeechRepository extends JpaRepository<Speech, Long> {

  @Query(value = "SELECT * from speechdb.speech WHERE id = :id", nativeQuery = true)
  Speech findIfExist(long id);

  @Query(value = "SELECT * from speechdb.speech WHERE date BETWEEN :start and :end", nativeQuery = true)
  List<Speech> findByDateRange(Date start, Date end);

  @Query(value = "SELECT * from speechdb.speech WHERE actual_speech LIKE %:speech%", nativeQuery = true)
  List<Speech> findByActualSpeech(String speech);

  @Query(value = "SELECT * FROM speechdb.speech AS sp INNER JOIN speechdb.speech_subject AS sbj " +
    "ON sp.id = sbj.speech_id WHERE sbj.subject_id = :subjectId", nativeQuery = true)
  List<Speech> findBySubjectArea(long subjectId);

  @Query(value = "SELECT * FROM speechdb.speech AS sp INNER JOIN speechdb.speech_author AS auth " +
    "ON sp.id = auth.speech_id WHERE auth.author_id = :authorId", nativeQuery = true)
  List<Speech> findByAuthor(long authorId);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM speechdb.speech_subject where speech_subject.speech_id = :id", nativeQuery = true)
  int deleteSpeechSubj(long id);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM speechdb.speech_author where speech_author.speech_id = :id", nativeQuery = true)
  int deleteSpeechAuth(long id);
}
