package com.shope.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shope.common.entity.user;

@Repository
public interface UserRepository extends CrudRepository<user, Integer>{

}
