package com.gsnotes.services.impl;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.Module;
import com.gsnotes.dao.IModuleDao;
import com.gsnotes.services.IModuleService;
import com.gsnotes.services.INiveauService;
import com.gsnotes.services.InscriptionModuleService;
import com.gsnotes.utils.export.ExcelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private IModuleDao moduleDao;

    @Autowired
    private InscriptionModuleService inscriptionModuleService;

    @Autowired
    private INiveauService niveauService;

    @Override
    public List<Module> getAllModules() {
        return moduleDao.findAll();
    }

    @Override
    public Module getModule(Long id) {
        return moduleDao.getById(id);
    }

    @Override
    public ExcelFile prepareFile(long idModule, String session) {

        ExcelFile file = new ExcelFile();

        String sheetName = getModule(idModule).getTitre() + "_Session" + session;
        String[][] headerData = prepareHeaderData(idModule, session);
        String[][] data = prepareData(idModule, session);

        file.setSheetName(sheetName);
        file.setHeaderData(headerData);
        file.setData(data);

        return file;
    }

    @Override
    public String[][] prepareHeaderData(long idModule, String session) {

        //Preparation de deux 2 premiers lignes

        int year = (YearMonth.now().getMonthValue() >= 10) ? YearMonth.now().getYear() + 1 : YearMonth.now().getYear();

        Module module = getModule(idModule);
        List<Element> elements = module.getElements();

        String moduleTitle = module.getTitre(),
                semestre = module.getSemestre().getTitre(),
                annee = (year - 1) + "/" + (year),
                enseignants = EnseignantsToString(module),
                classe = module.getNiveau().getAlias();

        String[] firstLine = {"Module", moduleTitle, "Semestre", semestre, "Année", annee};
        String[] secondLine = {"Enseignant", enseignants, "Session", session, "Classe", classe};


        //Preparation du 3ᵉ ligne
        List<String> thirdLineList = new ArrayList<>();


        thirdLineList.addAll(List.of(new String[]{"ID", "Cne", "Nom", "Prenom"}));
        for (Element element : elements) {
            thirdLineList.add(element.getNom());
        }
        thirdLineList.addAll(List.of(new String[]{"Moyenne", "Validation"}));

        String[] thirdLine = new String[thirdLineList.size()];
        thirdLine = thirdLineList.toArray(thirdLine);


        return new String[][]{firstLine, secondLine, thirdLine};
    }

    @Override
    public String[][] prepareData(long idModule, String session) {

        Module module = getModule(idModule);

        List<Etudiant> etudiants = inscriptionModuleService.getEtudiantsInscrisAuModule(idModule, session);
        List<Element> elements = module.getElements();
        int elementsSize = elements.size();

        String[][] data = new String[etudiants.size()][6 + elementsSize];
        int row = 0;
        for (Etudiant et : etudiants) {
            int col = 0;
            data[row][col++] = String.valueOf(row);
            data[row][col++] = et.getCne();
            data[row][col++] = et.getNom();
            data[row][col++] = et.getPrenom();
            for (int i = 0; i < elementsSize; i++) {
                data[row][col++] = null;
            }

            String moyenneFormula = "";
            char colName = 'E';
            int size = elementsSize;
            for (Element element : elements) {
                moyenneFormula += (colName++) + "" + (row + 4) + "*" + element.getCurrentCoefficient();
                if ((--size) > 0) {
                    moyenneFormula += "+";
                }
            }
            data[row][col++] = moyenneFormula;

            String validationFormula = "IF(" + colName + "" + (row + 4) + ">=" + niveauService.getSeuilBySession(module.getNiveau().getIdNiveau(), session) + ("Normale".equals(session) ? ",\"V\",\"R\")" : ",\"V\",\"NV\")");
            data[row][col] = validationFormula;

            row++;
        }
        return data;
    }

    @Override
    public String EnseignantsToString(Module module) {
        String enseignants = "";
        List<Element> elements = module.getElements();
        int size = elements.size();

        for (Element element : elements) {
            enseignants += element.getCurrentEnseignant().getFullName();
            if ((--size) > 0) {
                enseignants += " et ";
            }
        }
        return enseignants;
    }
}
