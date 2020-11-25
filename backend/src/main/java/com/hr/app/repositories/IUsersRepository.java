package com.hr.app.repositories;

import com.hr.app.models.database.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IUsersRepository extends JpaRepository<UsersModel, Long> {

    UsersModel findById(long id);
    UsersModel findByLogin(String login);
    UsersModel findByLoginOrEmail(String login, String email);

    @Query(value = "SELECT * FROM Users _user JOIN Account_Types accountType " +
            "ON accountType.id = _user.role_id WHERE NOT (accountType.role_name LIKE '%hr%' OR " +
            "accountType.role_name LIKE '%ceo%')", nativeQuery = true)
    List<UsersModel> findNonHrUsers();

    @Query(value = "SELECT * FROM Users _user JOIN Hr_Users hr_user " +
            "ON hr_user.user_id = _user.id WHERE hr_user.company_id = ?1", nativeQuery = true)
    List<UsersModel> findOurHrUsers(long companyId);

    UsersModel findByEmail(String email);
}
