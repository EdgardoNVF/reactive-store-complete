package com.bancolombia.reactive_store.exception;

public class ProductNotFoundException extends RuntimeException{
   public ProductNotFoundException(String message){
       super(message);
   }
}
