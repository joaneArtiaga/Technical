package com.example.speech.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "speech")
public class Speech {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id;
 @Column(name = "author")
 private String author;
 @Column(name = "date")
 private Date date;
 @Column(name = "actual_speech")
 private String actual_speech;

 @OneToMany(mappedBy = "speech")
 private Set<SpeechSubject> speechSubjectSet = new HashSet<>();

 public Speech(){

 }

 public Speech(String author, Date date, String actual_speech){
  this.author = author;
  this.date = date;
  this.actual_speech = actual_speech;
 }

 public void setId(long id) {
  this.id = id;
 }

 public long getId() {
  return id;
 }

 public String getAuthor() {
  return author;
 }

 public void setAuthor(String author) {
  this.author = author;
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

 public Set<SpeechSubject> getSpeechSubjectSet() {
  return speechSubjectSet;
 }

 public void setSpeechSubjectSet(Set<SpeechSubject> speechSubjectSet) {
  this.speechSubjectSet = speechSubjectSet;
 }
}
