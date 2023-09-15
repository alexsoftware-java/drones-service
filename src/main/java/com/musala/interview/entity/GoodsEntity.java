package com.musala.interview.entity;

import com.musala.interview.utils.GoodsTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

import static com.musala.interview.utils.Constants.MAX_DRONE_CAPACITY;

@Entity(name = "goods")
@Getter
@Setter
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "goods_id_seq")
    @SequenceGenerator(name = "goods_id_seq", sequenceName = "goods_id_seq", allocationSize = 1)
    private long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    private int goodsType = GoodsTypes.MEDICATIONS;

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

    @Nullable
    @OneToOne
    @JoinColumn(name="image_id", referencedColumnName = "id")
    private ImageEntity image;
}
