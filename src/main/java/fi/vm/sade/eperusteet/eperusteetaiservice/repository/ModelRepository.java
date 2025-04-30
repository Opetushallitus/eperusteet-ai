package fi.vm.sade.eperusteet.eperusteetaiservice.repository;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Model;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.ModelDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, String> {
    Model findByDefaultModelTrue();
}
