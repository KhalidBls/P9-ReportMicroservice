package com.mediscreen.report.service;

import com.mediscreen.report.proxies.ObservationProxy;
import com.mediscreen.report.proxies.PatientProxy;
import com.mediscreen.report.dto.ObservationDTO;
import com.mediscreen.report.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportService {

    private final PatientProxy patientProxy;
    private final ObservationProxy observationProxy;
   private static final String[] WORDS = {"Hémoglobine A1C","Microalbumine","Taille","Poids","Fumeur",
           "Anormal","Cholestérol","Vertige","Rechute","Réaction","Anticorps"};

    @Autowired
    public ReportService(PatientProxy patientProxy,ObservationProxy observationProxy){
        this.patientProxy = patientProxy;
        this.observationProxy = observationProxy;
    }

    public List<PatientDTO> getPatients() {
        return patientProxy.getPatients();
    }

    public List<ObservationDTO> getObservationsOfPatient(Integer id){
        return  observationProxy.getObservationByPatientId(id);
    }

    public Optional<PatientDTO> getPatientById(Integer id) {
        return patientProxy.getPatients().stream().parallel().filter(p -> p.getId() == id ).findAny();
    }

    public String getRiskOfPatient(List<ObservationDTO> observationsOfPatient, PatientDTO patientDTO) {

        int wordAlert = countAlertWord(observationsOfPatient);

        if(wordAlert == 0)
            return "None";

        if( ageCalculation(patientDTO.getDateOfBirth()) < 30){
            if(patientDTO.getSex().equals("M")){
                if (wordAlert >= 5)
                    return "Early Onset";
                if (wordAlert >= 3 )
                    return "In Danger";
            }else{
                if (wordAlert >= 7 )
                    return "Early Onset";
                if (wordAlert >= 4)
                    return "In Danger";
            }
        }else{
            if (wordAlert >= 8)
                return "Early Onset";
            if (wordAlert >= 6 )
                return "In Danger";
            if(wordAlert >= 2 )
                return "Borderline";
        }

        return "None";
    }


    public int ageCalculation(String birthdate){
        LocalDate localD = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate nowDate = LocalDate.now();
        return Period.between(localD, nowDate).getYears();
    }

    public Integer countAlertWord(List<ObservationDTO> observationsOfPatient){
        AtomicInteger numberOfWord = new AtomicInteger();

        for(String word : WORDS){
            observationsOfPatient.forEach(observation -> {
                if(observation.getContent().toLowerCase().contains(word.toLowerCase())){
                    numberOfWord.getAndIncrement();
                }
            });
        }
        return numberOfWord.get();
    }

}
