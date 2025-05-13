package com.ticket_platform.ticket_platform.Repository;

import com.ticket_platform.ticket_platform.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoriaRepository extends JpaRepository<Categoria,Integer> {

}
