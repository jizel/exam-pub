package cz.etyka.exam.pub.repository;

import cz.etyka.exam.pub.entity.PubOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PubOrder, Long> {
}
