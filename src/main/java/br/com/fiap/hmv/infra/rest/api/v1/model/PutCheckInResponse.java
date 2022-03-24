package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@ApiModel("PutCheckInResponseV1")
@Getter
@SuperBuilder
public class PutCheckInResponse extends PostCheckInResponse {

}