package dev.chel_shev.fast.entity;

import dev.chel_shev.fast.type.FastInquiryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "fast_command")
public class FastCommandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String label;
    @Enumerated(EnumType.STRING)
    private FastInquiryType type;

    public FastCommandEntity(String name, String label, FastInquiryType type) {
        this.name = name;
        this.label = label;
        this.type = type;
    }

    public FastCommandEntity() {
    }
}