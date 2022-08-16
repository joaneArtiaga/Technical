package com.example.speech.controller;

import com.example.speech.model.ErrorResponse;
import com.example.speech.model.Speech;
import com.example.speech.repository.AuthorRepository;
import com.example.speech.repository.SpeechRepository;
import com.example.speech.repository.SubjectRepository;
import com.example.speech.service.SpeechService;
import com.example.speech.service.impl.SpeechServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SpeechController {
    @Autowired
    SpeechRepository speechRepository;

    @Autowired
    SpeechServiceImpl speechService;

    Logger logger = LoggerFactory.getLogger(SpeechController.class);

    @PostMapping("/speech")
    public ResponseEntity<Speech> createSpeech(@RequestBody Speech speech) {
        logger.info("Creating Speech...");
        return new ResponseEntity<>(speechService.saveSpeech(speech),HttpStatus.CREATED);
    }

    @GetMapping("/speech/all")
    public ResponseEntity<List<Speech>> getAllSpeech() {
        logger.info("Retrieving Speech...");
        return new ResponseEntity<>(speechService.getAllSpeech(), HttpStatus.FOUND);
    }

    @GetMapping("/speech/dateRange/{start}/{end}")
    public ResponseEntity<List<Speech>> findByDateRange(@PathVariable("start") Date start,
                                                        @PathVariable("end") Date end) {
        logger.info("Finding Speech by date range...");
        return new ResponseEntity<>(speechService.findByDateRange(start, end), HttpStatus.FOUND);
    }

    @GetMapping("/speech/findByActualSpeech/{actualSpeech}")
    public ResponseEntity<List<Speech>> findByActualSpeech(@PathVariable("actualSpeech") String actualSpeech) {
        logger.info("Finding Speech by actual speech...");
        return new ResponseEntity<>(speechService.findByActualSpeech(actualSpeech), HttpStatus.FOUND);
    }

    @GetMapping("/speech/findBySubject/{subject}")
    public ResponseEntity<List<Speech>> findBySubject(@PathVariable("subject") String subject) {
        logger.info("Finding Speech by subject...");
        return new ResponseEntity<>(speechService.findBySubject(subject), HttpStatus.FOUND);
    }

    @GetMapping("/speech/findAuthor/{firstname}/{lastname}")
    public ResponseEntity<List<Speech>> findByAuthor(@PathVariable("firstname") String firstname,
                                       @PathVariable("lastname") String lastname) {
        logger.info("Finding Speech by author...");
        return new ResponseEntity<>(speechService.findByAuthor(firstname, lastname),
          HttpStatus.FOUND);
    }

    @DeleteMapping("/speech/{id}")
    public ResponseEntity deleteSpeech(@PathVariable("id") long id) {
        logger.info("Deleting Speech...");
        if(speechService.deleteSpeech(id)){
            return new ResponseEntity<>("Speech with id "+id+" has been deleted.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Speech with id "+id+" has not been deleted successfully.",
              HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/speech/{id}")
    public ResponseEntity<Speech> updateSpeech(@PathVariable("id") long id,
                                       @RequestBody Speech speech) {
        logger.info("Updating Speech...");
        return new ResponseEntity<>(speechService.updateSpeech(id, speech), HttpStatus.OK);
    }
}
