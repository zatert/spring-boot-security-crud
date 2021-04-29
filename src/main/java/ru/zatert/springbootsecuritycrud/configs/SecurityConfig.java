package ru.zatert.springbootsecuritycrud.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.zatert.springbootsecuritycrud.configs.handler.LoginSuccessHandler;
import ru.zatert.springbootsecuritycrud.services.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                //разбивка по ролям для реста
//            .antMatchers(HttpMethod.GET, "/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
//            .antMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
//            .antMatchers(HttpMethod.DELETE, "/**").hasAnyRole(Role.ADMIN.name())
                // любой запрос только аунтифицированный
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
               // .defaultSuccessUrl("/auth/hello");
                .loginProcessingUrl("/login")
                .successHandler(new LoginSuccessHandler())
                .usernameParameter("username")
                .passwordParameter("password") // везде 100
                .permitAll();
//                .and()
//                .logout().logoutSuccessUrl("/");
                //.loginProcessingUrl("/login")
            //.permitAll();
                // будем пускать только аунтофицированных пользоватлей
//            .antMatchers("/auth/**").authenticated()
//                // админка
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .and()
//                // базовая аутентификация
//            //.httpBasic()
//                // навигация на свою сверстанную форму, либо спринговая по умолчанию
//            .formLogin()
//            .loginPage("/login")
//                // после логина отправляем на логику саксессХендлера
//            .successHandler(new LoginSuccessHandler())
//                // указываем action с формы логина
//            .loginProcessingUrl("/login")
//            .permitAll()
//            .and()
//                // идем в корень
//            .logout().logoutSuccessUrl("/");

    }

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                    .username("admin")
//                    .password(passwordEncoder().encode("admin"))
//                    .roles(Role.ADMIN.name())
//                    .build(),
//                User.builder()
//                    .username("user")
//                    .password(passwordEncoder().encode("user"))
//                    .roles(Role.USER.name())
//                    .build()
//        );
//    }


    // его задача выяснить существует ли пользователь и если да, то положить в спрингСекьюретиКонтекст
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(12);
    }
}
