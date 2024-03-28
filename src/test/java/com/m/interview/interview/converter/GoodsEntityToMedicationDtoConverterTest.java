package com.m.interview.interview.converter;

import com.m.interview.interview.entity.DroneEntity;
import com.m.interview.interview.entity.GoodsEntity;
import com.m.interview.interview.entity.ImageEntity;
import com.m.interview.interview.exception.DispatcherException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoodsEntityToMedicationDtoConverterTest {

    private final GoodsEntityToMedicationDtoConverter converter = new GoodsEntityToMedicationDtoConverter();

    @Test
    void converterTest() {
        // given
        var entity = new GoodsEntity();
        entity.setGoodsType(1);
        entity.setWeight(100);
        entity.setDrone(new DroneEntity());
        entity.setName("Aspirin");
        entity.setCode("ASP");
        var image = new ImageEntity();
        image.setId(1);
        entity.setImage(image);
        // when
        var result = assertDoesNotThrow(() -> converter.convert(entity));
        // then
        assertEquals("Aspirin", result.getName());
        assertEquals("ASP", result.getCode());
        assertEquals(100, result.getWeight());
        assertEquals(1, result.getImageId());
    }

    @Test
    void convertThrowsExceptionOnWrongType() {
        var entity = new GoodsEntity();
        entity.setGoodsType(-1);
        assertThrows(DispatcherException.class, () -> converter.convert(entity));
    }
}