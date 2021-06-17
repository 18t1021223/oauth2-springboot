package com.vn;

import com.vn.entity.OauthClientDetails;
import com.vn.service.OauthClientDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityOauthAuthorizationServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOauthAuthorizationServerApplication.class, args);
    }

    @Autowired
    private OauthClientDetailRepo oauthClientDetailRepo;

    /*
    INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('fooClientIdPassword', '$2a$10$F2dXfNuFjqezxIZp0ad5OeegW43cRdSiPgEtcetHspiNrUCi3iI6O', 'foo,read,write',
	'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true);
     */
    @Override
    public void run(String... args) throws Exception {
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId("fooClientIdPassword");
        oauthClientDetails.setClientSecret("$2a$10$F2dXfNuFjqezxIZp0ad5OeegW43cRdSiPgEtcetHspiNrUCi3iI6O");
        oauthClientDetails.setScope("foo,read,write");
        oauthClientDetails.setAuthorized_grant_types("password,authorization_code,refresh_token,client_credentials");
        oauthClientDetails.setAccess_token_validity(36000);
        oauthClientDetails.setRefresh_token_validity(36000);
        oauthClientDetails.setAutoApprove(true);

        OauthClientDetails oauthClientDetails2 = new OauthClientDetails();
        oauthClientDetails2.setClientId("sampleClientId");
        oauthClientDetails2.setClientSecret("$2a$10$F2dXfNuFjqezxIZp0ad5OeegW43cRdSiPgEtcetHspiNrUCi3iI6O");
        oauthClientDetails2.setScope("read,write,foo,bar");
        oauthClientDetails2.setAuthorized_grant_types("implicit");
        oauthClientDetails2.setAccess_token_validity(36000);
        oauthClientDetails2.setRefresh_token_validity(36000);
        oauthClientDetails2.setAutoApprove(false);

        OauthClientDetails oauthClientDetails3 = new OauthClientDetails();
        oauthClientDetails3.setClientId("barClientIdPassword");
        oauthClientDetails3.setClientSecret("$2a$10$F2dXfNuFjqezxIZp0ad5OeegW43cRdSiPgEtcetHspiNrUCi3iI6O");
        oauthClientDetails3.setScope("bar,read,write");
        oauthClientDetails3.setAuthorized_grant_types("password,authorization_code,refresh_token");
        oauthClientDetails3.setAccess_token_validity(36000);
        oauthClientDetails3.setRefresh_token_validity(36000);
        oauthClientDetails3.setAutoApprove(true);

        oauthClientDetailRepo.save(oauthClientDetails);
        oauthClientDetailRepo.save(oauthClientDetails2);
        oauthClientDetailRepo.save(oauthClientDetails3);

    }
}
