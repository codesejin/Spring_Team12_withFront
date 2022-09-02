package com.example.spring_team12_withfront.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//secured 어노테이션 활성화 : securedEnabled
//preAuthorized 어노테이션 활성화 :prePostEnabled
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록된다
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //@Bean을 적으면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
    @Bean
    public BCryptPasswordEncoder encoderPwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csrf 회원가입 post할 때 자꾸 403에러 발생
        //참고 사이트 : https://swk3169.tistory.com/entry/Web-CSRFCross-Site-Request-Forgery-%EA%B3%B5%EA%B2%A9-%EA%B8%B0%EB%B2%95
        http.csrf().disable();
        http.authorizeRequests()
                //해당 엔드포인트를 들어가려면 인증이 필요하다 : 로그인 한사람만 들어올 수 있음
                .antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
                //access권한 : 로그인했지만, admin이나 manager권한이 있어야 입장가능
                .antMatchers("/manager/**")
                .access("hasRole('ROLE_ADMIN')or hasRole('ROLE_MANAGER')")
                //로그인했지만 admin권한만 들어갈 수 있다
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                //위에 3개가 아닌 이상 모든 권한이 해당됨
                .anyRequest().permitAll()
                //권한이 없을 경우, user,manager,admin페이지로 갈때 forbidden으로 막히지 않게
                //loginPage함수로 login페이지로 이동 시킴(view 있을때 활용)
                .and()
                .formLogin().disable();
    }
}