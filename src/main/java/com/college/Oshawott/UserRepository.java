package com.college.Oshawott;

import com.college.Oshawott.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {}
