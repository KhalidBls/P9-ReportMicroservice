package com.mediscreen.report.service;

import com.mediscreen.report.proxies.ObservationProxy;
import com.mediscreen.report.proxies.PatientProxy;
import com.mediscreen.report.dto.ObservationDTO;
import com.mediscreen.report.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportService {

    private final PatientProxy patientProxy;
    private final ObservationProxy observationProxy;

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

        return "error";
    }


    public int ageCalculation(String birthdate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDateFormat = null;
        try {
            birthDateFormat = sdf.parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        now.setTime(birthDateFormat);

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);

        LocalDate localD = LocalDate.of(year, month, date);
        LocalDate nowDate = LocalDate.now();
        Period diff1 = Period.between(localD, nowDate);
        return diff1.getYears();
    }

    public Integer countAlertWord(List<ObservationDTO> observationsOfPatient){
        List<String> words = new ArrayList<>();
        words.add("Hémoglobine A1C");
        words.add("Microalbumine");
        words.add("Taille");
        words.add("Poids");
        words.add("Fumeur");
        words.add("Anormal");
        words.add("Cholestérol");
        words.add("Vertige");
        words.add("Rechute");
        words.add("Réaction");
        words.add("Anticorps");

        AtomicInteger numberOfWord = new AtomicInteger();

        words.forEach(word -> {
            observationsOfPatient.forEach(observation -> {
                if(observation.getContent().toLowerCase().contains(word.toLowerCase())){
                    numberOfWord.getAndIncrement();
                }
            });
        });

        return numberOfWord.get();

    }

}
