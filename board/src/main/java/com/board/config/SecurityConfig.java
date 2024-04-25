package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //spring security filterChanin 이 자동으로 포함되는 클래스를 만들어준다. (스프링 시큐리티를 활성화하고 웹 보안 설정을 구성하는 데 사용됩니다)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        //1. 페이지 접근에 관한 설정(인가 - 로그인 이후에)
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/fonts/**").permitAll()
                        .requestMatchers("/", "/members/**", "/item/**").permitAll()
                        .requestMatchers("/favicon.ico", "/error").permitAll()
                        //관리자만 접근가능하도록 설정(인가)
                        //.requestMatchers("/admin/**").hasRole("ADMIN") //hasRole: 관리자인지 회원이지 구분함.
                        //그 외 페이지는 모두 로그인(인증)을 해야한다.
                        .anyRequest().authenticated() //CustomAuthenticationEntryPoint 클래스에 있는 commence() 메소드를 실행
                )
                //2. 로그인에 관한 설정
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login") //로그인 페이지 URL
                        .defaultSuccessUrl("/") //로그인 성공시 이동할 페이지
                        .usernameParameter("email") //★로그인시 id로 사용할 파라미터 이름
                        .failureUrl("/members/login/error") //로그인 실패시 이동할 페이지
                )
                //3. 로그아웃에 관한 설정
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃시 이동할 URL
                        .logoutSuccessUrl("/") //로그아웃 성공시 이동할 URL
                )
                //4. 인증되지 않은 사용자가 리소스에 접근시 설정
                .exceptionHandling(handing -> handing
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .rememberMe(Customizer.withDefaults()); //로그인 이후 세션을 통해 로그인을 유지

        return httpSecurity.build();
    }

    @Bean //인스턴스화 된 객체를 의미함.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //암호화 로직이 되어 있는 코드
    }
}
