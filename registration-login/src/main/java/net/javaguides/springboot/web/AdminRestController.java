package net.javaguides.springboot.web;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.authenticateUser(email, password);

        if (user != null && "admin".equals(user.getUsertype())) {
            // Admin authenticated successfully
            // Add your authorization logic here if needed
            return ResponseEntity.ok("Admin authenticated successfully");
        } else {
            // Authentication failed or user is not an admin
            return ResponseEntity.status(401).body("Invalid credentials or insufficient privileges");
        }
    }


    @GetMapping("/dashboard")
    public List<User> showAdminDashboard() {
        return userService.getAllUsers();
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        // Check if the user with the given email or user ID already exists
        if (userService.isUserExists(user.getEmail(), user.getId())) {
            // If user already exists, return an error message
            return ResponseEntity.status(400).body("User with the same email or user ID already exists");
        } else {
            // If user doesn't exist, add the user
            userService.addUser(user);
            // Return a success message
            return ResponseEntity.ok("User added successfully");
        }
    }

    @PostMapping("/edit-user")
    public ResponseEntity<String> editUser(@RequestBody User user) {
        // Fetch the existing user from the database
        User existingUser = userService.getUserById(user.getId());

        if (existingUser != null) {
            // Update only the fields that are not null in the request
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getFullName() != null) {
                existingUser.setFullName(user.getFullName());
            }
            if (user.getUsertype() != null) {
                existingUser.setUsertype(user.getUsertype());
            }

            // Save the updated user back to the database
            userService.editUser(existingUser);
            return ResponseEntity.ok("User edited successfully");
        } else {
            // Handle the case where the user with the specified ID doesn't exist
            return ResponseEntity.status(404).body("User with ID " + user.getId() + " not found");
        }
    }

    @PostMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, Long> requestData) {
        Long userId = requestData.get("id");

        // Check if the user with the given ID exists
        User user = userService.getUserById(userId);
        if (user == null) {
            // If the user does not exist, return an error message
            return ResponseEntity.status(404).body("User with ID " + userId + " does not exist");
        } else {
            // If the user exists, delete the user
            userService.deleteUserById(userId);
            // Return a success message
            return ResponseEntity.ok("User deleted successfully");
        }
    }

}
