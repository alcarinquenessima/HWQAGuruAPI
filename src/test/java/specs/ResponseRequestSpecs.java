package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class ResponseRequestSpecs {
    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().all();

    private static ResponseSpecification createResponseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpec200 = createResponseSpec(200);
    public static ResponseSpecification responseSpec204 = createResponseSpec(204);
    public static ResponseSpecification responseSpec400 = createResponseSpec(400);
}
