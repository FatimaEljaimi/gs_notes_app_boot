package com.gsnotes.services.impl;

import com.gsnotes.bo.Filiere;
import com.gsnotes.bo.Module;
import com.gsnotes.bo.Niveau;
import com.gsnotes.dao.INiveauDao;
import com.gsnotes.services.IModuleService;
import com.gsnotes.services.INiveauService;
import com.gsnotes.utils.export.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NiveauServiceImpl implements INiveauService {

    @Autowired
    private INiveauDao niveauDao;

    @Autowired
    private IModuleService moduleService;

    @Override
    public List<Niveau> getAllNiveaux() {
        return niveauDao.findAll();
    }

    @Override
    public Niveau getNiveau(Long idNiveau) {
        return niveauDao.getById(idNiveau);
    }

    @Override
    public int getSeuilBySession(Long idNiveau, String session) {
        Filiere filiere = getNiveau(idNiveau).getFiliere();
        return "Normale".equals(session) ? filiere.getSeuilSN() : filiere.getSeuilSR();
    }
    @Override
    public List<ExcelFile> prepareFiles(String session, Long idNiveau) {

        Niveau niveau = getNiveau(idNiveau);
        List<Module> modules = niveau.getModules();

        List<ExcelFile> files = new ArrayList<>();

        for (Module module : modules) {

            Long idModule = module.getIdModule();

            String fileName = module.getTitre();
            String[][] header = moduleService.prepareHeaderData(idModule, session);
            String[][] data = moduleService.prepareData(idModule, session);

            ExcelFile excelFile = new ExcelFile(fileName, header, data);

            files.add(excelFile);

        }
        return files;

    }


}
