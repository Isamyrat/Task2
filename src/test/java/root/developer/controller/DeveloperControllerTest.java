package root.developer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import root.developer.model.Developer;
import root.developer.service.DeveloperService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DeveloperController.class)
class DeveloperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private DeveloperService developerService;

    @InjectMocks
    private DeveloperController developerController;

    Developer RECORD_1 = new Developer(1L, "Nikita", "ganu@gmail.com");
    Developer RECORD_2 = new Developer(2L, "Misha", "orlov@gmail.com");
    Developer RECORD_3 = new Developer(3L, "Sasha", "volchonok@gmail.com");

    @Test
    public void getAllRecords_success() throws Exception {
        List<Developer> developerList = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(developerService.findAll()).thenReturn(developerList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/developer/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Sasha")))
                .andExpect(jsonPath("$[0].email", is("ganu@gmail.com")));

    }
    @Test
    public void getDeveloperById_success() throws Exception {
        when(developerService.findById(RECORD_1.getId())).thenReturn(RECORD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/developer/find/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Nikita")))
                .andExpect(jsonPath("$.email", is("ganu@gmail.com")));
    }

    @Test
    public void createDeveloper_success() throws Exception {
        Developer developer = Developer.builder()
                .id(1L)
                .name("Andryusha")
                .email("varlamov@gmail.com")
                .build();


        Map<Validation,Developer> map  = new LinkedHashMap<>();
        map.put(Validation.CREATED,developer);

        Mockito.when(developerService.save(developer)).thenReturn(map);

        String content = objectWriter.writeValueAsString(developer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/developer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Andryusha")))
                .andExpect(jsonPath("$.email", is("varlamov@gmail.com")));
    }
    @Test
    public void updateDeveloper_success() throws Exception {
        Developer developer = Developer.builder()
                .id(1L)
                .name("Masha")
                .email("varlamova@gmail.com")
                .build();
        Map<Validation,Developer> map  = new LinkedHashMap<>();
        map.put(Validation.CREATED,developer);
        Mockito.when(developerService.findById(RECORD_1.getId())).thenReturn(RECORD_1);
        Mockito.when(developerService.update(developer)).thenReturn(map);

        String updateContent = objectWriter.writeValueAsString(developer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/developer/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Masha")))
                .andExpect(jsonPath("$.email", is("varlamova@gmail.com")));
    }
}