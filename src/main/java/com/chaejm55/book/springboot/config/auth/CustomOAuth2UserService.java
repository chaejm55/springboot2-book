package com.chaejm55.book.springboot.config.auth;

import com.chaejm55.book.springboot.config.auth.dto.OAuthAttributes;
import com.chaejm55.book.springboot.config.auth.dto.SessionUser;
import com.chaejm55.book.springboot.domain.user.User;
import com.chaejm55.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delgate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delgate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인 진행 중인 서비스를 구분하는 코드(구글만 사용)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 로그인 키가 진행 되는 필드값
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // OAuth2User의 Attribute를 담음

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보를 저장하기 위한 Dto(직렬화 문제 때문)

        return new DefaultOAuth2User(
                Collections.singleton(new
                        SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey()
                );
    }

    private User saveOrUpdate(OAuthAttributes attributes) { // 사용자 정보 업데이트 대비
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
