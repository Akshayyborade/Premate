/*******************************************************************************
 * Premate- School Management System © 2024 by Akshay Borade is licensed under CC BY-NC-SA 4.0 
 *******************************************************************************/
package com.Premate.util;

import com.Premate.Model.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

//AddressConverter class
@Converter
public class AddressConverter implements AttributeConverter<String, Address> {

 @Override
 public Address convertToDatabaseColumn(String attribute) {
     // Convert JSON string to Address object
     ObjectMapper objectMapper = new ObjectMapper();
     try {
         return objectMapper.readValue(attribute, Address.class);
     } catch (JsonProcessingException e) {
         // Handle exception
         return null;
     }
 }

 @Override
 public String convertToEntityAttribute(Address dbData) {
     // Convert Address object to JSON string
     ObjectMapper objectMapper = new ObjectMapper();
     try {
         return objectMapper.writeValueAsString(dbData);
     } catch (JsonProcessingException e) {
         // Handle exception
         return null;
     }
 }
}

