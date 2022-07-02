package wawer.kamil.beerproject.utils.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import wawer.kamil.beerproject.configuration.SpringAppConfig;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wawer.kamil.beerproject.helpers.UserTestHelper.*;

class UserMapperTest {

    private final SpringAppConfig springAppConfig = new SpringAppConfig();
    private final ModelMapper defaultModelMapper = springAppConfig.getModelMapper();
    private final ModelMapper userModelMapper = springAppConfig.getUserModelMapper();
    private final UserMapper mapper = new UserMapper(defaultModelMapper, userModelMapper);

    private User user;
    private UserRequest userRequest;
    private Page<User> userPage;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        this.user = getUserEntityWithUserRole();
        this.userRequest = getUserRequest();
        this.userPage = createUserPage();
        this.userList = createListOfUser();
    }

    @Test
    @DisplayName("Should map user entity to user response keeping all values the same")
    void should_map_user_entity_to_user_response_keeping_all_values_the_same() {
        //given
        UserResponse userResponse = mapper.mapUserEntityToUserResponse(user);

        //when
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getUsername(), userResponse.getUsername());
        assertEquals(user.getPassword(), userResponse.getPassword());
        assertEquals(user.getGrantedAuthorities(), userResponse.getGrantedAuthorities());
    }

    @Test
    @DisplayName("Should map Page<User> to user Page<UserResponse> keeping all values the same")
    void should_map_user_entity_page_to_user_response_page_keeping_all_values_the_same() {
        //given
        Page<UserResponse> userResponse = mapper.mapUserEntityPageToUserResponsePage(userPage);

        //when
        assertEquals(userPage.getTotalPages(), userResponse.getTotalPages());
        assertEquals(userPage.getTotalElements(), userResponse.getTotalElements());
        assertEquals(userPage.getSize(), userResponse.getSize());
        assertEquals(userPage.getContent().size(), userResponse.getContent().size());
        assertEquals(userPage.getContent().get(0).getId(), userResponse.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Should map List<User> to List<UserResponse> keeping all values the same")
    void should_map_list_of_user_entity_to_list_of_user_response_keeping_all_values_the_same() {
        //when
        List<UserResponse> userResponses = mapper.mapUserEntityListToUserResponseList(userList);

        //then
        assertEquals(userList.size(), userResponses.size());
        assertEquals(userList.get(0).getId(), userResponses.get(0).getId());
    }

    @Test
    @DisplayName("Should map UserRequest to User keeping all values the same")
    void should_map_user_request_to_user_keeping_all_values_the_same() {
        //when
        User user = mapper.mapUserRequestToUserEntity(userRequest);

        //then
        assertEquals(userRequest.getUsername(), user.getUsername());
        assertEquals(userRequest.getPassword(), user.getPassword());
        assertEquals(userRequest.getGrantedAuthorities(), user.getGrantedAuthorities());
        assertEquals(userRequest.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Should map UserRequest to User keeping all values the same for update entity")
    void should_map_user_request_to_user_entity_keeping_all_values_the_same() {
        //when
        User mappedUser = mapper.mapUserRequestToUserEntity(userRequest, user);

        //then
        assertEquals(userRequest.getUsername(), mappedUser.getUsername());
        assertEquals(userRequest.getPassword(), mappedUser.getPassword());
        assertEquals(userRequest.getGrantedAuthorities(), mappedUser.getGrantedAuthorities());
        assertEquals(userRequest.getEmail(), mappedUser.getEmail());
    }

}
