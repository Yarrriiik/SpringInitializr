package com.example.carapp.web;

import com.example.carapp.domain.AppUser;
import com.example.carapp.repository.AppUserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    public AuthController(AppUserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }

    @GetMapping("/login")
    public String loginPage() { return "auth/login"; }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("reg", new RegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("reg") RegisterDto reg, BindingResult br) {
        if (br.hasErrors()) return "auth/register";
        if (users.findByUsername(reg.getUsername()).isPresent()) {
            br.rejectValue("username", "exists", "Логин уже занят");
            return "auth/register";
        }

        AppUser u = new AppUser(reg.getUsername(), reg.getPassword(), "ROLE_USER"); // сейчас без шифрования
        AppUser saved = users.save(u);
        System.out.println("Registered id=" + saved.getId() + " username=" + saved.getUsername());

        return "redirect:/login?registered";
    }


    public static class RegisterDto {
        @NotBlank @Size(min=3, max=50) private String username;
        @NotBlank @Size(min=4, max=100) private String password;
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
