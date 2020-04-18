package majiang.community.controller;


import majiang.community.dto.AcctssTokenDTO;
import majiang.community.dto.GithubUser;
import majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


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


    @GetMapping("/callback")
    public String callbacK(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AcctssTokenDTO acctssTokenDTO = new AcctssTokenDTO();
        acctssTokenDTO.setClient_id("c80124bcc46b8f513898");
        acctssTokenDTO.setClient_secret("010a7709edcc04ede05858691d5a9e3cb7961550");
        acctssTokenDTO.setCode(code);
        acctssTokenDTO.setCode(code);
        acctssTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        acctssTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(acctssTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if (user != null) {
            //登录成功，写cookie和session
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
      }
}
