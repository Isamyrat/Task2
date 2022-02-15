package root.developer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import root.developer.exception.DeveloperNotFoundException;
import root.developer.model.Developer;
import root.developer.service.DeveloperService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeveloperController {

    private final DeveloperService developerService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @RequestMapping(value = "/developer/save", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Boolean>> save(@Valid @RequestBody Developer developer){
        return developerService.save(developer);
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
    @RequestMapping(value = "/developer/update", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Boolean>> update(@RequestBody Developer developer){
        return developerService.update(developer);
    }

}
