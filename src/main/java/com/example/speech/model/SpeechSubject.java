package com.example.speech.model;

import javax.persistence.*;

@Entity
@Table(name = "speech_subject")
public class SpeechSubject {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id;
 @ManyToOne
 @JoinColumn(name = "speech_id")
 private Speech speech;
 @ManyToOne
 @JoinColumn(name = "subject_id")
 private Subject subject;

 public SpeechSubject(){}

 public SpeechSubject(Speech speech, Subject subject){
     this.speech = speech;
     this.subject = subject;
 }

 public Speech getSpeech() {
  return speech;
 }

 public void setSpeech(Speech speech) {
  this.speech = speech;
 }

 public Subject getSubject() {
  return subject;
 }

 public void setSubject(Subject subject) {
  this.subject = subject;
 }
}
