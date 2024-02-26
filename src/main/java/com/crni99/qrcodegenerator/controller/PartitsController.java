package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.model.Partits;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PartitsController {

    @Autowired
    private PartitsRepository PartitsRepository;

    @GetMapping("/mostrarPartits")
    public String mostrarPartits(Model model) {
        List<Partits> partits = PartitsRepository.findAll();
        model.addAttribute("partits", partits); // Asegúrate de agregar el objeto con nombre 'partits'
        return "mostrarPartits"; // Nombre de la vista HTML (sin .html)
    }

    // Otros métodos del controlador (si los tienes)

}