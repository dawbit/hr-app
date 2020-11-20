package com.hr.app.repositories;

import com.hr.app.models.database.UsersModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface IUsersRepository extends JpaRepository<UsersModel, Long> {

    UsersModel findById(long id);
    UsersModel findByLogin(String login);
    UsersModel findByLoginOrEmail(String login, String email);
}
