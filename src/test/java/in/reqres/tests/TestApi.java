package in.reqres.tests;

import in.reqres.lombok.LoginBodyLombokModel;
import in.reqres.lombok.LoginResponseLombokModel;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.LoginSpecs.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class TestApi {
    @Test
    void userCreationTest() {
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        data.setName("morpheus");
        data.setJob("leader");

        LoginResponseLombokModel response = given(request)
                .body(data)
                .when()
                .post("/users")
                .then()
                .log().status()
                .spec(response201)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getJob()).isEqualTo("leader");
    }

    @Test
    void registerSuccessfulTest() {
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        LoginResponseLombokModel response = given(request)
                .body(data)
                .when()
                .post("/register")
                .then()
                .log().status()
                .spec(response200)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

    @Test
    void registerUnsuccessfulTest() {
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        data.setEmail("sydney@fife");

        LoginResponseLombokModel response = given(request)
                .body(data)
                .when()
                .post("/register")
                .then()
                .log().status()
                .spec(response400)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    void updateTest() {
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        data.setName("morpheus");
        data.setJob("zion resident");

        LoginResponseLombokModel response = given(request)
                .body(data)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .spec(response200)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getName()).isEqualTo("morpheus");
    }

    @Test
    void loginUnsuccessfulTest() {
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        data.setEmail("peter@klaven");

        LoginResponseLombokModel response = given(request)
                .body(data)
                .when()
                .post("/login")
                .then()
                .log().status()
                .spec(response400)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    void userDeletionTest() {
        given(request)
                .when()
                .delete("/api/users/2")
                .then()
                .log().status()
                .spec(response204);
    }
}
