package root.developer.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import root.developer.model.Developer;
import root.developer.repository.DeveloperRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeveloperServiceTest {

    @Autowired
    private DeveloperService developerService;

    @MockBean
    private DeveloperRepository developerRepository;

    Developer RECORD_1 = new Developer(1L, "Nikita", "ganu@gmail.com");

    @Test
    void save() {
        Developer developer = new Developer(1L,"Nikita", "ganu@gmail.com");

        when(developerRepository.save(developer)).thenReturn(developer);
    }

    @Test
    void update() {
        Developer developer = new Developer(1L,"Nikita", "ganu@gmail.com");

        when(developerRepository.findById(RECORD_1.getId())).thenReturn(Optional.ofNullable(RECORD_1));
        when(developerRepository.save(developer)).thenReturn(developer);
    }

    @Test
    void findById() {
        Developer developer = new Developer(1L,"Nikita", "ganu@gmail.com");
        when(developerRepository.findById(1L)).thenReturn(Optional.of(developer));
        assertThat(developerService.findById(1L)).isEqualTo(developer);
    }

    @Test
    void findAll() {
        when(developerRepository.findAll()).thenReturn(Stream.of(new Developer(101L, "sasha","sasha@gmail.com")).collect(Collectors.toList()));
        assertEquals(1, developerService.findAll().size());
    }
}