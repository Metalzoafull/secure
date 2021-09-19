package security.secure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security.secure.security.JwtAuthorizationFilter;
import security.secure.service.Impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    //@Autowired
    //private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;


    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(myUserDetailService);
        auth
                .userDetailsService(userService).passwordEncoder(passwordEncoder.passwordEncoder()).and()
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, deleted "
                        + "FROM users "
                        + "WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT email, roles "
                        + "FROM users"
                        + "WHERE email = ?");    }

     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication()
                .dataSource(dataSource);
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //authenticationProvider.setUserDetailsService();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

     */


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/create").permitAll()
                .antMatchers("/login").permitAll()
                //.antMatchers("/testing").permitAll()
                //.antMatchers("/database/populate").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public JwtAuthorizationFilter authenticationTokenFIlterBean() throws Exception {
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

     */
    /*@Bean
    public AuthenticationJwtTokenFilter authenticationTokenFIlterBean() throws Exception {
        AuthenticationJwtTokenFilter filter = new AuthenticationJwtTokenFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

     */
}
