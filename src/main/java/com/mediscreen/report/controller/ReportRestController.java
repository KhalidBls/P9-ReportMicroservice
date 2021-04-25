package com.mediscreen.report.controller;

import com.mediscreen.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportRestController {

    private final ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("report/risk/{id}")
    public String getRiskOfPatientById(@PathVariable int id){
        return reportService.getRiskOfPatient(reportService.getObservationsOfPatient(id),
                reportService.getPatientById(id).get());
    }

}
