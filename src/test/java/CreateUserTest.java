import dto.UserRequest;
import dto.UserResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import services.UserApi;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    /**
     * 1. Создание валидного пользователя
     * Шаги:
     * 1. Заполняем параметры пользователя согласно UserRequest;
     * 2. Отправляем POST-запрос на URL https://petstore.swagger.io/v2/user, передавая в теле запроса созданного в п.1. пользователя;
     * Ожидаемый результат:
     * Статус ответа 200
     */
    @Test
    public void createValidUser() {
        UserRequest user = UserRequest.builder()
                .userName("Sunflower")
                .firstName("Diana")
                .lastName("Smith")
                .email("sundiana@uk.com")
                .id(3L)
                .password("qwerty123")
                .phoneNumber("321-321-321")
                .status(2L)
                .build();

        UserApi userApi = new UserApi();
        userApi.createUser(user)
                .then()
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
                .statusCode(HttpStatus.SC_OK);

    }

    /**
     * 2. Создание списка пользователей
     * Шаги:
     * 1. Создаём первого пользователя;
     * 2. Создаём второго пользователя;
     * 3. Отправляем POST-запрос на URL https://petstore.swagger.io/v2/user/createWithList, передавая в теле запроса
     * созданных в п.1 пользователей;
     * Ожидаемый результат:
     * Статус ответа 200
     */
    @Test
    public void createListOfValidUsers() {
        UserRequest firstUser = UserRequest.builder()
                .userName("Camomile")
                .firstName("Ekaterina")
                .lastName("Bond")
                .email("bonderina@uk.com")
                .id(8L)
                .password("qwerty231")
                .phoneNumber("123-123-123")
                .status(2L)
                .build();

        UserRequest secondUser = UserRequest.builder()
                .userName("Hydrangea")
                .firstName("Elena")
                .lastName("Beautiful")
                .email("bonderina@uk.com")
                .id(4L)
                .password("wasd159")
                .phoneNumber("963-963-963")
                .status(2L)
                .build();

        List<UserRequest> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);

        UserApi userApi = new UserApi();
        userApi.createUsersWithList(users)
                .then()
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
                .statusCode(HttpStatus.SC_OK);
    }

    /**
     * 3. Получение пользователя по существующему userName
     * Шаги:
     * 1. Создаём пользователя;
     * 2. Отправляем GET-запрос на URL https://petstore.swagger.io/v2/user/{username} с userName созданного в п.1 пользователя
     * Ожидаемый результат:
     * Статус ответа 200
     */
    @Test
    public void getUserByExistUserName() {
        UserRequest.builder()
                .userName("Hydrangea")
                .firstName("Elena")
                .lastName("Beautiful")
                .email("beaute@uk.com")
                .id(4L)
                .password("wasd159")
                .phoneNumber("963-963-963")
                .status(2L)
                .build();
        UserApi userApi = new UserApi();
        userApi.getUserByName("Hydrangea")
                .then()
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/GetUserByName.json"))
                .statusCode(HttpStatus.SC_OK)
                .body("userName", equalTo("Hydrangea"))
                .body("firstName", equalTo("Elena"))
                .body("lastName", equalTo("Beautiful"))
                .body("email", equalTo("beaute@uk.com"))
                .body("id", equalTo("4L"))
                .body("phoneNumber", equalTo("963-963-963"));
    }

    /**
     * 3. Получение пользователя по несуществующему userName
     * Шаги:
     * 1. Создаём пользователя;
     * 2. Отправляем GET-запрос на URL https://petstore.swagger.io/v2/user/{username} с невалидным userName
     * Ожидаемый результат:
     * Статус ответа 404
     */
    @Test
    public void getUserByNonExistUserName() {
        UserRequest.builder()
                .userName("Camomile")
                .firstName("Ekaterina")
                .lastName("Bond")
                .email("bonderina@uk.com")
                .id(8L)
                .password("qwerty231")
                .phoneNumber("123-123-123")
                .status(2L)
                .build();
        UserApi userApi = new UserApi();
        UserResponse userResponse = userApi.getUserByName("Peony")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .body()
                .as(UserResponse.class);

        assertThat(userResponse.getCode(), equalTo("1"));
        assertThat(userResponse.getType(), equalTo("error"));
        assertThat(userResponse.getMessage(), equalTo("User not found"));

    }
}