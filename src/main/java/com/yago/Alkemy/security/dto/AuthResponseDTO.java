package com.yago.Alkemy.security.dto;

public class AuthResponseDTO {

  private String token;

  public AuthResponseDTO(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
