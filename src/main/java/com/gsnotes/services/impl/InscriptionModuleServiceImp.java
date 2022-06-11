package com.gsnotes.services.impl;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.services.InscriptionModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InscriptionModuleServiceImp implements InscriptionModuleService {

    @Autowired
    private IInscriptionModuleDao inscriptionModuleDao;

    @Override
    public List<Etudiant> getEtudiantsInscrisAuModule(Long idModule, String session) {

        int year = (YearMonth.now().getMonthValue() >= 10) ? YearMonth.now().getYear() + 1 : YearMonth.now().getYear();


        List<InscriptionModule> inscriptionModules = inscriptionModuleDao.getAllByModuleIdModuleAndInscriptionAnnuelle_Annee(idModule, year);
        List<Etudiant> etudiants = new ArrayList<>();

        for (InscriptionModule ins : inscriptionModules) {
            if ("Rattrapage".equals(session) && !("R".equals(ins.getValidation()))) {
                continue;
            }
            etudiants.add(ins.getInscriptionAnnuelle().getEtudiant());
        }

        return etudiants;

    }
}
