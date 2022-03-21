package br.com.fiap.hmv.infra.rest.api.v1.mapper;

import br.com.fiap.hmv.domain.entity.Address;
import br.com.fiap.hmv.infra.rest.api.v1.model.AddressModel;

import static java.util.Objects.nonNull;

public class AddressModelMapper {

    public static AddressModel toAddressModel(Address address) {
        return nonNull(address) ? AddressModel.builder()
                .zipcode(address.getZipcode())
                .publicPlace(address.getPublicPlace())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .build() : null;
    }

    public static Address toAddress(AddressModel model) {
        return nonNull(model) ? Address.builder()
                .zipcode(model.getZipcode())
                .publicPlace(model.getPublicPlace())
                .number(model.getNumber())
                .complement(model.getComplement())
                .neighborhood(model.getNeighborhood())
                .city(model.getCity())
                .state(model.getState())
                .build() : null;
    }

}