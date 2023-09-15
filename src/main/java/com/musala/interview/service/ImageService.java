package com.musala.interview.service;

import com.musala.interview.entity.ImageEntity;
import com.musala.interview.exception.DispatcherException;
import com.musala.interview.repository.GoodsRepository;
import com.musala.interview.repository.ImageRepository;
import com.musala.interview.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final GoodsRepository goodsRepository;

    /**
     * Compress and save image as bytearray
     *
     * @param medicationId id of medication
     * @param imageBytes   array of bytes represents JPEG image
     */
    public void addImage(Long medicationId, MultipartFile imageBytes) {
        var medication = goodsRepository.findById(medicationId);
        if (medication.isEmpty()) {
            throw new DispatcherException("Can't add photo of medication - medication with Id %d not found".formatted(medicationId));
        }
        if (imageBytes.isEmpty()) {
            throw new DispatcherException("Can't add photo of medication - photo is empty");
        }
        if (imageBytes.getSize() > 1_000_000) {
            throw new DispatcherException("Can't add photo of medication - image size is bigger when allowed(1MB)");
        }
        var image = new ImageEntity();
        try {
            image.setImage(ImageUtil.compressImage(imageBytes.getBytes()));
        } catch (IOException ex) {
            throw new DispatcherException("Can't proceed image");
        }
        image.setGoods(medication.get());
        imageRepository.saveAndFlush(image);
    }

    public byte[] getImage(Long medicationId) {
        var image = imageRepository.findByGoodsId(medicationId);
        if (image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Image not found");
        }
        return ImageUtil.decompressImage(image.get().getImage());
    }
}
