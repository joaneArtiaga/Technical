package com.example.speech.service.impl;

import com.example.speech.exception.SpeechCustomException;
import com.example.speech.model.Subject;
import com.example.speech.repository.SubjectRepository;
import com.example.speech.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.HashSet;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Override
    public Set<Subject> saveSubjects(Set<Subject> setOfSubj) {
        logger.info("Saving subjects...");
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
            throw new SpeechCustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return newSubjects;
    }

    public Subject checkSubjExist(String subj) {
        logger.info("Check Subject Exist");
        return subjectRepository.findIfExist(subj);
    }
}
