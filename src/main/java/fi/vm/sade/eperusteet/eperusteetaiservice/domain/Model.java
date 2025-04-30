package fi.vm.sade.eperusteet.eperusteetaiservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Model {
    @Id
    private String id;
    private boolean defaultModel;
    private String description;
}
