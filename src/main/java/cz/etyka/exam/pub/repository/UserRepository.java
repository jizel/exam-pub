package cz.etyka.exam.pub.repository;

import cz.etyka.exam.pub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
