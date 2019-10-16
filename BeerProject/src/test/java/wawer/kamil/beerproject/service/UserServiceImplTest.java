package wawer.kamil.beerproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    Pageable pageable;

    @Mock
    User user;

    @InjectMocks
    UserServiceImpl service;

    private final static Long ID = 1L;

    @Test
    public void verify_get_all_users_page(){
        service.findAllUsersPage(pageable);
        verify(repository).findAll(pageable);
    }

    @Test
    public void verify_get_all_users_list(){
        service.findAllUsersList();
        verify(repository).findAll();
    }

    @Test
    public void verify_get_user_by_user_id_when_user_id_exists() throws NoContentException {
        when(repository.findById(ID)).thenReturn(Optional.of(user));
        service.findUserByUserId(ID);
        verify(repository).findById(ID);
    }

    @Test(expected = NoContentException.class)
    public void verify_get_brewery_by_user_id_when_user_id_do_not_exists() throws NoContentException {
        service.findUserByUserId(ID);
        verify(repository).findById(ID);
    }

    @Test
    public void verify_create_new_user(){
        service.createNewUser(user);
        verify(repository).save(user);
    }

    @Test
    public void verify_update_user_by_user_id_when_user_id_exists() throws NoContentException {
        when(repository.existsById(ID)).thenReturn(true);
        service.updateUser(ID,user);
        verify(repository).save(user);
    }

    @Test(expected = NoContentException.class)
    public void verify_update_brewery_by_brewery_id_when_brewery_id_do_not_exists() throws NoContentException {
        when(repository.existsById(ID)).thenReturn(false);
        service.updateUser(ID,user);
        verify(repository).save(user);
    }

    @Test
    public void verify_delete_user_by_user_id_when_user_id_exists() throws NoContentException {
        when(repository.existsById(ID)).thenReturn(true);
        service.deleteUser(ID);
        verify(repository).deleteById(ID);
    }

    @Test(expected = NoContentException.class)
    public void verify_delete_user_by_user_id_when_user_id_do_not_exists() throws NoContentException {
        when(repository.existsById(ID)).thenReturn(false);
        service.deleteUser(ID);
        verify(repository).deleteById(ID);
    }
}
