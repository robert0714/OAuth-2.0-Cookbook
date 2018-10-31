package com.packt.example.facebookloginoauth2.openid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "facebook.filter")
public class FacebookFilterProperties {

	private String callbackUri;
	private String apiBaseUri;

	public String getCallbackUri() {
		return callbackUri;
	}

	public void setCallbackUri(String callbackUri) {
		this.callbackUri = callbackUri;
	}

	public String getApiBaseUri() {
		return apiBaseUri;
	}

	public void setApiBaseUri(String apiBaseUri) {
		this.apiBaseUri = apiBaseUri;
	}

}
