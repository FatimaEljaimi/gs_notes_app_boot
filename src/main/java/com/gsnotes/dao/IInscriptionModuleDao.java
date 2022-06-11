package com.gsnotes.dao;

import com.gsnotes.bo.InscriptionModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInscriptionModuleDao extends JpaRepository<InscriptionModule, Long> {

	List<InscriptionModule> getAllByModuleIdModuleAndInscriptionAnnuelle_Annee(Long IdModule, int Annee);

}
