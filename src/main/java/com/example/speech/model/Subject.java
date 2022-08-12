package com.example.speech.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
public class Subject {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id;
 @Column(name = "keyword")
 private String keyword;

 @OneToMany(mappedBy = "subject")
 private Set<SpeechSubject> speechSubjectSet = new HashSet<>();

 public Subject(){

 }

 public Subject(String keyword){
  this.keyword = keyword;
 }

 public void setId(long id) {
  this.id = id;
 }

 public long getId() {
  return id;
 }

 public String getAuthor() {
  return keyword;
 }

 public void setAuthor(String keyword) {
  this.keyword = keyword;
 }

 public void setSpeechSubjectSet(Set<SpeechSubject> speechSubjectSet) {
  this.speechSubjectSet = speechSubjectSet;
 }

 public Set<SpeechSubject> getSpeechSubjectSet() {
  return speechSubjectSet;
 }
}
