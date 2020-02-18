package majiang.community.controller;


import majiang.community.dto.AcctssTokenDTO;
import majiang.community.dto.GithubUser;
import majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callbacK(@RequestParam(name = "code")String code,
                            @RequestParam(name = "state") String state){
        AcctssTokenDTO acctssTokenDTO = new AcctssTokenDTO();
        acctssTokenDTO.setClient_id("c80124bcc46b8f513898");
        acctssTokenDTO.setClient_secret("010a7709edcc04ede05858691d5a9e3cb7961550");
        acctssTokenDTO.setCode(code);
        acctssTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        acctssTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(acctssTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
