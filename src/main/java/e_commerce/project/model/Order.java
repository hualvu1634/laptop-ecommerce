package e_commerce.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;      

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;  

    @Column(name = "total_money")
    private Long totalMoney;    

    @Column(name = "order_date")
    private LocalDateTime orderDate;      
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
   
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}