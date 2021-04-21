package com.mediscreen.report.controller;

import com.mediscreen.report.dto.PatientDTO;
import com.mediscreen.report.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ReportControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReportService reportService;


    @Test
    public void testShowReportHome() throws Exception {

        mockMvc.perform(get("/home"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("home"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    public void testGenerateReport() throws Exception {

        when(reportService.getPatientById(1)).thenReturn(java.util.Optional.of(new PatientDTO(
                1,"bob","bobby","M","Rue des bobby","0123456789","01/10/1997"
        )));

        mockMvc.perform(post("/report")
                .param("id","1")
                .param("firstName", "bob")
                .param("lastName", "bobby")
                .param("sex", "M")
                .param("address", "Rue des Bobby")
                .param("dateOfBirth", "01/10/1997")
                .param("phone", "0123456789"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("report"));
    }

}
