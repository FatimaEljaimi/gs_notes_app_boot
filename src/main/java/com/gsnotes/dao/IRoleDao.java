package com.gsnotes.dao;


import com.gsnotes.bo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role, Long> {

}
