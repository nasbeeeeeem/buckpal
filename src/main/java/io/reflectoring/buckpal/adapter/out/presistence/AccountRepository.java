package io.reflectoring.buckpal.adapter.out.presistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountJpaEntity, Long>{
  
}
