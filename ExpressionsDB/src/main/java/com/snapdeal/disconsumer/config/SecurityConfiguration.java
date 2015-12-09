package com.snapdeal.disconsumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebMvcSecurity
@ImportResource("classpath:/security/spring_saml_sso_security.xml")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	MetadataGeneratorFilter metadataGeneratorFilter;
	@Autowired
	FilterChainProxy samlFilter;
	@Autowired
	SAMLEntryPoint samlEntryPoint;
	
	
	
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
		ProviderManager pm=(ProviderManager)authenticationManager();
		pm.setEraseCredentialsAfterAuthentication(false);
		authenticationManagerBuilder=authenticationManagerBuilder.eraseCredentials(false);
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
		
		/*auth.inMemoryAuthentication().withUser("User").password("password").roles("ADMIN");*/
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
  
		http.httpBasic().authenticationEntryPoint(samlEntryPoint);
		http.addFilterBefore(metadataGeneratorFilter, ChannelProcessingFilter.class).addFilterAfter(samlFilter, BasicAuthenticationFilter.class);
		
		/*http
        .authorizeRequests()
            .anyRequest().authenticated() 
            .and()
        .formLogin()                      
            .and()
        .httpBasic();*/
    }
}
