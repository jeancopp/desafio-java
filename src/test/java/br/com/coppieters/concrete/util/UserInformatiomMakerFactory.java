package br.com.coppieters.concrete.util;

import br.com.coppieters.concrete.service.maker.UserInformationMaker;
import br.com.coppieters.concrete.service.maker.UserMaker;
import br.com.coppieters.concrete.service.maker.UserPhoneMaker;

public class UserInformatiomMakerFactory {

    public static UserInformationMaker produce(){
        UserPhoneMaker phoneMaker = new UserPhoneMaker();
        UserMaker userMaker = new UserMaker(phoneMaker);
        return new UserInformationMaker(userMaker);
    }
}
