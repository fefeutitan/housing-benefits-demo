package dev.fefeutitan.housing_benefits_demo.applicant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Transactional
    public ApplicantResponse create(CreateApplicantRequest request) {
        if (applicantRepository.existsByEmail(request.email())) {
            throw new ApplicantAlreadyExistsException(request.email());
        }

        Applicant applicant = new Applicant(request.name(), request.email());
        return ApplicantResponse.from(applicantRepository.save(applicant));
    }

    @Transactional(readOnly = true)
    public List<ApplicantResponse> findAll() {
        return applicantRepository.findAll().stream()
                .map(ApplicantResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ApplicantResponse findById(UUID id) {
        return applicantRepository.findById(id)
                .map(ApplicantResponse::from)
                .orElseThrow(() -> new ApplicantNotFoundException(id));
    }
}
