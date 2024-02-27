package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.model.Usuaris;
import com.crni99.qrcodegenerator.repository.UsuarisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UsuarisController {

    @Autowired
    private UsuarisRepository UsuarisRepository;
    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        return "login"; // Devuelve la vista de inicio de sesión
    }

    @PostMapping("/login")
    public String login(@RequestParam("usuari") String usuari, @RequestParam("password") String password, HttpSession session) {
        Usuaris usuario = UsuarisRepository.findByUsuari(usuari); // Busca el usuario por su nombre
        if (usuario != null) {
            if (password.equals(usuario.getPassword())) { // Compara la contraseña sin encriptar
                System.out.println("Usuario autenticado: " + usuario.getUsuari());
                session.setAttribute("userId", usuario.getDni()); // Guarda el ID del usuario en la sesión
                return "redirect:/mostrarPartits"; // Redirige al usuario a la página principal después de iniciar sesión correctamente
            } else {
                System.out.println("Contraseña incorrecta para el usuario: " + usuario.getUsuari());
            }
        } else {
            System.out.println("Usuario no encontrado: " + usuari);
        }
        return "redirect:/login?error"; // Redirige al usuario a la página de inicio de sesión con un mensaje de error
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida y destruye la sesión
        return "redirect:/login?logout"; // Redirige al usuario a la página de inicio de sesión con un mensaje de cierre de sesión
    }

}