package com.example.speech.service.impl;

import com.example.speech.controller.SpeechController;
import com.example.speech.exception.SpeechCustomException;
import com.example.speech.model.Author;
import com.example.speech.model.ErrorResponse;
import com.example.speech.model.Speech;
import com.example.speech.model.Subject;
import com.example.speech.repository.SpeechRepository;
import com.example.speech.service.AuthorService;
import com.example.speech.service.SpeechService;
import com.example.speech.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpeechServiceImpl implements SpeechService {

    @Autowired
    SpeechRepository speechRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    SubjectService subjectService;

    Logger logger = LoggerFactory.getLogger(SpeechServiceImpl.class);

    @Override
    public Speech saveSpeech(Speech speech) {
        logger.info("Saving Speech...");
        try {
            logger.info("inside try catch");
            speech.setAuthors(authorService.saveAuthors(speech.getAuthors()));
            logger.info("After setting authors");
            speech.setSubjects(subjectService.saveSubjects(speech.getSubjects()));
            Speech createdSpeech = speechRepository.save(new Speech(speech.getAuthors(),
              speech.getDate(), speech.getActual_speech(), speech.getSubjects()));
            logger.info("Saving speech");
            logger.info(createdSpeech.getActual_speech());
            return createdSpeech;
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Speech> getAllSpeech() {
        try {
            List<Speech> allSpeech = speechRepository.findAll();
            if (allSpeech.isEmpty()) {
                throw new SpeechCustomException("There are no speeches right now.",
                  HttpStatus.NOT_FOUND.value());
            } else {
                return allSpeech;
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Speech> findByDateRange(Date start, Date end) {
        try {
            List<Speech> allSpeech = speechRepository.findByDateRange(start, end);
            if(allSpeech.isEmpty()){
                throw new SpeechCustomException("There are no speeches around dates "+start.toString()+
                  " and "+end.toString()+".", HttpStatus.NOT_FOUND.value());
            }else{
                return allSpeech;
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Speech> findByActualSpeech(String actualSpeech) {
        try {
            List<Speech> allSpeech = speechRepository.findByActualSpeech(actualSpeech);
            if(allSpeech.isEmpty()){
                throw new SpeechCustomException("There are no speeches with actual speech "+actualSpeech+".",
                  HttpStatus.NOT_FOUND.value());
            }else{
                return allSpeech;
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Speech> findBySubject(String subject) {
        try {
            Subject subj = subjectService.checkSubjExist(subject);
            if (subj != null) {
                List<Speech> allSpeech = speechRepository.findBySubjectArea(subj.getId());
                return allSpeech;
            } else {
                throw new SpeechCustomException("There is no subject with " + subject,
                  HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Speech> findByAuthor(String firstname, String lastname) {
        try {
            Author author = authorService.checkAuthorExist(new Author(firstname, lastname));
            if (author != null) {
                List<Speech> allSpeech = speechRepository.findByAuthor(author.getId());
                return allSpeech;
            } else {
                throw new SpeechCustomException("There is no Author by the name " + firstname +
                  " " + lastname, HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Boolean deleteSpeech(long id) {
        try {
            Optional<Speech> speech = speechRepository.findById(id);
            if(speech.isPresent()){
                speechRepository.deleteSpeechSubj(id);
                speechRepository.deleteSpeechAuth(id);
                speechRepository.deleteById(id);
                return true;
            }else{
                throw new SpeechCustomException("There are no speeches with id "+id,
                  HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Speech updateSpeech(long id, Speech speech) {
        try {
            Optional<Speech> existSpeech = speechRepository.findById(id);
            if(existSpeech.isPresent()){
                Speech optData = existSpeech.get();
                speech.setAuthors(authorService.saveAuthors(speech.getAuthors()));
                speech.setSubjects(subjectService.saveSubjects(speech.getSubjects()));
                optData.setActual_speech(speech.getActual_speech());
                optData.setDate(speech.getDate());
                optData.setAuthors(speech.getAuthors());
                optData.setSubjects(speech.getSubjects());
                return speechRepository.save(optData);
            }else{
                throw new SpeechCustomException("There are no speeches with id "+id,
                  HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception ex) {
            throw new SpeechCustomException(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
