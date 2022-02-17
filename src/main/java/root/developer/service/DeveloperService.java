package root.developer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.developer.exception.DeveloperNotFoundException;
import root.developer.model.Developer;
import root.developer.repository.DeveloperRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public Map<String, Developer> save(Developer developer) {
        Map<String, Developer> response = new LinkedHashMap<>();
        if(developer.getId() != null) {
            if (developerRepository.existsById(developer.getId())) {
                response.put("This id is busy", developer);
                return response;
            }
        }
        if (developerRepository.existsByEmail(developer.getEmail())) {
            response.put("Cant save new developer with this email because it is busy or check valid email(dog@gmail.com)", developer);
            return response;
        }
        if (!checkUserName(developer.getName())) {
            response.put("Cant save new developer with this name because name's length should be 50<name>2 or check valid name it should start with alphabet", developer);
            return response;
        }
        developerRepository.save(developer);
        Developer developer1 = developerRepository.findByEmail(developer.getEmail());
        response.put("Developer created", developer1);
        return response;
    }

    public Boolean checkUserName(String username) {
        if( username.length() > 2 && username.length() < 50){
            if (Pattern.matches("^[A-Za-z]", String.valueOf(username.charAt(0)))) {
                log.info("Developer with username: " + username + " passed verification");
                return true;
            }
        }
        return false;
    }

    public Boolean existsByEmail(String email) {
        if (!developerRepository.existsByEmail(email)) {
            return !Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email);
        }
        return true;
    }

    public Map<String, Developer> update(Developer developer) {
        Map<String, Developer> response = new LinkedHashMap<>();
        Developer developer1;
        if (developer.getId() != null) {
            developer1 = developerRepository.findById(developer.getId())
                    .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:" + developer.getId()));
            if (developer.getEmail() != null) {
                if (developerRepository.existsByEmail(developer.getEmail())) {
                    response.put("Cant save new developer with this email because it is busy or check valid email(dog@gmail.com)", developer);
                    return response;
                }
                developer1.setEmail(developer.getEmail());
            }
            if (developer.getName() != null) {
                if (!checkUserName(developer.getName())) {
                    response.put("Cant save new developer with this name because name's length should be 50<name>2 or check valid name it should start with alphabet", developer);
                    return response;
                }
                developer1.setName(developer.getName());
            }
            developerRepository.save(developer1);
        } else {
            response.put("Developer id is null!! Please write id:", developer);
            return response;
        }
        response.put("Developer created", developer);
        return response;
    }

    public Developer findById(Long id) throws DeveloperNotFoundException {
        return developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:" + id));
    }

    public List<Developer> findAll() {
        return (List<Developer>) developerRepository.findAll();
    }
}
