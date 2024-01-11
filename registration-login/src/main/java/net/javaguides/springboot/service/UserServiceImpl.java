package net.javaguides.springboot.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.web.dto.UserRegistrationDto;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;


	@Override
	public User authenticateUser(String email, String password) {
		return userRepository.findByEmailAndPassword( email, password);
	}



	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}


	@Override
	public boolean isUserExists(String email, Long Id) {
		return userRepository.existsByEmailOrId(email, Id);
	}


	@Override
	public void editUser(User existingUser) {
		userRepository.save(existingUser);
	}

	@Override
	public User getUserById(Long Id) {
		return userRepository.findById(Id).orElse(null);
	}

	@Override
	public void deleteUserById(Long Id) {
		userRepository.deleteById(Id);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	@Override
	public void updateUser(Long Id, User updatedUser) {
		User existingUser = userRepository.findById(Id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + Id));

		// Update only non-null fields
		if (updatedUser.getEmail() != null) {
			existingUser.setEmail(updatedUser.getEmail());
		}
		if (updatedUser.getPassword() != null) {
			existingUser.setPassword(updatedUser.getPassword());
		}
		if (updatedUser.getFullName() != null) {
			existingUser.setFullName(updatedUser.getFullName());
		}
		if (updatedUser.getUsertype() != null) {
			existingUser.setUsertype(updatedUser.getUsertype());
		}

		userRepository.save(existingUser);
	}



	public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFullName(), 
				registrationDto.getRole(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getSubject(),Arrays.asList(new Role("ROLE_USER")),registrationDto.getUsertype());
		
		return userRepository.save(user);
	}
	
	
	@Override
    public void saveFile(MultipartFile file) {
        // Implementation of the saveFile method to handle file storage
        // This is where you'll use the logic from the earlier saveFile method example
        // ...
		String uploadDirectory = "C:/Users/bhargavi_tiruveedula/Desktop/Certificates";

        // Create the directory if it doesn't exist
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the specified directory
        try {
            String fileName = file.getOriginalFilename();
            File newFile = new File(uploadDirectory + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            // Handle exception - Could not save the file
            e.printStackTrace();
        }
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
}
