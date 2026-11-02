package dev.fefeutitan.housing_benefits_demo.applicant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicantController.class)
class ApplicantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicantService applicantService;

    @Test
    void shouldCreateApplicant() throws Exception {
        UUID id = UUID.randomUUID();
        ApplicantResponse response = new ApplicantResponse(
                id,
                "Maria Silva",
                "maria@example.com",
                OffsetDateTime.parse("2026-01-01T10:00:00Z")
        );
        given(applicantService.create(new CreateApplicantRequest("Maria Silva", "maria@example.com")))
                .willReturn(response);

        mockMvc.perform(post("/api/applicants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Maria Silva",
                                  "email": "maria@example.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Maria Silva"))
                .andExpect(jsonPath("$.email").value("maria@example.com"));
    }

    @Test
    void shouldListApplicants() throws Exception {
        given(applicantService.findAll()).willReturn(List.of(
                new ApplicantResponse(
                        UUID.randomUUID(),
                        "Maria Silva",
                        "maria@example.com",
                        OffsetDateTime.parse("2026-01-01T10:00:00Z")
                )
        ));

        mockMvc.perform(get("/api/applicants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maria Silva"))
                .andExpect(jsonPath("$[0].email").value("maria@example.com"));
    }

    @Test
    void shouldFindApplicantById() throws Exception {
        UUID id = UUID.randomUUID();
        given(applicantService.findById(id)).willReturn(new ApplicantResponse(
                id,
                "Maria Silva",
                "maria@example.com",
                OffsetDateTime.parse("2026-01-01T10:00:00Z")
        ));

        mockMvc.perform(get("/api/applicants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("maria@example.com"));
    }

    @Test
    void shouldReturnNotFoundWhenApplicantDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        given(applicantService.findById(id)).willThrow(new ApplicantNotFoundException(id));

        mockMvc.perform(get("/api/applicants/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Applicant not found: " + id));
    }

    @Test
    void shouldRejectInvalidApplicant() throws Exception {
        mockMvc.perform(post("/api/applicants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "email": "invalid-email"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
