package br.com.fiap.hmv.infra.rest.api.model;

import lombok.Getter;

@Getter
public class PostLoginRequest {

    private String login;
    private String password;

}