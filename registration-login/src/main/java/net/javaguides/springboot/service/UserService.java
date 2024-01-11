package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
    void saveFile(MultipartFile file);
    List<User> getAllUsers();
    void saveOrUpdateUser(User user);
    void deleteUser(Long id);

    void updateUser(Long Id, User updatedUser);

    User getUserById(Long id);

    User authenticateUser(String email, String password);

    boolean isUserExists(String email, Long Id);

    void editUser(User existingUser);

    void addUser(User user);


    void deleteUserById(Long Id);
}
