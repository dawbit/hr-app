package com.hr.app.repositories;

import com.hr.app.models.UsersModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface IUsersRepository extends JpaRepository<UsersModel, Long> {

    UsersModel findById(long id);
    UsersModel findByLogin(String login);

    //List<UsersModel> findAllBy(String)
}
