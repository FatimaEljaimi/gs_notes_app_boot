package com.gsnotes.dao;

import com.gsnotes.bo.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompteDao extends JpaRepository<Compte, Long> {
	public Compte getCompteByLogin(String username);

}
