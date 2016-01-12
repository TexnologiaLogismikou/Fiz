package com.tech.configurations.tools.customvalidators.elements;

import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;

/**
 * Created by Aenaos on 12/1/2016.
 */
public class EmptyValidator implements ICustomValidator {

    @Override
    public void setNext(ICustomValidator next) throws InappropriateValidatorException {

    }

    @Override
    public ICustomValidator getNext() {
        return null;
    }

    @Override
    public void replaceNext(ICustomValidator next) throws InappropriateValidatorException {

    }

    @Override
    public String getName() {
        return null;
    }
}
