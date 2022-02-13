package root.developer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.developer.model.Developer;
import root.developer.service.DeveloperService;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperController {

    private final DeveloperService developerService;

    @RequestMapping(value = "/developer/save", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Boolean>> save(@RequestBody Developer developer){
        return developerService.save(developer);
    }
    @RequestMapping(value = "/developer/findAll",method = RequestMethod.GET)
    public List<Developer> findAll(){
        return developerService.findAll();
    }

    @RequestMapping(value = "/developer/find/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Developer developer = developerService.findById(id);
        if (developer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(developer, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/developer/update", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Boolean>> update(@RequestBody Developer developer){
        return developerService.update(developer);
    }

}
