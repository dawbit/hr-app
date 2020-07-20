package com.hr.app.repositories;

import com.hr.app.models.HrUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHrUsersRepository extends JpaRepository<HrUsersModel, Long> {

    HrUsersModel findById(long id);

    List<HrUsersModel> findAllByFk_hruserdepartment(long fk_hruserdepartment);
    List<HrUsersModel> findAllByFk_hrusercompany(long fk_hrusercompany);

}
