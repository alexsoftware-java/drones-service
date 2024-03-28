package com.m.interview.interview.converter;

import com.m.interview.interview.dto.MedicationDto;
import com.m.interview.interview.entity.GoodsEntity;
import com.m.interview.interview.exception.DispatcherException;
import com.m.interview.interview.utils.GoodsTypes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GoodsEntityToMedicationDtoConverter implements Converter<GoodsEntity, MedicationDto> {
    @Override
    public MedicationDto convert(GoodsEntity source) {
        if (source.getGoodsType() != GoodsTypes.MEDICATIONS) {
            throw new DispatcherException("Can't get medication");
        }
        var medication = new MedicationDto();
        medication.setId(source.getId());
        medication.setName(source.getName());
        medication.setCode(source.getCode());
        medication.setWeight(source.getWeight());
        if (source.getImage() != null) {
            medication.setImageId(source.getImage().getId());
        }
        return medication;
    }
}
