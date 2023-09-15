package com.musala.interview.entity;

import com.musala.interview.dto.Model;
import com.musala.interview.dto.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Collection;

import static com.musala.interview.utils.Constants.*;

@Entity(name = "drones")
@Getter
@Setter
public class DroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "drones_id_seq")
    @SequenceGenerator(name = "drones_id_seq", sequenceName = "drones_id_seq", allocationSize = 1)
    private long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Length(min = DRONE_MIN_SN_LENGTH, max = DRONE_MAX_SN_LENGTH)
    @Column(unique = true)
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Model model;

    @NotNull
    @Min(0)
    @Max(MAX_DRONE_CAPACITY)
    private Integer weightLimit;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer batteryCapacity = 100;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "id")
    private Collection<GoodsEntity> goods;
}