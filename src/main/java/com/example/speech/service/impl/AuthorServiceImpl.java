package com.example.speech.service.impl;

import com.example.speech.controller.SpeechController;
import com.example.speech.exception.SpeechCustomException;
import com.example.speech.model.Author;
import com.example.speech.repository.AuthorRepository;
import com.example.speech.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Override
    public Set<Author> saveAuthors(Set<Author> setOfAuthor) {
        logger.info("Saving authors...");
        Set<Author> newAuthors = new HashSet<>();
        try {
            for (Author author : setOfAuthor) {
                Author fromRepoAuthor = checkAuthorExist(author);
                if (fromRepoAuthor == null) {
                    fromRepoAuthor = authorRepository.save(new Author(author.getFirstname(),
                      author.getLastname()));
                }
                newAuthors.add(fromRepoAuthor);
            }
        } catch (Exception ex) {
            logger.error("Save Authors error");
            throw new SpeechCustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return newAuthors;
    }

    public Author checkAuthorExist(Author author) {
        logger.info("Check Author Exist");
        logger.info(author.getFirstname() + " " + author.getLastname());
        return authorRepository.findIfExist(author.getFirstname(), author.getLastname());
    }
}
