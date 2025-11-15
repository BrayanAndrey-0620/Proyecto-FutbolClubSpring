package com.futbolclub.controller;

import com.futbolclub.model.Club;
import com.futbolclub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clubes")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private AsociacionRepository asociacionRepository;
    
    @Autowired
    private JugadorRepository jugadorRepository;
    
    @Autowired
    private CompeticionRepository competicionRepository;

    @GetMapping
    public String listarClubes(Model model) {
        List<Club> clubes = clubRepository.findAll();
        model.addAttribute("clubes", clubes);
        return "clubes/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("club", new Club());
        cargarDatosRelaciones(model);
        return "clubes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarClub(@ModelAttribute Club club) {
        clubRepository.save(club);
        return "redirect:/clubes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isPresent()) {
            model.addAttribute("club", club.get());
            cargarDatosRelaciones(model);
            return "clubes/formulario";
        }
        return "redirect:/clubes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarClub(@PathVariable Long id) {
        clubRepository.deleteById(id);
        return "redirect:/clubes";
    }
    
    private void cargarDatosRelaciones(Model model) {
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
    }
}