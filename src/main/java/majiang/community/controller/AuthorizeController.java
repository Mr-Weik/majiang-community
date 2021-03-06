package majiang.community.controller;


import majiang.community.Mapper.UserMapper;
import majiang.community.dto.AcctssTokenDTO;
import majiang.community.dto.GithubUser;
import majiang.community.model.User;
import majiang.community.provider.GithubProvider;
import netscape.security.UserTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;


    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientsecret;

    @Value("${github.redirect.uri}")
    private String redirecturi;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callbacK(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AcctssTokenDTO acctssTokenDTO = new AcctssTokenDTO();
        acctssTokenDTO.setClient_id("client_id");
        acctssTokenDTO.setClient_secret("client_secret");
        acctssTokenDTO.setCode(code);
        acctssTokenDTO.setCode(code);
        acctssTokenDTO.setRedirect_uri("redirect_uri");
        acctssTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(acctssTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功，写cookie和session
            request.getSession().setAttribute("githubUser", githubUser);
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
      }
}
