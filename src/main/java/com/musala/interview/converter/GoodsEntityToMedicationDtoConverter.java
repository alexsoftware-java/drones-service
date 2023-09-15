package com.musala.interview.converter;

import com.musala.interview.dto.MedicationDto;
import com.musala.interview.entity.GoodsEntity;
import com.musala.interview.exception.DispatcherException;
import com.musala.interview.utils.GoodsTypes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GoodsEntityToMedicationDtoConverter implements Converter<GoodsEntity, MedicationDto> {
    @Override
    public MedicationDto convert(GoodsEntity source) {
        if (source.getGoodsType() != GoodsTypes.MEDICATIONS) {
            throw new DispatcherException("Illegal argument during goods to medication conversion given!");
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
