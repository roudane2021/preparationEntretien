package com.roudane.preparationentretien;

import com.roudane.preparationentretien.repository.UserRepository;
import com.roudane.preparationentretien.repository.entity.OrderEntity;
import com.roudane.preparationentretien.repository.entity.OrderLineEntity;
import com.roudane.preparationentretien.repository.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class PreparationEntretienApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreparationEntretienApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {

           this.createSampleData(userRepository);
        };
    }

    private void createSampleData(UserRepository userRepository) {
        // 1. Création de l'utilisateur
        UserEntity user = UserEntity.builder()
                .firstName("Rachid")
                .lastName("Roudane")
                .email("rachid.roudane@example.com")
                .phone("+33600000000")
                .build();

        // 2. Création de la commande
        OrderEntity order = OrderEntity.builder()
                .build();

        // 3. Création des lignes de commande
        OrderLineEntity line1 = OrderLineEntity.builder()
                .productName("Clavier Mécanique")
                .quantity(1)
                .unitPrice(new BigDecimal("120.00"))
                .build();

        OrderLineEntity line2 = OrderLineEntity.builder()
                .productName("Souris Sans Fil")
                .quantity(2)
                .unitPrice(new BigDecimal("45.50"))
                .build();

        // 4. Association bidirectionnelle via la méthode d'assistance (calcule aussi le total)
        order.addOrderLine(line1);
        order.addOrderLine(line2);

        // 5. Association User <-> Order
        order.setUser(user);
        user.getOrders().add(order);

        // 6. Sauvegarde globale par cascade
        userRepository.save(user);

    }

}
