package com.ticket_platform.ticket_platform.Repository;

import com.ticket_platform.ticket_platform.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role,Integer> {
    Role findByNomeRegola(String r);
}
