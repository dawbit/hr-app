package com.hr.app.repositories;

import com.hr.app.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsersRepository extends JpaRepository<UsersModel, Long> {

    UsersModel findById(long id);
    UsersModel findByLogin(String login);
    UsersModel findByPhone_number(String phone_number);
    UsersModel findByEmail(String email);

    List<UsersModel> findAllByLogin(String login);
    List<UsersModel> findAllByName(String login);
    List<UsersModel> findAllByFore_name(String fore_name);
    List<UsersModel> findAllByMiddle_name(String middle_name);
    List<UsersModel> findAllBySure_name(String sure_name);
    List<UsersModel> findAllByPhone_number(String phone_number);
    List<UsersModel> findAllByEmail(String email);

}
