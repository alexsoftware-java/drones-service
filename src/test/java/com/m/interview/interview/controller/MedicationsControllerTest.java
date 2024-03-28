package com.m.interview.interview.controller;

import com.m.interview.interview.dto.Model;
import com.m.interview.interview.dto.State;
import com.m.interview.interview.entity.DroneEntity;
import com.m.interview.interview.entity.GoodsEntity;
import com.m.interview.interview.entity.ImageEntity;
import com.m.interview.interview.repository.DronesRepository;
import com.m.interview.interview.repository.GoodsRepository;
import com.m.interview.interview.repository.ImageRepository;
import com.m.interview.interview.service.DispatcherScheduledService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;

import java.io.InputStream;

import static com.m.interview.interview.utils.ImageUtil.compressImage;
import static com.m.interview.interview.utils.ImageUtil.decompressImage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests.
 */
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@MockBean(DispatcherScheduledService.class) // disable scheduler by mocking it
@AutoConfigureMockMvc
class MedicationsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private DronesRepository dronesRepository;
    @SpyBean
    private GoodsRepository goodsRepository;
    @SpyBean
    private ImageRepository imageRepository;
    private DroneEntity droneEntity;
    private GoodsEntity goodsEntity;

    @BeforeEach
    void init(){
        droneEntity = new DroneEntity();
        droneEntity.setModel(Model.CRUISERWEIGHT);
        droneEntity.setSerialNumber("DRN_123456");
        droneEntity.setState(State.IDLE);
        droneEntity.setBatteryCapacity(100);
        droneEntity.setWeightLimit(300);

        goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsType(1);
        goodsEntity.setName("ASPIRIN");
        goodsEntity.setCode("ASP-1");
        goodsEntity.setWeight(25);
        goodsEntity.setDrone(droneEntity);
    }

    @Test
    void addMedication() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        // when
        mockMvc.perform(post("/api/v1/drones/DRN_123456/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                       "name":"ASPIRIN",
                                       "weight":100,
                                       "code":"ASP_1"
                                }"""))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        [{"id":5,"name":"ASPIRIN","weight":100,"code":"ASP_1","imageId":null}]"""));

    }

    @Test
    void addMedicationBadRequestCodeValidationFailed() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        // when
        mockMvc.perform(post("/api/v1/drones/DRN_123456/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                       "name":"1_ASPIRIN",
                                       "weight":100,
                                       "code":"asp_1"
                                }"""))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string("""
                        Error occurred, request validation failed! addMedicationBySN.medicationRequestDto: Medication request validation failed!
                        Please check: name (allowed only letters, numbers, ‘-‘, ‘_’)',
                        code (allowed only upper case letters, underscore and numbers), max weight=500, max image size=1MB"""));
        verify(goodsRepository, times(0)).saveAndFlush(any(GoodsEntity.class));
    }

    @Test
    void getMedication() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        goodsRepository.save(goodsEntity);
        // when
        mockMvc.perform(get("/api/v1/drones/DRN_123456/medication"))
        // then
                .andExpect(status().isOk())
                .andExpect(content().string("""
                        [{"id":5,"name":"ASPIRIN","weight":25,"code":"ASP-1","imageId":null}]"""));
    }

    @Test
    void addImage() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        goodsRepository.save(goodsEntity);
        try (InputStream s = new ClassPathResource("image/image1.png").getInputStream()) {
            byte[] bytes = StreamUtils.copyToByteArray(s);
            MockMultipartFile file = new MockMultipartFile("image", "image1.png", MediaType.IMAGE_PNG_VALUE, bytes);
            // when
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/drones/DRN_123456/medication/5/image")
                            .file(file)
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .characterEncoding("UTF-8"))
                    // then
                    .andExpect(status().isOk());
        }
    }

    @Test
    void addImageEmptyFileTest() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        goodsRepository.save(goodsEntity);
        final MockMultipartFile image = new MockMultipartFile("image.png", "image.png", "image/png", MedicationsControllerTest.class.getResourceAsStream("image/image_null.png"));
        // when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/drones/DRN_123456/medication/5/image")
                        .file("image", image.getBytes())
                )
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void addImageWrongFileTest() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        goodsRepository.save(goodsEntity);
        MockMultipartFile wrongFile = new MockMultipartFile("test.json", "test.json", "application/json", "{}".getBytes());
        // when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/drones/DRN_123456/medication/5/image")
                        .file("image", wrongFile.getBytes())
                )
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Dispatcher can't process your request: Can't add image of medication - photo is not in png format"));
    }

    @Test
    void getImage() throws Exception {
        // given
        dronesRepository.save(droneEntity);
        goodsRepository.save(goodsEntity);
        var imageEntity = new ImageEntity();
        imageEntity.setGoods(goodsEntity);
        byte[] expected;
        try (InputStream s = new ClassPathResource("image/image1.png").getInputStream()) {
            expected = compressImage(StreamUtils.copyToByteArray(s));
            imageEntity.setImage(expected);
            imageRepository.save(imageEntity);
        }
        // when
        mockMvc.perform(get("/api/v1/drones/DRN_123456/medication/5/image"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(decompressImage(expected)))
        ;
    }
}