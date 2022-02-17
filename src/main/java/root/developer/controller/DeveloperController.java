package root.developer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.developer.exception.DeveloperNotFoundException;
import root.developer.model.Developer;
import root.developer.service.DeveloperService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperController {

    private final DeveloperService developerService;


    @RequestMapping(value = "/developer/save", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Developer>> save(@Valid @RequestBody Developer developer){
        Map<String,Developer> map = developerService.save(developer);
        return ResponseEntity.ok(map);
    }

    @RequestMapping(value = "/developer/findAll",method = RequestMethod.GET)
    public List<Developer> findAll(){
        return developerService.findAll();
    }

    @RequestMapping(value = "/developer/find/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable("id") Long id) throws DeveloperNotFoundException {
        Developer developer = developerService.findById(id);
        if (developer == null) {
            throw  new DeveloperNotFoundException("Developer must not be null");
        } else {
            return new ResponseEntity<>(developer, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/developer/update", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Developer>> update(@Valid @RequestBody Developer developer){
        Map<String,Developer> map = developerService.update(developer);

        return ResponseEntity.ok(map);
    }
}
