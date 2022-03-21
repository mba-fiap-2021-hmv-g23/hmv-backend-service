package br.com.fiap.hmv.infra.mongodb.mapper;

import br.com.fiap.hmv.domain.entity.Address;
import br.com.fiap.hmv.infra.mongodb.entity.AddressEntity;

import static java.util.Objects.nonNull;

public class AddressEntityMapper {

    public static AddressEntity toAddressEntity(Address address) {
        return nonNull(address) ? AddressEntity.builder()
                .zipcode(address.getZipcode())
                .publicPlace(address.getPublicPlace())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .build() : null;
    }

    public static Address toAddress(AddressEntity entity) {
        return nonNull(entity) ? Address.builder()
                .zipcode(entity.getZipcode())
                .publicPlace(entity.getPublicPlace())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .neighborhood(entity.getNeighborhood())
                .city(entity.getCity())
                .state(entity.getState())
                .build() : null;
    }

}