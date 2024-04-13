package org.example.common;

import org.example.common.staticdata.CodeType;
import org.example.entity.AppEntityCode;
import org.example.repository.AppEntityCodeRepository;
import org.example.repository.SystemCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeGenerator {

    @Autowired
    private AppEntityCodeRepository appEntityCodeRepository;

    public String newCode(CodeType codeType) {
        String code = "";
        AppEntityCode appEntityCode = null;
        do {
            String newCode = getCode(codeType.name());
            appEntityCode = appEntityCodeRepository.findByCode(newCode);
            if (appEntityCode == null) {
                code = newCode;
            }
        } while (appEntityCode != null);
        appEntityCode = new AppEntityCode();
        appEntityCode.setCode(code);
        appEntityCode.setCodeType(codeType);
        appEntityCodeRepository.save(appEntityCode);
        return appEntityCode.getCode();
    }

    private String getCode(String codeType) {
        Random random = new Random(System.currentTimeMillis());
        int id = ((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));
        String codePrefix = "123";
        return codePrefix + id;
    }
}
