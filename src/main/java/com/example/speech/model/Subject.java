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

 public String getKeyword() {
  return keyword;
 }

 public void setKeyword(String keyword) {
  this.keyword = keyword;
 }
}
