package fi.vm.sade.eperusteet.eperusteetaiservice.repository;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.AppPreference;
import fi.vm.sade.eperusteet.eperusteetaiservice.domain.AppPreferenceProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppPreferenceRepository extends JpaRepository<AppPreference, Long> {
    AppPreference findByProperty(AppPreferenceProperty property);
}
