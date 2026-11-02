package dev.fefeutitan.housing_benefits_demo.applicant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {

    @Mock
    private ApplicantRepository applicantRepository;

    @Test
    void shouldCreateApplicant() {
        ApplicantService service = new ApplicantService(applicantRepository);
        CreateApplicantRequest request = new CreateApplicantRequest("Maria Silva", "maria@example.com");
        given(applicantRepository.existsByEmail(request.email())).willReturn(false);
        given(applicantRepository.save(any(Applicant.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ApplicantResponse response = service.create(request);

        assertThat(response.name()).isEqualTo("Maria Silva");
        assertThat(response.email()).isEqualTo("maria@example.com");
        verify(applicantRepository).save(any(Applicant.class));
    }

    @Test
    void shouldRejectDuplicateEmail() {
        ApplicantService service = new ApplicantService(applicantRepository);
        CreateApplicantRequest request = new CreateApplicantRequest("Maria Silva", "maria@example.com");
        given(applicantRepository.existsByEmail(request.email())).willReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ApplicantAlreadyExistsException.class);
    }

    @Test
    void shouldFindApplicantById() {
        ApplicantService service = new ApplicantService(applicantRepository);
        Applicant applicant = new Applicant("Maria Silva", "maria@example.com");
        given(applicantRepository.findById(applicant.getId())).willReturn(Optional.of(applicant));

        ApplicantResponse response = service.findById(applicant.getId());

        assertThat(response.id()).isEqualTo(applicant.getId());
        assertThat(response.email()).isEqualTo("maria@example.com");
    }

    @Test
    void shouldThrowWhenApplicantDoesNotExist() {
        ApplicantService service = new ApplicantService(applicantRepository);
        UUID id = UUID.randomUUID();
        given(applicantRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ApplicantNotFoundException.class);
    }
}
