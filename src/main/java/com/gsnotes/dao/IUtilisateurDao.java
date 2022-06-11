package com.gsnotes.dao;

import com.gsnotes.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUtilisateurDao extends JpaRepository<Utilisateur, Long> {

	public Utilisateur getUtilisateurByCin(String cin);

}
