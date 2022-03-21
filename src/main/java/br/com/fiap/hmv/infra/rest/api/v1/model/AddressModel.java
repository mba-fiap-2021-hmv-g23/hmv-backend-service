package br.com.fiap.hmv.infra.rest.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "AddressModelV1")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {

    @ApiModelProperty(value = "CEP.", required = true)
    private String zipcode;

    @ApiModelProperty(value = "Logradouro.", required = true)
    private String publicPlace;

    @ApiModelProperty(value = "Número.", required = true)
    private String number;

    @ApiModelProperty(value = "Complemento.")
    private String complement;

    @ApiModelProperty(value = "Bairro.")
    private String neighborhood;

    @ApiModelProperty(value = "Cidade.")
    private String city;

    @ApiModelProperty(value = "Estado.")
    private String state;

}