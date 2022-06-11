package com.gsnotes.web.controllers;

import com.gsnotes.services.IModuleService;
import com.gsnotes.services.INiveauService;
import com.gsnotes.utils.export.CustomExcelExporter;
import com.gsnotes.utils.export.ExcelFile;
import com.gsnotes.utils.export.ZipExporter;
import com.gsnotes.web.models.ExportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Fatima El Jaimi
 */

@Controller
@RequestMapping("/cadreadmin")
public class ExportController {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private INiveauService niveauService;

    @GetMapping("/showModuleExportForm")
    public String showModuleExportForm(Model m) {

        ExportModel ex = new ExportModel();
        m.addAttribute("exportModel", ex);

        m.addAttribute("modules", moduleService.getAllModules());
        return "cadreadmin/moduleExportForm";
    }

    @GetMapping("/showNiveauExportForm")
    public String showNiveauExportForm(Model m) {

        ExportModel ex = new ExportModel();
        m.addAttribute("archiveExportModel", ex);

        m.addAttribute("niveaux", niveauService.getAllNiveaux());
        return "cadreadmin/niveauExportForm";
    }

    @PostMapping(value = "exportModuleFile", produces = "application/octet-stream")
    public void exportFichier(@ModelAttribute("exportModel") ExportModel exportModel, HttpServletResponse response) throws IOException {

        Long idModule = exportModel.getIdModule();
        String session = exportModel.getSession();
        String fileName = moduleService.getModule(idModule).getTitre() + "_excelFile_Session" + session + ".xlsx";

        setResponseHeader(fileName, response);

        ExcelFile excelFile = moduleService.prepareFile(idModule, session);

        CustomExcelExporter exporter = new CustomExcelExporter(excelFile);

        exporter.export(response);

    }

    @PostMapping(value = "exportArchiveFile", produces = "application/zip")
    public void exportArchive(@ModelAttribute("exportModel") ExportModel exportModel, HttpServletResponse response) throws IOException {

        Long idNiveau = exportModel.getIdNiveau();
        String session = exportModel.getSession();
        String fileName = niveauService.getNiveau(idNiveau).getAlias() + "_archiveFile_Session" + session + ".zip";

        setResponseHeader(fileName, response);

        List<ExcelFile> excelFiles = niveauService.prepareFiles(session, idNiveau);

        ZipExporter zipExporter = new ZipExporter(excelFiles);

        zipExporter.export(response);

    }

    public void setResponseHeader(String fileName, HttpServletResponse response) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + currentDateTime + "_" + fileName;
        response.setHeader(headerKey, headerValue);
    }
}
