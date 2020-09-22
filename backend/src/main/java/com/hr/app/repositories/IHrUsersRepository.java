package com.hr.app.repositories;

import com.hr.app.models.database.HrUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHrUsersRepository extends JpaRepository<HrUsersModel, Long> {

    HrUsersModel findById(long id);

    HrUsersModel findByFKhrUserUserId(long id);

}
