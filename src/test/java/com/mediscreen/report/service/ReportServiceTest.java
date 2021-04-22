package com.mediscreen.report.service;

import com.mediscreen.report.dto.ObservationDTO;
import com.mediscreen.report.dto.PatientDTO;
import com.mediscreen.report.proxies.ObservationProxy;
import com.mediscreen.report.proxies.PatientProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceTest {

    @MockBean
    private PatientProxy patientProxy;
    @MockBean
    private ObservationProxy observationProxy;

    @InjectMocks
    private ReportService reportService;

    @Test
    public void shouldReturnNoneWhenWordIs0(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent toujours aussi bien",3));
        PatientDTO patient = new PatientDTO(
                3,"bob","bobby","M",
                "Rue des bobby","0123456789","01/10/1997");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("None"));
    }

    @Test
    public void shouldReturnInDangerWhenWordIsMoreThan3AndPatientIsMaleOf25(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Hémoglobine A1C",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));
        PatientDTO patient = new PatientDTO(
                3,"bob","bobby","M",
                "Rue des bobby","0123456789","01/10/1995");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("In Danger"));
    }

    @Test
    public void shouldReturnEarlyOnsetWhenWordIsMoreThan5AndPatientIsMaleOf25(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Hémoglobine A1C",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient ne développe pas d'Anticorps et a des Rechute",3));
        PatientDTO patient = new PatientDTO(
                3,"bob","bobby","M",
                "Rue des bobby","0123456789","01/10/1995");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("Early Onset"));
    }

    @Test
    public void shouldReturnEarlyOnsetWhenWordIsMoreThan7AndPatientIsFemaleOf25(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Hémoglobine A1C",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient ne développe pas d'Anticorps et a des Rechute",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient est Fumeur et a pris beaucoup de Poids",3));
        PatientDTO patient = new PatientDTO(
                3,"marie","marie","F",
                "Rue des bobby","0123456789","01/10/1995");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("Early Onset"));
    }

    @Test
    public void shouldReturnInDangerWhenWordIsMoreThan4AndPatientIsFemaleOf25(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Hémoglobine A1C",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient ne développe pas d'Anticorps",3));

        PatientDTO patient = new PatientDTO(
                3,"marie","marie","F",
                "Rue des bobby","0123456789","01/10/1995");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("In Danger"));
    }

    @Test
    public void shouldReturnBorderlineWhenWordIsMoreThan2AndPatientIs35(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));

        PatientDTO patient = new PatientDTO(
                3,"marie","marie","F",
                "Rue des bobby","0123456789","01/10/1985");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("Borderline"));
    }

    @Test
    public void shouldReturnInDangerWhenWordIsMoreThan6AndPatientIs35(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Hémoglobine A1C",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient ne développe pas d'Anticorps",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient perd de la Taille",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a une voix Anormal",3));

        PatientDTO patient = new PatientDTO(
                3,"marie","marie","F",
                "Rue des bobby","0123456789","01/10/1985");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("In Danger"));
    }

    @Test
    public void shouldReturnEarlyOnsetWhenWordIsMoreThan8AndPatientIs35(){
        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Hémoglobine A1C",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a les symptomes de Microalbumine et de Cholestérol",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient ne développe pas d'Anticorps",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient perd de la Taille",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a une voix Anormal",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient est Fumeur et a des Vertiges",3));

        PatientDTO patient = new PatientDTO(
                3,"marie","marie","F",
                "Rue des bobby","0123456789","01/10/1985");

        //WHEN - THEN
        assertTrue(reportService.getRiskOfPatient(observationsOfPatient,patient).equals("Early Onset"));
    }

    @Test
    public void shouldReturn0When0WordAreEquals(){

        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient se sent très bien",3));

        //WHEN -THEN
        assertTrue(reportService.countAlertWord(observationsOfPatient) == 0);
    }

    @Test
    public void shouldReturn5When5WordAreEquals(){

        //GIVEN
        List<ObservationDTO> observationsOfPatient = new ArrayList<>();
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient ne développe pas d'Anticorps",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient perd de la Taille",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient a une voix Anormal",3));
        observationsOfPatient.add(new ObservationDTO("sha1",
                "Le patient est Fumeur et a des Vertiges",3));

        //WHEN -THEN
        assertTrue(reportService.countAlertWord(observationsOfPatient) == 5);
    }

}
