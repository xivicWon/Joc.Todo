package com.joc.todo.study.factory;

import com.joc.todo.study.LoginType;
import com.joc.todo.study.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class LoginServiceFactoryV3 implements LoginServiceFactory {

    private final List<LoginService> loginServices;
    // 1. new 하지 않으면 LinkedHashMap
    // 2. 일반적으론 HashMap 을 사용
    // 3. Map의 Key 가 Enum 인 경우, HashMap 대신 EnumMap 을 추천
    //    private final Map<LoginType, LoginService> cachedLoginServiceMap = new EnumMap<>(LoginType.class);

    // 오류 발견을 어느때 발견해야 하는지에 대한 순서.
    //  1. 컴파일 시
    //  2. 애플리케이션 로딩 시
    //  3. 런타임 시 ( 최악 )


    @PostConstruct
    void postConstruct() {
        EnumSet<LoginType> loginTypes = EnumSet.allOf(LoginType.class);
        loginTypes.forEach(loginType -> {
            try {
                LoginServiceCache.put(loginType, getLoginService(loginType));
            } catch (NoSuchElementException e) {
                log.warn("Cannot find LoginService of : {}", loginType);
            }
        });
    }

    @Override
    public LoginService find(LoginType loginType) {
        return LoginServiceCache.get(loginType)
                .orElseThrow(() -> new NoSuchElementException("find : 노 써치 엘레멘트 익셉션 ~  : " + loginType));
    }

    private LoginService getLoginService(LoginType loginType) {
        return loginServices.stream()
                .filter(service -> service.supports(loginType))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("getLoginService : 노 써치 엘레멘트 익셉션 ~  : " + loginType));
    }

    private static class LoginServiceCache {

        private static final Map<LoginType, LoginService> loginServiceMap = new EnumMap<>(LoginType.class);

        public static Optional<LoginService> get(LoginType loginType) {
            return Optional.ofNullable(loginServiceMap.get(loginType));
        }

//        public static LoginService get(LoginType loginType) {
//            return loginServiceMap.get(loginType);
//        }

        public static void put(LoginType loginType, LoginService loginService) {
            loginServiceMap.put(loginType, loginService);
        }
    }
}


