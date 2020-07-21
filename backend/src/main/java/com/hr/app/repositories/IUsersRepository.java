package com.hr.app.repositories;

import com.hr.app.models.UsersModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface IUsersRepository extends JpaRepository<UsersModel, Long> {
    UsersModel findByLogin(String login);
}
