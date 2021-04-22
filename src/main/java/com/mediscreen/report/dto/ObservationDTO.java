package com.mediscreen.report.dto;

public class ObservationDTO {
    private String id;
    private String content;
    private Integer idPatient;

    public ObservationDTO(){}

    public ObservationDTO(String id, String content, Integer idPatient) {
        this.id = id;
        this.content = content;
        this.idPatient = idPatient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }
}
