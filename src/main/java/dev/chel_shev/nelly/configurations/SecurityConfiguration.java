package dev.chel_shev.nelly.configurations;

//@Configuration
//@EnableConfigurationProperties
//@RequiredArgsConstructor
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final JwtRequestFilter jwtRequestFilter;
//    private final JwtUserDetailsService jwtUserDetailsService;
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/user/signup", "/dashboard");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().antMatchers("/authenticate").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .apply(new JwtConfigurer(jwtRequestFilter));
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.parentAuthenticationManager(authenticationManagerBean())
//                .userDetailsService(jwtUserDetailsService);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}