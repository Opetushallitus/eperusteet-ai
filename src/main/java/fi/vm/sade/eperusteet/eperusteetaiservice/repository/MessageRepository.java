package fi.vm.sade.eperusteet.eperusteetaiservice.repository;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.ThreadWithCreateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByMessageId(String messageId);

    List<Message> findByThreadId(String threadId);

    List<Message> findByThreadIdIn(List<String> threadId);

    @Query(nativeQuery = true,
            value = "SELECT thread_id, min(created_at) as created_at " +
                    "FROM message " +
                    "WHERE created_at BETWEEN :startDate AND :endDate " +
                    "AND meta->>'sourceType' IN :sourceType " +
                    "AND (:educationLevel like '' OR meta->>'educationLevel' = :educationLevel) " +
                    "AND (:hasFeedbackOnly = false OR EXISTS (select 1 from feedback where message_id = message.message_id)) " +
                    "GROUP BY thread_id " +
                    "ORDER BY min(created_at) DESC")
    Page<ThreadWithCreateTime> findByParameters(Date startDate, Date endDate, List<String> sourceType, String educationLevel, Boolean hasFeedbackOnly, Pageable pageable);
}
