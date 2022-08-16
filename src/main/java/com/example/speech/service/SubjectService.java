package com.example.speech.service;

import com.example.speech.model.Subject;

import java.util.Set;

public interface SubjectService {
    public Set<Subject> saveSubjects(Set<Subject> setOfSubj);
    public Subject checkSubjExist(String subj);
}
