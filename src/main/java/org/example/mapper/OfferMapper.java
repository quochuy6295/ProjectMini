package org.example.mapper;

import org.example.dto.OfferInfo;
import org.example.entity.Offer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 23 / Nov / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface OfferMapper {

    OfferMapper INSTANCE =  Mappers.getMapper(OfferMapper.class);
    Offer offerInfoToOffer(OfferInfo offerInfo);
    @InheritInverseConfiguration
    OfferInfo offerToOfferInfo(Offer offer);
}
