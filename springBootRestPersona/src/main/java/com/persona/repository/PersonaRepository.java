package com.persona.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.persona.entity.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Integer> {


    /**
     * Consulta todas las personas
     * @return  retorna lista de personas
     */
    @Query("SELECT p FROM Persona p")
    public List<Persona> findTodos();
    
    /**
     * Consulta persona por cédula
     * @param ruc
     * @return Retora persona
     */
    @Query("SELECT p FROM Persona p WHERE p.ruc = :ruc")
    public List<Persona> findByCedula(@Param("ruc") Integer ruc);
    
}
