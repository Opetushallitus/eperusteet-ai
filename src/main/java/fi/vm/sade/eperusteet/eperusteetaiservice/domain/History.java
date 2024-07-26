package fi.vm.sade.eperusteet.eperusteetaiservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String messageId;
    private String threadId;
    private Date createdAt;
    private String role;
    private String sourceType;
    private String sourceId;
    private String sourceLanguage;
    private String sourceRevision;
    private String content;
}
