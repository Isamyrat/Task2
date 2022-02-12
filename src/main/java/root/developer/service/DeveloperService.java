package root.developer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.developer.exception.DeveloperNotFoundException;
import root.developer.model.Developer;
import root.developer.repository.DeveloperRepository;
import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperService {

    private DeveloperRepository developerRepository;

    public Boolean save(Developer developer){
        Developer developer1 = developerRepository.findByEmail(developer.getEmail());
        if(developer1 == null){
            developerRepository.save(developer);
            return true;
        }else return false;
    }

    public Boolean update(Developer developer){
        Developer developer1 = developerRepository.findById(developer.getId())
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:"  + developer.getId()));
        if(developer.getEmail() != null){
            if(developerRepository.findByEmail(developer.getEmail()) != null) return false;
            else developer1.setEmail(developer.getEmail());
        }
        if(developer.getName() != null){
            developer1.setName(developer.getName());
        }
        developerRepository.save(developer1);
        return true;
    }

    public Developer findById(Long id){
        return developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not exist with id:"  + id));
    }
    public List<Developer> findAll(){
        return (List<Developer>) developerRepository.findAll();
    }
}
