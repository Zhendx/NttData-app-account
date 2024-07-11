package com.NttDataChallenge.account.repository;

import com.NttDataChallenge.account.models.entity.MovementEntity;
import com.NttDataChallenge.account.models.entity.MovementReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Integer> {

    @Query(value = "SELECT new com.NttDataChallenge.account.models.entity.MovementReport(m.date, a.accountType, a.accountNumber, m.typeMovement, m.balance, a.state, m.value) " +
            "FROM MovementEntity m JOIN AccountEntity a ON a.idAccount = m.idAccount " +
            "WHERE m.date BETWEEN ?1 AND ?2 AND a.idClient =?3")
    List<MovementReport> getMovementReport(Date dateInitial, Date dateFinal, Integer id);

    @Query(value = "SELECT m FROM MovementEntity m JOIN AccountEntity a ON m.idAccount = a.idAccount " +
            "WHERE a.idClient=?1 ORDER BY m.idMovement DESC LIMIT 1")
    MovementEntity findByMovement(Integer id);
}
