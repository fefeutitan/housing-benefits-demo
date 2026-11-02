package dev.fefeutitan.housing_benefits_demo.applicant;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicantResponse(
        UUID id,
        String name,
        String email,
        OffsetDateTime createdAt
) {

    public static ApplicantResponse from(Applicant applicant) {
        return new ApplicantResponse(
                applicant.getId(),
                applicant.getName(),
                applicant.getEmail(),
                applicant.getCreatedAt()
        );
    }
}
