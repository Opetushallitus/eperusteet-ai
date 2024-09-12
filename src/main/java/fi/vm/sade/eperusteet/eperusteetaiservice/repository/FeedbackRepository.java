package fi.vm.sade.eperusteet.eperusteetaiservice.repository;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
