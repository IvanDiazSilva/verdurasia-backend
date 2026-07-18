package com.verdurasia.repository;

import com.verdurasia.entity.Oferta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    Page<Oferta> findByActivaTrue(Pageable pageable);

    @Query("""
            SELECT o FROM Oferta o
            WHERE o.activa = true
              AND o.fechaInicio <= :hoy
              AND o.fechaFin   >= :hoy
            """)
    List<Oferta> findVigentes(LocalDate hoy);
}
