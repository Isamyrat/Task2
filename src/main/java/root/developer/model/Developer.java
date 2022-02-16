package root.developer.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Builder
@Table(name = "developer")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50, message = "Name must have at least 2-50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Email should be valid!! (qwerty@gmail.com)")
    @Column(name = "email", nullable = false)
    private String email;
}
