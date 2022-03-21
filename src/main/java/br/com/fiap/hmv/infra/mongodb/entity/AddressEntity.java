package br.com.fiap.hmv.infra.mongodb.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressEntity {

    private String zipcode;
    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;

}