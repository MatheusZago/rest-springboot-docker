package br.com.matheus.integrationTests.swagger;

import br.com.matheus.integrationTests.testsContainer.AbstractIntegrationTest;
import configs.TesteConfigs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void showDisplaySwaggerUiPage() {

		var content = given()
						.basePath("/swagger-ui/index.html")
						.port(TesteConfigs.SERVER_PORT)
						.when()
							.get()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();

		assertTrue(content.contains("swagger-ui"));
	}

}
