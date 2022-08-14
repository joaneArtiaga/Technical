package com.example.speech.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "speech")
public class Speech{
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id;

 @Column(name = "date")
 private Date date;

 @Column(name = "actual_speech")
 private String actual_speech;

 @ManyToMany(cascade = { CascadeType.ALL })
 @JoinTable(
   name = "speech_subject",
   joinColumns = { @JoinColumn(name = "speech_id") },
   inverseJoinColumns = { @JoinColumn(name = "subject_id") }
 )
 private Set<Subject> subjects;

 @ManyToMany(cascade = { CascadeType.ALL })
 @JoinTable(
   name = "speech_author",
   joinColumns = { @JoinColumn(name = "speech_id") },
   inverseJoinColumns = { @JoinColumn(name = "author_id") }
 )
 private Set<Author> authors;

 public Speech(){

 }

 public Speech(Set<Author> authors, Date date, String actual_speech, Set<Subject> subjects){
  this.authors = authors;
  this.date = date;
  this.actual_speech = actual_speech;
  this.subjects = subjects;
 }

 public void setId(long id) {
  this.id = id;
 }

 public long getId() {
  return id;
 }

 public Set<Author> getAuthors() {
  return authors;
 }

 public void setAuthors(Set<Author> authors) {
  this.authors = authors;
 }

 public Date getDate() {
  return date;
 }

 public void setDate(Date date) {
  this.date = date;
 }

 public String getActual_speech() {
  return actual_speech;
 }

 public void setActual_speech(String actual_speech) {
  this.actual_speech = actual_speech;
 }

 public void setSubjects(Set<Subject> subjects) {
  this.subjects = subjects;
 }

 public Set<Subject> getSubjects() {
  return subjects;
 }
}
