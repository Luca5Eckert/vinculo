package com.vinculo.module.person.infrastructure.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.vinculo.module.person.domain.port.PhoneNumberValidator;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberValidatorAdapter implements PhoneNumberValidator {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }

        try {
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, "BR");
            return phoneNumberUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            return false;
        }
    }

}