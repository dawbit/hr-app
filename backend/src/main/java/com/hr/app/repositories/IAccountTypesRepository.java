package com.hr.app.repositories;

import com.hr.app.models.database.AccountTypesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountTypesRepository extends JpaRepository<AccountTypesModel, Long> {

    AccountTypesModel findById(long id);
    AccountTypesModel findByRoleId(long roleId);
    AccountTypesModel findByRoleName(String roleName);

}
