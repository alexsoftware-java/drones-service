package com.musala.interview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.time.LocalDateTime;

@Entity(name = "image")
@Getter
@Setter
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "image_id_seq")
    @SequenceGenerator(name = "image_id_seq", sequenceName = "image_id_seq", allocationSize = 1)
    private long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name="goods_id", referencedColumnName = "id", nullable = false)
    private GoodsEntity goods;

    @Lob
    @JdbcType(value = VarbinaryJdbcType.class)
    @Column(name = "image", length = 1_000_000)
    private byte[] image;
}
