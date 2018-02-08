package com.worksap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.worksap.model.entity.User;

/**
 * @author Xiaoyue Xiao
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {



    User findByUsername(String username);




}
