package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.AppPreferenceProperty;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.AppPreferenceDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.repository.AppPreferenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppPreferenceService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppPreferenceRepository appPreferenceRepository;

    public AppPreferenceDto getAppPreference(AppPreferenceProperty appPreferenceProperty) {
        return modelMapper.map(appPreferenceRepository.findByProperty(appPreferenceProperty), AppPreferenceDto.class);
    }

    public boolean isOpsAiAvailable() {
        return Boolean.parseBoolean(getAppPreference(AppPreferenceProperty.SERVICE_AVAILABLE).getValue());
    }
}
