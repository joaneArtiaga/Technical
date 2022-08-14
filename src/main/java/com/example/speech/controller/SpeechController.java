package com.example.speech.controller;

import com.example.speech.model.Author;
import com.example.speech.model.ErrorResponse;
import com.example.speech.model.Speech;
import com.example.speech.model.Subject;
import com.example.speech.repository.AuthorRepository;
import com.example.speech.repository.SpeechRepository;
import com.example.speech.repository.SubjectRepository;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SpeechController {
    @Autowired
    SpeechRepository speechRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    SubjectRepository subjectRepository;

    Logger logger = LoggerFactory.getLogger(SpeechController.class);

    private ErrorResponse errorResponse = null;

    @PostMapping("/speech")
    public ResponseEntity createSpeech(@RequestBody Speech speech) {
        logger.info("Create Speech");
        try {
            speech.setAuthors(saveAuthors(speech.getAuthors()));
            logger.error(String.valueOf(speech.getSubjects().size()));
            speech.setSubjects(saveSubjects(speech.getSubjects()));
            Speech createdSpeech = speechRepository.save(new Speech(speech.getAuthors(),
              speech.getDate(), speech.getActual_speech(), speech.getSubjects()));

            return new ResponseEntity<>(createdSpeech, HttpStatus.CREATED);
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/speech/all")
    public ResponseEntity getAllSpeech() {
        try {
            List<Speech> allSpeech = speechRepository.findAll();
            if (allSpeech.isEmpty()) {
                errorResponse = new ErrorResponse("There are no speeches right now.",
                  HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
            }
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/speech/dateRange/{start}/{end}")
    public ResponseEntity findByDateRange(@PathVariable("start") Date start,
                                                        @PathVariable("end") Date end) {
        try {
            List<Speech> allSpeech = speechRepository.findByDateRange(start, end);
            if(allSpeech.isEmpty()){
                errorResponse =
                  new ErrorResponse("There are no speeches around dates "+start.toString()+" and "+end.toString()+".", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
            }
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/speech/findByActualSpeech/{actualSpeech}")
    public ResponseEntity findByActualSpeech(@PathVariable("actualSpeech") String actualSpeech) {
        try {
            List<Speech> allSpeech = speechRepository.findByActualSpeech(actualSpeech);
            if(allSpeech.isEmpty()){
                errorResponse =
                  new ErrorResponse("There are no speeches with actual speech "+actualSpeech+".",
                    HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
            }
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/speech/findBySubject/{subject}")
    public ResponseEntity findBySubject(@PathVariable("subject") String subject) {
        try {
            Subject subj = checkSubjExist(subject);
            if (subj != null) {
                List<Speech> allSpeech = speechRepository.findBySubjectArea(subj.getId());
                return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
            } else {
                errorResponse = new ErrorResponse("There is no subject with " + subject,
                  HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/speech/findAuthor/{firstname}/{lastname}")
    public ResponseEntity findByAuthor(@PathVariable("firstname") String firstname,
                                       @PathVariable("lastname") String lastname) {
        try {
            Author author = checkAuthorExist(new Author(firstname, lastname));
            if (author != null) {
                List<Speech> allSpeech = speechRepository.findByAuthor(author.getId());
                return new ResponseEntity<>(allSpeech, HttpStatus.FOUND);
            } else {
                errorResponse = new ErrorResponse("There is no Author by the name " + firstname + " " + lastname, HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/speech/{id}")
    public ResponseEntity deleteSpeech(@PathVariable("id") long id) {
        try {
            Speech speech = checkSpeechExist(id);
            if(speech == null){
                errorResponse = new ErrorResponse("There are no speeches with id "+id,
                  HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
            speechRepository.deleteById(id);
            return new ResponseEntity<>("Speech with id " + id + " has been deleted",
              HttpStatus.OK);
        } catch (Exception ex) {
            errorResponse = new ErrorResponse(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/speech/{id}")
    public ResponseEntity updateSpeech(@PathVariable("id") long id,
                                       @RequestBody Speech speech) {
        logger.info("Update Speech");
        try {
            Speech existSpeech = checkSpeechExist(id);
            if(existSpeech == null){
                errorResponse = new ErrorResponse("There are no speeches with id "+id,
                  HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }else{
                speech.setAuthors(saveAuthors(speech.getAuthors()));
                speech.setSubjects(saveSubjects(speech.getSubjects()));
                existSpeech.setActual_speech(speech.getActual_speech());
                existSpeech.setDate(speech.getDate());
                logger.info(String.valueOf(speech.getAuthors().size()));
                existSpeech.setAuthors(speech.getAuthors());
                existSpeech.setSubjects(speech.getSubjects());
                return new ResponseEntity<>(speechRepository.save(existSpeech), HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Update Speech error");
            errorResponse = new ErrorResponse(ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Subject checkSubjExist(String subj) {
        logger.info("Check Subject Exist");
        return subjectRepository.findIfExist(subj);
    }

    private Set<Subject> saveSubjects(Set<Subject> setOfSubj) {
        Set<Subject> newSubjects = new HashSet<>();
        try {
            for (Subject subject : setOfSubj) {
                Subject fromRepoSubj = checkSubjExist(subject.getKeyword());
                if (fromRepoSubj == null) {
                    fromRepoSubj = subjectRepository.save(new Subject(subject.getKeyword()));
                }
                newSubjects.add(fromRepoSubj);
            }
        } catch (Exception ex) {
            logger.error("Save Subjects error");
            logger.error(ex.getMessage());
            return null;
        }
        return newSubjects;
    }

    private Author checkAuthorExist(Author author) {
        logger.info("Check Author Exist");
        logger.info(author.getFirstname() + " " + author.getLastname());
        return authorRepository.findIfExist(author.getFirstname(), author.getLastname());
    }

    private Set<Author> saveAuthors(Set<Author> setOfAuthor) {
        Set<Author> newAuthors = new HashSet<>();
        try {
            for (Author author : setOfAuthor) {
                Author fromRepoAuthor = checkAuthorExist(author);
                if (fromRepoAuthor == null) {
                    logger.info("Empty Author");
                    fromRepoAuthor = authorRepository.save(new Author(author.getFirstname(),
                      author.getLastname()));
                } else {
                    logger.info("notEmpty Author");
                }
                newAuthors.add(fromRepoAuthor);
            }
        } catch (Exception ex) {
            logger.error("Save Authors error");
            return null;
        }
        return newAuthors;
    }

    private Speech checkSpeechExist(long id) {
        logger.info("Check Speech Exist");
        return speechRepository.findIfExist(id);
    }

}
