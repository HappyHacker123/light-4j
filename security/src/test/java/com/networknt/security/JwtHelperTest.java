/*
 * Copyright (c) 2016 Network New Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.networknt.security;

import com.networknt.config.Config;
import com.networknt.utility.Constants;
import org.jose4j.jwt.JwtClaims;
import org.junit.Assert;
import org.junit.Test;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 01/09/16.
 */
public class JwtHelperTest {
    @Test
    public void testReadCertificate() {
        Map<String, Object> securityConfig = Config.getInstance().getJsonMapConfig(JwtHelper.SECURITY_CONFIG);
        Map<String, Object> jwtConfig = (Map<String, Object>)securityConfig.get(JwtHelper.JWT_CONFIG);
        Map<String, Object> keyMap = (Map<String, Object>) jwtConfig.get(JwtHelper.JWT_CERTIFICATE);
        Map<String, X509Certificate> certMap = new HashMap<>();
        for(String kid: keyMap.keySet()) {
            X509Certificate cert = null;
            try {
                cert = JwtHelper.readCertificate((String)keyMap.get(kid));
            } catch (Exception e) {
                e.printStackTrace();
            }
            certMap.put(kid, cert);
        }
        Assert.assertEquals(2, certMap.size());
    }

    @Test
    public void longLivedAPIAJwt() throws Exception {
        JwtClaims claims = getTestClaims("Steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("api_a.w", "api_b.w", "api_c.w", "api_d.w", "server.info.r"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived APIA JWT***: " + jwt);
    }

    @Test
    public void longLivedATMP1000Jwt() throws Exception {
        JwtClaims claims = getTestClaims("eric", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("ATMP1000.w", "ATMP1000.r"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived ATMP1000 JWT***: " + jwt);
    }


    @Test
    public void longLivedPetStoreJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("write:pets", "read:pets"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived PetStore JWT***: " + jwt);
    }

    @Test
    public void longLivedTrainingJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("training.accounts.read", "training.customers.read", "training.myaccounts.read", "training.transacts.read"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived Training JWT***: " + jwt);
    }

    @Test
    public void longLivedHelloWorldJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("world.r", "world.w", "server.info.r"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived HelloWorld JWT***: " + jwt);
    }

    @Test
    public void longLivedCodegenJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("codegen.r", "codegen.w", "server.info.r"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived Codegen JWT***: " + jwt);
    }

    @Test
    public void longLivedReferenceJwt() throws Exception {
        Map<String, String> custom = new HashMap<>();
        custom.put("consumer_application_id", "361");
        custom.put("request_transit", "67");
        JwtClaims claims = getCustomClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("party.util.reference.read", "server.info.r"), custom);
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived reference JWT***: " + jwt);
    }

    @Test
    public void longLivedProductSubjectJwt() throws Exception {
        Map<String, String> custom = new HashMap<>();
        custom.put("consumer_application_id", "361");
        custom.put("request_transit", "67");
        JwtClaims claims = getCustomClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", null, custom);
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived product subject JWT***: " + jwt);
    }

    @Test
    public void longLivedProductAccessJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("party.product.read", "server.info.r"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***LongLived product access JWT***: " + jwt);
    }

    @Test
    public void normalPetStoreJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("write:pets", "read:pets"));
        claims.setExpirationTimeMinutesInTheFuture(10);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***JWT***: " + jwt);
    }

    @Test
    public void longlivedTransferJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("etransfer.r", "etransfer.w"));
        claims.setExpirationTimeMinutesInTheFuture(5256000);
        String jwt = JwtHelper.getJwt(claims);
        System.out.println("***Long lived token for etransfer***: " + jwt);
    }

    private JwtClaims getTestClaims(String userId, String userType, String clientId, List<String> scope) {
        JwtClaims claims = JwtHelper.getDefaultJwtClaims();
        claims.setClaim("user_id", userId);
        claims.setClaim("user_type", userType);
        claims.setClaim("client_id", clientId);
        if(scope != null) claims.setStringListClaim("scope", scope); // multi-valued claims work too and will end up as a JSON array
        return claims;
    }

    private JwtClaims getCustomClaims(String userId, String userType, String clientId, List<String> scope, Map<String, String> custom) {
        JwtClaims claims = JwtHelper.getDefaultJwtClaims();
        claims.setClaim("user_id", userId);
        claims.setClaim("user_type", userType);
        claims.setClaim("client_id", clientId);
        custom.forEach((k, v) -> claims.setClaim(k, v));
        if(scope != null) claims.setStringListClaim("scope", scope); // multi-valued claims work too and will end up as a JSON array
        return claims;
    }

    @Test
    public void testVerifyJwt() throws Exception {
        JwtClaims claims = getTestClaims("steve", "EMPLOYEE", "f7d42348-c647-4efb-a52d-4c5787421e72", Arrays.asList("write:pets", "read:pets"));
        String jwt = JwtHelper.getJwt(claims);
        claims = null;
        Assert.assertNotNull(jwt);
        try {
            claims = JwtHelper.verifyJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(claims);
        Assert.assertEquals("steve", claims.getStringClaimValue(Constants.USER_ID_STRING));

        try {
            claims = JwtHelper.verifyJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("jwtClaims = " + claims);
    }

    /**
     * This test needs light-oauth2 service to be up and running in order to test it
     * to start the light-oauth2 please refer to https://networknt.github.io/light-oauth2/tutorials/enterprise/
     */
    //@Test
    public void testGetCertFromOauth() {
        X509Certificate certificate = JwtHelper.getCertFromOauth("100");
        System.out.println("certificate = " + certificate);
        Assert.assertNotNull(certificate);
    }
}
