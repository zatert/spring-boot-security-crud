package ru.zatert.springbootsecuritycrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zatert.springbootsecuritycrud.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
