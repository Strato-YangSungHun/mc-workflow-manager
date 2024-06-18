package kr.co.strato.mcmp.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "M-CMP API", version = "v1"))
public class SwaggerConfig {

	public static final String AUTHORIZATION = "Authorization";

	@Bean
	public OpenAPI getOpenApi() {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(AUTHORIZATION);
		Components components = new Components()
				.addSecuritySchemes(AUTHORIZATION, new SecurityScheme()
						.name(AUTHORIZATION)
						.in(SecurityScheme.In.HEADER)
						.type(SecurityScheme.Type.APIKEY));

		return new OpenAPI()
				.addSecurityItem(securityRequirement)
				.components(components);
	}
}