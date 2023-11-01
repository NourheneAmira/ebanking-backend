package com.example.ebankingbackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Customer implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    // JsonProperty cad si je consulte un customer j ai pas besion de consulter la liste des bank account
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> accounts;
}
