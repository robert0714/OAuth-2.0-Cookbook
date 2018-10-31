package com.packt.example.openidspring5.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

//    @GetMapping
//    public ModelAndView profile() {
//        DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder
//            .getContext().getAuthentication().getPrincipal();
//
//        ModelAndView mv = new ModelAndView("dashboard");
//        mv.addObject("profile", user.getUserInfo().getClaims());
//        return mv;
//    }
	 

	@GetMapping()
	public ModelAndView profile(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User) {

		final String registrationId = authorizedClient.getClientRegistration().getRegistrationId();
		DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		System.out.println(registrationId);
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("profile", oauth2User);
		return mv;
	}

}
