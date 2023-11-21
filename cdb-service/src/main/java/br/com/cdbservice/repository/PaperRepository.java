package br.com.cdbservice.repository;

import br.com.cdbservice.model.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
}
