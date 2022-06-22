//package com.joc.todo.study.factory;
//
//import com.joc.todo.study.LoginType;
//import com.joc.todo.study.service.GoogleLoginService;
//import com.joc.todo.study.service.LoginService;
//import com.joc.todo.study.service.MobileLoginService;
//import com.joc.todo.study.service.WebLoginService;
//import lombok.RequiredArgsConstructor;
//
//import java.util.NoSuchElementException;
//
//
//@RequiredArgsConstructor
//public class LoginServiceFactoryV1 implements LoginServiceFactory {
//
//    private final WebLoginService webLoginService;
//    private final MobileLoginService mobileLoginService;
//    private final GoogleLoginService googleLoginService;
//
//    @Override
//    public LoginService find(LoginType loginType) {
//
//        if (loginType == LoginType.WEB) {
//            return webLoginService;
//        }
//        if (loginType == LoginType.MOBILE) {
//            return mobileLoginService;
//        }
//        if (loginType == LoginType.GOOGLE) {
//            return googleLoginService;
//        }
//        throw new NoSuchElementException("노 써치 엘레멘트 익셉션 ~  : " + loginType);
//    }
//}
//
//
