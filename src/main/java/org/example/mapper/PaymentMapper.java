package org.example.mapper;

import org.example.dto.PaymentInfo;
import org.example.entity.Payment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(implementationPackage = "mapper.impl")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentInfoToPayment(PaymentInfo paymentInfo);

    @InheritInverseConfiguration
    PaymentInfo paymentToPaymentInfo(Payment payment);
}
