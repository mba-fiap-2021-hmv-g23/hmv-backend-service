package br.com.fiap.hmv.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {

    String zipcode;
    String publicPlace;
    String number;
    String complement;
    String neighborhood;
    String city;
    String state;

}