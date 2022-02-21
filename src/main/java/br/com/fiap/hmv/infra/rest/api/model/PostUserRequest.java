package br.com.fiap.hmv.infra.rest.api.model;

import lombok.Getter;

@Getter
public class PostUserRequest {

    private String username;
    private String taxId;
    private String email;
    private String fullName;
    private String password;

}