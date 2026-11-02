package dev.fefeutitan.housing_benefits_demo.applicant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    boolean existsByEmail(String email);
}
