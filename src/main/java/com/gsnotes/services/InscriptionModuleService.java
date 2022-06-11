package com.gsnotes.services;

import com.gsnotes.bo.Etudiant;

import java.util.List;

public interface InscriptionModuleService {

    List<Etudiant> getEtudiantsInscrisAuModule(Long idModule, String session);
}
