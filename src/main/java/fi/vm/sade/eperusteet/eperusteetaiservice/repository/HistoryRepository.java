package fi.vm.sade.eperusteet.eperusteetaiservice.repository;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<History> findByMessageId(String messageId);
}
