package com.m.interview.interview.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.m.interview.interview.utils.Constants.MAX_DRONE_CAPACITY;

@Entity(name = "goods")
@Getter
@Setter
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    private int goodsType;

    @NotEmpty
    private String name;

    @NotNull
    @Min(0)
    @Max(MAX_DRONE_CAPACITY)
    private int weight;

    @NotEmpty
    private String code;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "drone_id", referencedColumnName = "id", nullable = false)
    private DroneEntity drone;

    @OneToOne
    @JoinColumn(name="image_id", referencedColumnName = "id")
    private ImageEntity image;
}
