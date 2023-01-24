package com.corvid.ninteen.impact.analysis.controller;

import com.corvid.ninteen.impact.analysis.model.UserPrincipal;
import com.corvid.ninteen.impact.analysis.model.UserToken;
import com.corvid.ninteen.impact.analysis.mapper.CorvidMapper;
import com.corvid.ninteen.impact.analysis.service.impl.JWTTokenProviderService;
import com.corvid.ninteen.impact.analysis.service.impl.UserAuthDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.*;

/**
 * @author shahzeb.latif
 */

@RestController
@RequestMapping("/user/api/")
public class UserAndAuthenticationController {

    @Autowired
    private JWTTokenProviderService jwtTokenProviderService;

    @Autowired
    UserAuthDetailsService userAuthDetailsService;

    @Autowired
    UserToken userToken;

    @Autowired
    private UserAuthDetailsService userDetailsService;

    @Autowired
    CorvidMapper corvidMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAndAuthenticationController.class);

    @GetMapping("get_access_token/{user_name}")
    public String getUserToken(@PathVariable("user_name") String userName){

        try {
            LOGGER.info("Request received for Access Token against name : " + userName);

            if (userName == null || userName.isEmpty()) {
                return corvidMapper.getCustomError("FAILED!! Invalid request");
            }

            UserPrincipal userDetails = userDetailsService.loadUserByUsername(userName);

            String token = jwtTokenProviderService.generateAccessToken(userName, userDetails.getAuthorities());

            userToken.setAccess_token(token);
            userToken.setToken_type(BEARER);
            userToken.setMessage(SUCCESS);

            LOGGER.info("Access Token Created : ", token);

        }catch (Exception ex){
            return corvidMapper.getError(ex);
        }
        return corvidMapper.convertObjectToJson(userToken);
    }

    @GetMapping("users/list")
    public String getUserList() {
        try {
            return corvidMapper.convertObjectToJson(userLists.keySet());
        }catch (Exception ex){
            return corvidMapper.getError(ex);
        }
    }
}
