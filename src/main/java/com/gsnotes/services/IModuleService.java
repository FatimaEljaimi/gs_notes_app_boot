package com.gsnotes.services;

import com.gsnotes.bo.Module;
import com.gsnotes.utils.export.ExcelFile;

import java.util.List;

public interface IModuleService {

    List<Module> getAllModules();

    Module getModule(Long id);

    String[][] prepareHeaderData(long idModule, String session);

    String EnseignantsToString(Module module);

    String[][] prepareData(long idModule, String session);

    ExcelFile prepareFile(long idModule, String session);

}
