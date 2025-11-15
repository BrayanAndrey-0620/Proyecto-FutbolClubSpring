package com.futbolclub.controller;

import com.futbolclub.model.Entrenador;
import com.futbolclub.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @GetMapping
    public String listarEntrenadores(Model model) {
        List<Entrenador> entrenadores = entrenadorRepository.findAll();
        model.addAttribute("entrenadores", entrenadores);
        return "entrenadores/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("entrenador", new Entrenador());
        return "entrenadores/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEntrenador(@ModelAttribute Entrenador entrenador) {
        entrenadorRepository.save(entrenador);
        return "redirect:/entrenadores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Entrenador> entrenador = entrenadorRepository.findById(id);
        if (entrenador.isPresent()) {
            model.addAttribute("entrenador", entrenador.get());
            return "entrenadores/formulario";
        }
        return "redirect:/entrenadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEntrenador(@PathVariable Long id) {
        entrenadorRepository.deleteById(id);
        return "redirect:/entrenadores";
    }
}