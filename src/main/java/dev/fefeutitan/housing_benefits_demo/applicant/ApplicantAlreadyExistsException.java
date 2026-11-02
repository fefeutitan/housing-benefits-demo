package dev.fefeutitan.housing_benefits_demo.applicant;

public class ApplicantAlreadyExistsException extends RuntimeException {

    public ApplicantAlreadyExistsException(String email) {
        super("An applicant with this email already exists: " + email);
    }
}
