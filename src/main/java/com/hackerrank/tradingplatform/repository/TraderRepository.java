package com.hackerrank.tradingplatform.repository;

import com.hackerrank.tradingplatform.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {
    @Query("SELECT t FROM Trader t WHERE t.email = :email")
    Optional<Trader> findByEmail(@Param("email") String email);
    
    @Query("SELECT t FROM Trader t ORDER BY t.id ASC")
    List<Trader> findAllOrderByIdAsc();

    @Query("SELECT t FROM Trader t ORDER BY t.id DESC")
    List<Trader> findAllOrderByIdDesc();
}