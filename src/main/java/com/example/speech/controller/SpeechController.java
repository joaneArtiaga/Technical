package com.example.speech.controller;

import com.example.speech.model.Speech;
import com.example.speech.repository.SpeechRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SpeechController {
   @Autowired
   SpeechRepository speechRepository;
   Logger logger = LoggerFactory.getLogger(SpeechController.class);

   @PostMapping("/speech")
   public ResponseEntity<Speech> createSpeech(@RequestBody Speech speech){
    logger.info("Create Speech");
    logger.info("Request Body: ");
       try{
         Speech createdSpeech = speechRepository.save(new Speech(speech.getAuthor(),
           speech.getDate(), speech.getActual_speech()));

         return new ResponseEntity<>(createdSpeech, HttpStatus.CREATED);
       }catch (Exception ex){
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @GetMapping("/speech/all")
   public ResponseEntity<List<Speech>> getAllSpeech(){
       try{
           List<Speech> allSpeech = speechRepository.findAll();
           return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
       }catch (Exception ex){
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @GetMapping("/speech/findAuthor/{author}")
   public ResponseEntity<List<Speech>> findByAuthor(@PathVariable("author") String author){
    try{
     List<Speech> allSpeech = speechRepository.findByAuthor(author);
     return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
    }catch (Exception ex){
     return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   }

   @GetMapping("/speech/dateRange/{start}/{end}")
   public ResponseEntity<List<Speech>> findByDateRange(@PathVariable("start") Date start,
                                                    @PathVariable("end") Date end){
    try{
     List<Speech> allSpeech = speechRepository.findByDateRange(start, end);
     return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
    }catch (Exception ex){
     return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   }

   @GetMapping("/speech/findByActualSpeech/{actualSpeech}")
   public ResponseEntity<List<Speech>> findByActualSpeech(@PathVariable("actualSpeech") String actualSpeech){
    try{
     List<Speech> allSpeech = speechRepository.findByActualSpeech(actualSpeech);
     return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
    }catch (Exception ex){
     return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   }

   @DeleteMapping("/speech/{id}")
   public ResponseEntity<Speech> deleteSpeech(@PathVariable("id") long id){
        try{
            speechRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception ex){
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

   @PutMapping("/speech/{id}")
   public  ResponseEntity<Speech> updateSpeech(@PathVariable("id") long id,
                                               @RequestBody Speech speech){
        Optional<Speech> optionalSpeech = speechRepository.findById(id);
        if(optionalSpeech.isPresent()){
            Speech optData = optionalSpeech.get();
            optData.setActual_speech(speech.getActual_speech());
            optData.setDate(speech.getDate());
            optData.setAuthor(speech.getAuthor());
            return new ResponseEntity<>(speechRepository.save(optData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
   }
}
