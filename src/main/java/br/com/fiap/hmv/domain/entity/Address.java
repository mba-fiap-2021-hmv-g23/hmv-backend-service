package br.com.fiap.hmv.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {

    private String zipcode;
    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;

}