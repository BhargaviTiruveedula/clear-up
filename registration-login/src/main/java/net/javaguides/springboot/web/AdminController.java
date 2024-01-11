package net.javaguides.springboot.web;


import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

//    @GetMapping("/admin")
//    public String showAdminLoginPage() {
//        return "admin";
//    }
//    @GetMapping("/admin-user-management.html")
//    public String showAdminUserPage(Model model) {
//        List<User> userList = userService.getAllUsers();
//        model.addAttribute("users", userList);
//        return "admin-user-management";
//    }


    @PostMapping("/admin")
    public String adminLogin(String email, String password, Model model) {
        net.javaguides.springboot.model.User user = userService.authenticateUser(email, password);

        if (user != null && "admin".equals(user.getUsertype())) {
            // Admin authenticated successfully
            // Add your authorization logic here if needed
            return "redirect:/dashboard"; // Redirect to admin dashboard
        } else {
            // Authentication failed or user is not an admin
            model.addAttribute("error", "Invalid credentials or insufficient privileges");
            return "redirect:/admin"; // Return to admin login page with an error message
        }
    }


//    @GetMapping("/dashboard")
//    public String showAdminDashboard(Model model) {
//        List<User> userList = userService.getAllUsers();
//        model.addAttribute("users", userList);
//        return "dashboard";
//    }


    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Check if the user with the given email or user ID already exists
        if (userService.isUserExists(user.getEmail(), user.getId())) {
            // If user already exists, add an error message
            redirectAttributes.addFlashAttribute("addError", "User with the same email or user ID already exists");
        } else {
            // If user doesn't exist, add the user
            userService.addUser(user);
            // Add a success message
            redirectAttributes.addFlashAttribute("addSuccess", "User added successfully");
        }

        return "redirect:/dashboard";
    }




    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Fetch the existing user from the database
        User existingUser = userService.getUserById(user.getId());

        if (existingUser != null) {
            // Update the existing user's fields with the new values
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setFullName(user.getFullName());
            existingUser.setUsertype(user.getUsertype());

            // Save the updated user back to the database
            userService.editUser(existingUser);
            redirectAttributes.addFlashAttribute("editSuccess", "User edited successfully");
        } else {
            // Handle the case where the user with the specified ID doesn't exist
            redirectAttributes.addFlashAttribute("editError", "User with ID " + user.getId() + " not found");
        }

        return "redirect:/dashboard";
    }


    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Long Id, RedirectAttributes redirectAttributes) {
        // Check if the user with the given ID exists
        User user = userService.getUserById(Id);
        if (user == null) {
            // If the user does not exist, add an error message
            redirectAttributes.addFlashAttribute("deleteError", "User with ID " + Id + " does not exist");
        } else {
            // If the user exists, delete the user
            userService.deleteUserById(Id);
            // Add a success message
            redirectAttributes.addFlashAttribute("deleteSuccess", "User deleted successfully");
        }
        return "redirect:/dashboard";
    }


}