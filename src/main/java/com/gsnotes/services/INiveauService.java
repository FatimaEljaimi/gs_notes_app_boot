package com.gsnotes.services;

import com.gsnotes.bo.Niveau;
import com.gsnotes.utils.export.ExcelFile;

import java.util.List;

public interface INiveauService {

    List<Niveau> getAllNiveaux();

    Niveau getNiveau(Long id);

    int getSeuilBySession(Long id, String session);

    List<ExcelFile> prepareFiles(String session, Long idNiveau);

}
