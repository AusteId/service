package lt.techin.exam.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Column(nullable = false, length = 100)
  private String address;

  @Column(nullable = false, length = 15)
  private String phone;

  @Column(nullable = false, length = 100)
  private String email;

  @Column(nullable = false, length = 100)
  private String city;

  @Column(nullable = false, length = 500)
  private String description;

  @Column(nullable = false, length = 200)
  private String workingHours;

  @Column(nullable = false)
  private String imagePath;

  @Column(nullable = false)
  @Setter(AccessLevel.NONE)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
  private List<Employee> employees = new ArrayList<>();

  @PrePersist
  private void onCreate() {
    createdAt = LocalDateTime.now();
  }

}
