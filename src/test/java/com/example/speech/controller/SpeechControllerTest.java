package com.example.speech.controller;

import com.example.speech.entity.Author;
import com.example.speech.entity.Speech;
import com.example.speech.entity.Subject;
import com.example.speech.service.SpeechService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpeechControllerTest {

    @Mock
    SpeechService speechService;

    List<Speech> mockSpeeches = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(SpeechControllerTest.class);

    @Test
    public void getAllSpeech() {
        assignSpeeches();

        logger.info("getAllSpeech Test");
        logger.info(String.valueOf(speechService.getAllSpeech().size()));

        Mockito.when(speechService.getAllSpeech()).thenReturn(mockSpeeches);
        Assert.assertNotNull(mockSpeeches.size());
        Assert.assertTrue(mockSpeeches.size()>0);
    }

    @SneakyThrows
    @Test
    public void findByDateRange(){
        assignSpeeches();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        String start = "2021-03-01", end = "2022-08-10";
        Date sdate = formatter.parse(start), edate = formatter.parse(end);


        logger.info("findByDateRange Test");
        logger.info(String.valueOf(speechService.findByDateRange(new java.sql.Date(sdate.getTime()),new java.sql.Date(edate.getTime())).size()));

        Mockito.when(speechService.findByDateRange(new java.sql.Date(sdate.getTime()),new java.sql.Date(edate.getTime()))).thenReturn(mockSpeeches);
        Assert.assertNotNull(mockSpeeches.size());
        Assert.assertTrue(mockSpeeches.size()>0);
    }

    @Test
    public void findByActualSpeech(){
        assignSpeeches();
        String actualSpeech = "hello!";

        logger.info("findByActualSpeech Test");
        logger.info(String.valueOf(speechService.findByActualSpeech(actualSpeech).size()));

        Mockito.when(speechService.findByActualSpeech(actualSpeech)).thenReturn(mockSpeeches);
        Assert.assertNotNull(mockSpeeches.size());
        Assert.assertTrue(mockSpeeches.size()>0);
    }

    @Test
    public void findBySubject(){
        assignSpeeches();
        String subj = "engagement";

        logger.info("findBySubject Test");
        logger.info(String.valueOf(speechService.findBySubject(subj).size()));

        Mockito.when(speechService.findBySubject(subj)).thenReturn(mockSpeeches);
        Assert.assertNotNull(mockSpeeches.size());
        Assert.assertTrue(mockSpeeches.size()>0);
    }

    @Test
    public void findByAuthor(){
        assignSpeeches();
        String fname = "Bill", lname = "Pepito";

        logger.info("findByAuthor Test");
        logger.info(String.valueOf(speechService.findByAuthor(fname, lname).size()));

        Mockito.when(speechService.findByAuthor(fname,lname)).thenReturn(mockSpeeches);
        Assert.assertNotNull(mockSpeeches.size());
        Assert.assertTrue(mockSpeeches.size()>0);
    }

    @Test
    public void deleteById(){
        long speechId = 2;
        Boolean mockReturn = true;

        logger.info("deleteById Test");

        Mockito.when(speechService.deleteSpeech(speechId)).thenReturn(mockReturn);
        Assert.assertNotNull(mockReturn);
        Assert.assertTrue(mockReturn);
    }

    @Test
    public void saveSpeech(){
        assignSpeeches();

        logger.info("saveSpeech Test");
        mockSpeeches.get(0).setId(2);
        Mockito.when(speechService.saveSpeech(mockSpeeches.get(0))).thenReturn(mockSpeeches.get(0));
        Assert.assertNotNull(mockSpeeches.get(0));
    }

    public void assignSpeeches(){
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
    }
}
