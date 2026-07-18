package com.verdurasia.repository;

import com.verdurasia.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Page<Producto> findByActivoTrue(Pageable pageable);

    Page<Producto> findByCategoriaId(Long categoriaId, Pageable pageable);

    @Query("""
            SELECT p FROM Producto p
            WHERE p.activo = true
              AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
              AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
            """)
    Page<Producto> buscar(@Param("nombre") String nombre,
                          @Param("categoriaId") Long categoriaId,
                          Pageable pageable);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
