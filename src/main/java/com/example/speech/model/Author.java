package com.example.speech.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id;
 @Column(name = "firstname")
 private String firstname;
 @Column(name = "lastname")
 private String lastname;

 public Author(){

 }

 public Author(String firstname, String lastname){
  this.firstname = firstname;
  this.lastname = lastname;
 }

 public void setFirstname(String firstname) {
  this.firstname = firstname;
 }

 public String getFirstname() {
  return firstname;
 }

 public void setLastname(String lastname) {
  this.lastname = lastname;
 }

 public String getLastname() {
  return lastname;
 }

 public void setId(long id) {
  this.id = id;
 }

 public long getId() {
  return id;
 }
}
