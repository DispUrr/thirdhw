package services;

import dto.UserRequest;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserApi extends PetStoreApi{
    private static final String USER_LIST_RESOURCE = "/user/createWithList";
    private static final String USER_RESOURCE = "/user";
    private static final String GET_USER_BY_NAME_RESOURCE = "/user/{userName}";
    private static final String GET_USER_LOGIN = "/user/login";


    public Response createUsersWithList(List<UserRequest> users) {
        return given(requestSpecification)
                .with()
                .body(users)
                .log().all()
                .when()
                .post(USER_LIST_RESOURCE);
    }

    public Response createUser(UserRequest user) {
        return given(requestSpecification)
                .with()
                .body(user)
                .log().all()
                .when()
                .post(USER_RESOURCE);
    }

    public Response getUserByName(String userName) {
        return given(requestSpecification)
                .with()
                .pathParam("userName", userName)
                .log().all()
                .when()
                .get(GET_USER_BY_NAME_RESOURCE);
    }

}
