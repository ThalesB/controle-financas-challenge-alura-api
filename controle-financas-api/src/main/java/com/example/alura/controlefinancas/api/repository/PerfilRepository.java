package com.example.alura.controlefinancas.api.repository;

import com.example.alura.controlefinancas.api.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
}
