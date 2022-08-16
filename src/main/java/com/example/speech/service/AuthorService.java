package com.example.speech.service;

import com.example.speech.model.Author;

import java.util.Set;

public interface AuthorService {
    public Set<Author> saveAuthors(Set<Author> setOfAuthor);
    public Author checkAuthorExist(Author author);
}
