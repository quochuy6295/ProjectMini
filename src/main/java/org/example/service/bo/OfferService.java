package org.example.service.bo;

import org.example.common.CodeGenerator;
import org.example.common.staticdata.CodeType;
import org.example.common.staticdata.Constants;
import org.example.common.staticdata.STATUS;
import org.example.dto.OfferInfo;
import org.example.entity.Offer;
import org.example.mapper.OfferMapper;
import org.example.message.ResponseMessage;
import org.example.repository.OfferRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Suresh Stalin on 23 / Nov / 2020.
 */

@Service
public class OfferService extends BaseService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    public ResponseMessage save(OfferInfo offerInfo) {

        Offer offer = OfferMapper.INSTANCE.offerInfoToOffer(offerInfo);
        if(StringUtils.isEmpty(offer.getOfferCode())) {
            String offerCode = codeGenerator.newCode(CodeType.OFFER_CODE);
            offer.setOfferCode(offerCode);
            offer.setStatus(STATUS.PENDING.name());
        }
        Offer newOffer = offerRepository.save(offer);
        OfferInfo newOfferInfo = OfferMapper.INSTANCE.offerToOfferInfo(newOffer);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(newOfferInfo, "Offer saved successfully", Constants.INFO_TYPE);
        return responseMessage;
    }

    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        Offer offer = offerRepository.findById(Long.parseLong(id)).orElse(null);
        OfferInfo offerInfo = OfferMapper.INSTANCE.offerToOfferInfo(offer);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(offerInfo, Constants.SUCCESS_STATUS, Constants.INFO_TYPE);
        return responseMessage;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        List<OfferInfo> offerInfos = new ArrayList<>();
        List<Offer> offers = offerRepository.findAll();
        for (Offer offer : offers) {
            OfferInfo offerInfo = OfferMapper.INSTANCE.offerToOfferInfo(offer);
            offerInfos.add(offerInfo);
        }
        ResponseMessage responseMessage = ResponseMessage.withResponseData(offerInfos, Constants.SUCCESS_STATUS, Constants.INFO_TYPE);
        return responseMessage;
    }
}
