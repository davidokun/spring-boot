package com.simgletonapps.repository;

import com.simgletonapps.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findByUsername(String username);
}
