package team_questio.questio.user.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_questio.questio.user.domain.AccountType;
import team_questio.questio.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameAndUserAccountType(String username, AccountType userAccountType);

    Optional<User> findByUsernameAndUserAccountType(String username, AccountType userAccountType);
}
