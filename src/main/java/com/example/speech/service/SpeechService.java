package com.example.speech.service;

import com.example.speech.model.Speech;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.List;

public interface SpeechService {
    public Speech saveSpeech(Speech speech);
    public List<Speech> getAllSpeech();
    public List<Speech> findByDateRange(Date start,Date end);
    public List<Speech> findByActualSpeech(String actualSpeech);
    public List<Speech> findBySubject(String subject);
    public List<Speech> findByAuthor(String firstname,String lastname);
    public Boolean deleteSpeech(long id);
    public Speech updateSpeech(long id,Speech speech);
}
