package com.NttDataChallenge.account.repository;

import com.NttDataChallenge.account.models.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    @Query(value = "SELECT t FROM AccountEntity t WHERE t.accountNumber=?1 AND t.accountType=?2")
    AccountEntity findByPublishedAccountNumber(String accountNumber, String accountType);
}
