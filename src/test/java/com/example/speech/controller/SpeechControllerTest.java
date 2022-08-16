package com.example.speech.controller;

import com.example.speech.model.Author;
import com.example.speech.model.Speech;
import com.example.speech.model.Subject;
import com.example.speech.service.SpeechService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpeechControllerTest {
    @Autowired
    SpeechService speechService;

    List<Speech> mockSpeeches = new ArrayList<>();

    @Test
    public void getAllSpeech() {
        Set<Author> authors = new HashSet<>();
        authors.add(new Author("Joane", "Artiaga"));
        authors.add(new Author("Bill", "Pepito"));
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("hello"));
        subjects.add(new Subject("test"));

        mockSpeeches.add(new Speech(authors,
            new java.sql.Date(Calendar.getInstance().getTime().getTime()), "test actual speech",
          subjects));

        authors.add(new Author("Joane", "Artiaga"));
        authors.add(new Author("Resty", "Artiaga"));
        subjects.add(new Subject("engaged"));
        subjects.add(new Subject("congratulations"));

        mockSpeeches.add(new Speech(authors,
          new java.sql.Date(Calendar.getInstance().getTime().getTime()), "I just got engaged!",
          subjects));

        Mockito.when(speechService.getAllSpeech()).thenReturn(mockSpeeches);
        Assert.assertEquals(mockSpeeches.size(),2);
    }
}
