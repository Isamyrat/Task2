package root.developer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.developer.exception.DeveloperNotFoundException;
import root.developer.model.Developer;
import root.developer.repository.DeveloperRepository;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public Developer save(Developer developer) {
        if (developerRepository.existsByEmail(developer.getEmail())) {
            throw new DeveloperNotFoundException("Cant save new developer with this email because it is busy");
        }
        if (!checkUserName(developer.getName())) {
            throw new DeveloperNotFoundException("Cant save new developer with this email because it is busy");
        }
        developerRepository.save(developer);
        return developer;
    }

    public Boolean checkUserName(String username) {
        if (Pattern.matches("^[A-Za-z]", String.valueOf(username.charAt(0)))) {
            log.info("Developer with username: " + username + " passed verification");
            return true;
        }
        return false;
    }

    public Boolean existsByEmail(String email) {
        if (developerRepository.existsByEmail(email)) {
            return true;
        }
        log.error("Developer with email: " + email + " is not exist");
        return false;
    }

    public Developer update(Developer developer) {
        Developer developer1;
        if (developer.getId() != null) {
            developer1 = developerRepository.findById(developer.getId())
                    .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:" + developer.getId()));
            if (developer.getEmail() != null) {
                if (existsByEmail(developer.getEmail())) {
                    throw new DeveloperNotFoundException("Cant save new developer with this email because it is busy");
                }
                developer1.setEmail(developer.getEmail());
            }
            if (developer.getName() != null) {
                if (!checkUserName(developer.getName())) {
                    throw new DeveloperNotFoundException("Cant save new developer with this email because it is busy");
                }
                developer1.setName(developer.getName());
            }

            developerRepository.save(developer1);

        } else throw new DeveloperNotFoundException("Developer id is null");
        return developer1;
    }

    public Developer findById(Long id) throws DeveloperNotFoundException {
        return developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:" + id));
    }

    public List<Developer> findAll() {
        return (List<Developer>) developerRepository.findAll();
    }
}
