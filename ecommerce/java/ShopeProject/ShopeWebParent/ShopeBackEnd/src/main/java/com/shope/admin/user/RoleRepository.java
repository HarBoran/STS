package com.shope.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shope.common.entity.role;

@Repository
public interface RoleRepository extends CrudRepository<role, Integer>{

}
