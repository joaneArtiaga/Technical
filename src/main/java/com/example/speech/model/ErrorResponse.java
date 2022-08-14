package com.example.speech.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ErrorResponse {
 @JsonProperty("Error_Code")
 private int errorCode;

 @JsonProperty("Error_Message")
 private String errorMessage;

 public ErrorResponse(String errorMessage, int errorCode){
  this.errorCode = errorCode;
  this.errorMessage = errorMessage;
 }

 public ErrorResponse(){}
}
