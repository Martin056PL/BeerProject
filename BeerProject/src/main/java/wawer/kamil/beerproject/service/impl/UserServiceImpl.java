package wawer.kamil.beerproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "UserServiceImpl")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findAllByUsername(username);
    }

    @Override
    public Page<UserResponse> findAllUsersPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Override
    public List<UserResponse> findAllUsersList() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
    }


    @Override
    public UserResponse findUserByUserId(Long userId) throws NoContentException {
        User user = userRepository.findById(userId).orElseThrow(NoContentException::new);
        return modelMapper.map(user, UserResponse.class);
    }
}
