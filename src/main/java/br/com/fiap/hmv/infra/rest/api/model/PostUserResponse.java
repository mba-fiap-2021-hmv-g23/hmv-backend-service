package br.com.fiap.hmv.infra.rest.api.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUserResponse {

    private String userId;

}