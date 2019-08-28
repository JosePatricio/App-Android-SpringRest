package com.persona.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.persona.entity.Persona;
import com.persona.repository.PersonaRepository;

@RestController
@RequestMapping("/persona")
public class PersonaController {

	@Autowired
	PersonaRepository personaRepository;


	// Guarda persona en la base de datos
	@PostMapping("/add")
	public Persona addPersona(@RequestBody Persona persona) {
            return personaRepository.save(persona);
	}

	// Consulta todas alas personas
	@GetMapping("/all")
	public Iterable<Persona> allPersonas() {
            return personaRepository.findAll();
	}

        //Consulta persona por cedula        
        @GetMapping("ruc/{ruc}")
	public List<Persona> personaByCedula(@PathVariable("ruc") int ruc) {
		return personaRepository.findByCedula(ruc);
 
	}
        
	// Elimina persona de base de datos
	@DeleteMapping("/{personaId}")
	public void deletePersona(@PathVariable("personaId") int personaId) {
		personaRepository.deleteById(personaId);
	}
        
     
        
        
        
        
        
        


public static HttpHeaders headers() {
        HttpHeaders hd = new HttpHeaders();
        hd.add("Access-Control-Allow-Origin", "*");
        hd.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        hd.add("Access-Control-Allow-Credentials", "true");

        hd.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEADs");
        //  hd.add("Content-Type", "application/json; charset=UTF-8");

        return hd;
    }

}
