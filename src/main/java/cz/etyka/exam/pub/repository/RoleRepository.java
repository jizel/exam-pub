package cz.etyka.exam.pub.repository;

import cz.etyka.exam.pub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
