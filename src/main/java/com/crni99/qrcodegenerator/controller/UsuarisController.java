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
    public String login(@RequestParam("usuari") String usuari, @RequestParam("password") String password, HttpSession session, Model model) {
        Usuaris usuario = UsuarisRepository.findByUsuari(usuari);

        if (usuario != null) {
            if (password.equals(usuario.getPassword())) {
                System.out.println("Usuario autenticado: " + usuario.getUsuari());
                session.setAttribute("userId", usuario.getDni());

                String userId = (String) session.getAttribute("userId");

                if (userId != null) {

                    if (usuario != null) {
                        session.setAttribute("esSoci", usuario.getSoci());
                    } else {
                        model.addAttribute("error", "Usuario no encontrado");
                        return "login";
                    }
                } else {
                    model.addAttribute("error", "ID de usuario no encontrado en la sesión");
                    return "login";
                }

                return "redirect:/mostrarPartits";
            } else {
                System.out.println("Contraseña incorrecta para el usuario: " + usuario.getUsuari());
            }
        } else {
            model.addAttribute("error", "Usuario no encontrado: " + usuari);
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida y destruye la sesión
        return "redirect:/login?logout"; // Redirige al usuario a la página de inicio de sesión con un mensaje de cierre de sesión
    }

    @GetMapping("/updateSocio")
    public String updateSocio(HttpSession session) {
        // Obtén el ID del usuario de la sesión
        String userId = (String) session.getAttribute("userId");

        if (userId != null) {
            // Busca el usuario por su ID en la sesión
            Usuaris usuario = UsuarisRepository.findByUsuari(userId);

            if (usuario != null && usuario.getSoci() == 0) {
                // Modifica el valor del atributo socio si es 0
                Integer soci = 1;
                usuario.setSoci((soci));
                UsuarisRepository.save(usuario);
                System.out.println("Valor de socio actualizado a 1 para el usuario con ID: " + userId);
            } else {
                System.out.println("El usuario no existe o ya tiene el valor de socio diferente de 0");
            }
        } else {
            System.out.println("ID de usuario no encontrado en la sesión");
        }

        // Redirige a la página deseada después de realizar la actualización
        return "redirect:/mostrarPartits";
    }

}