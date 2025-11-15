package com.futbolclub;

import com.futbolclub.model.*;
import com.futbolclub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private JugadorRepository jugadorRepository;
    
    @Autowired
    private AsociacionRepository asociacionRepository;
    
    @Autowired
    private CompeticionRepository competicionRepository;

    @Override
    public void run(String... args) throws Exception {
        // LIMPIAR DATOS EXISTENTES PARA EVITAR CONFLICTOS
        clubRepository.deleteAll();
        entrenadorRepository.deleteAll();
        asociacionRepository.deleteAll();
        competicionRepository.deleteAll();
        jugadorRepository.deleteAll();

        // Crear Asociaciones
        Asociacion fcf = new Asociacion();
        fcf.setNombre("Federación Colombiana de Fútbol");
        fcf.setPais("Colombia");
        fcf.setPresidente("Ramón Jesurún");
        asociacionRepository.save(fcf);

        // Crear Entrenadores (UNO PARA CADA CLUB)
        Entrenador entrenador1 = new Entrenador();
        entrenador1.setNombre("Juan");
        entrenador1.setApellido("Pérez");
        entrenador1.setEdad(45);
        entrenador1.setNacionalidad("Argentino");
        entrenadorRepository.save(entrenador1);

        Entrenador entrenador2 = new Entrenador();
        entrenador2.setNombre("Carlos");
        entrenador2.setApellido("Gómez");
        entrenador2.setEdad(50);
        entrenador2.setNacionalidad("Colombiano");
        entrenadorRepository.save(entrenador2);

        // Crear Competiciones
        Competicion liga = new Competicion();
        liga.setNombre("Liga BetPlay Dimayor");
        liga.setMontoPremio(5000000);
        liga.setFechaInicio(LocalDate.of(2024, 1, 20));
        liga.setFechaFin(LocalDate.of(2024, 6, 15));
        competicionRepository.save(liga);

        Competicion copa = new Competicion();
        copa.setNombre("Copa Colombia");
        copa.setMontoPremio(3000000);
        copa.setFechaInicio(LocalDate.of(2024, 2, 1));
        copa.setFechaFin(LocalDate.of(2024, 11, 30));
        competicionRepository.save(copa);

        // Crear Jugadores
        Jugador jugador1 = new Jugador();
        jugador1.setNombre("Luis");
        jugador1.setApellido("Díaz");
        jugador1.setNumero(7);
        jugador1.setPosicion("Delantero");
        jugadorRepository.save(jugador1);

        Jugador jugador2 = new Jugador();
        jugador2.setNombre("James");
        jugador2.setApellido("Rodríguez");
        jugador2.setNumero(10);
        jugador2.setPosicion("Mediocampista");
        jugadorRepository.save(jugador2);

        // Crear Clubes con relaciones (CADA UNO CON SU PROPIO ENTRENADOR)
        Club millonarios = new Club();
        millonarios.setNombre("Millonarios FC");
        millonarios.setEntrenador(entrenador1); // ENTRENADOR ÚNICO PARA ESTE CLUB
        millonarios.setJugadores(Arrays.asList(jugador1, jugador2));
        millonarios.setAsociacion(fcf);
        millonarios.setCompeticiones(Arrays.asList(liga, copa));
        clubRepository.save(millonarios);

        Club nacional = new Club();
        nacional.setNombre("Atlético Nacional");
        nacional.setEntrenador(entrenador2); // ENTRENADOR DIFERENTE
        nacional.setAsociacion(fcf);
        nacional.setCompeticiones(Arrays.asList(liga));
        clubRepository.save(nacional);
        
        System.out.println("✅ Datos de prueba creados exitosamente!");
    }
}