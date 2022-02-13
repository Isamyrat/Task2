package root.developer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import root.developer.exception.DeveloperNotFoundException;
import root.developer.model.Developer;
import root.developer.repository.DeveloperRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public ResponseEntity<Map<String, Boolean>> save(Developer developer){
        Map<String, Boolean> response = new HashMap<>();
        Developer developer1 = developerRepository.findByEmail(developer.getEmail());
        if(developer1 != null){
            response.put("This email is busy", Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        if(developer.getName().length() > 50 || developer.getName().length() < 2){
            response.put("Name should have length from 2 to 50 characters", Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", developer.getEmail())){
            response.put("Email is incorrect!! For example(qwerty@gmail.com)", Boolean.FALSE);
            return ResponseEntity.ok(response);
        }

        if(!Pattern.matches("^[A-Za-z]",  String.valueOf(developer.getName().charAt(0)))){
            response.put("Name always should start with alphabet", Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        developerRepository.save(developer);
        response.put("Developer created", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Boolean>> update(Developer developer){
        Map<String, Boolean> response = new HashMap<>();
        if(developer.getId() == 0) {
            response.put("Please write id", Boolean.FALSE);
            return ResponseEntity.ok(response);
        }
        Developer developer1 = developerRepository.findById(developer.getId())
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:"  + developer.getId()));

        if(developer.getEmail() != null){
            if(developerRepository.findByEmail(developer.getEmail()) != null) {
                response.put("This email is busy", Boolean.FALSE);
                return ResponseEntity.ok(response);
            }
            else developer1.setEmail(developer.getEmail());

            if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", developer.getEmail())){
                response.put("Email is incorrect!! For example(qwerty@gmail.com)", Boolean.FALSE);
                return ResponseEntity.ok(response);
            }
        }
        if(developer.getName() != null){
            if(developer.getName().length() > 50 || developer.getName().length() < 2){
                response.put("Name should have length from 2 to 50 characters", Boolean.FALSE);
                return ResponseEntity.ok(response);
            }

            if(!Pattern.matches("^[A-Za-z]",  String.valueOf(developer.getName().charAt(0)))){
                response.put("Name always should start with alphabet", Boolean.FALSE);
                return ResponseEntity.ok(response);
            }

            developer1.setName(developer.getName());
        }
        developerRepository.save(developer1);
        response.put("Developer created", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public Developer findById(Long id){
        return developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:"  + id));
    }
    public List<Developer> findAll(){
        return (List<Developer>) developerRepository.findAll();
    }
}
