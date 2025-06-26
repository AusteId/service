package lt.techin.exam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String firstname;

  @Column(nullable = false, length = 100)
  private String lastname;

  @Column
  private LocalDate birthDate;

  @Column(length = 100)
  private String address;

  @Column(nullable = false, length = 15)
  private String phone;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, length = 100)
  private String city;

  @Column(nullable = false, length = 500)
  private String specialization;

  @Column(length = 1000)
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "shop_id", nullable = false)
  private Shop shop;

  @Column
  private Double averageRating;

  @Column
  private int ratingCount;

  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<Rating> receivedRatings = new ArrayList<>();
}
