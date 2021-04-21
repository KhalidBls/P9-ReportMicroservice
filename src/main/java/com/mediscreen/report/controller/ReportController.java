package com.mediscreen.report.controller;

import com.mediscreen.report.dto.PatientDTO;
import com.mediscreen.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/home")
    public ModelAndView showHome() {
        String viewName = "home";
        Map<String,Object> model = new HashMap<>();
        model.put("patients",reportService.getPatients());
        model.put("patient",new PatientDTO());

        return new ModelAndView(viewName,model);
    }

    @PostMapping("/report")
    public ModelAndView showReport( PatientDTO patientTransfered) {

        if(patientTransfered.getId()==null) {
            RedirectView redirect = new RedirectView();
            redirect.setUrl("/home");
            return new ModelAndView(redirect);
        }

        Optional<PatientDTO> patientDTO = reportService.getPatientById(patientTransfered.getId());

        if( !patientDTO.isPresent()) {
            RedirectView redirect = new RedirectView();
            redirect.setUrl("/home");
            return new ModelAndView(redirect);
        }

        Map<String,Object> model = new HashMap<>();
        String viewName = "report";

        model.put("patient",patientDTO.get());
        model.put("risk",reportService.getRiskOfPatient(reportService.getObservationsOfPatient(patientDTO.get().getId()),patientDTO.get()));

        return new ModelAndView(viewName,model);
    }

}
