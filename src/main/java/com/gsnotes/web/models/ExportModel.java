package com.gsnotes.web.models;


public class ExportModel {

    
    private Long idModule;

    private Long idNiveau;

    private String session;

    public ExportModel() {}

    public ExportModel(Long idModule, String session) {
        this.idModule = idModule;
        this.session = session;
    }

    public ExportModel(String session, Long idNiveau) {
        this.idNiveau = idNiveau;
        this.session = session;
    }

    public Long getIdModule() {
        return idModule;
    }

    public void setIdModule(Long idModule) {
        this.idModule = idModule;
    }

    public Long getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(Long idNiveau) {
        this.idNiveau = idNiveau;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
