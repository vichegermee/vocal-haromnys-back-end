package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.Chorister;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoristerRepository extends JpaRepository<Chorister, Long> {

    List<Chorister> findAllByOrderByDisplayOrderAsc();
}
