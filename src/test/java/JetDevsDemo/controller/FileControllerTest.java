package JetDevsDemo.controller;


import JetDevsDemo.AbstractTest;
import jetdevs.model.UploadFile;
import jetdevs.repository.FileRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileControllerTest extends AbstractTest {

    @Autowired
    private FileRepository fileRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "user_role")
    public void testFileById() throws Exception {
        UploadFile expected = fileRepository.save(new UploadFile()
                .toBuilder().fileName(RandomStringUtils
                        .randomAlphabetic(10))
                .userId(adminUser.getId())
                .totalRecords(10)
                .totalUploaded(10).build());

        String uri = "/file/" + expected.getId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(content);
    }

    @Test
    public void testListFiles() throws Exception {

        int randomNo = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        List<UploadFile> list = IntStream.range(0, randomNo).mapToObj(value -> new UploadFile()
                .toBuilder().fileName(RandomStringUtils
                        .randomAlphabetic(10))
                .userId(adminUser.getId())
                .totalRecords(10)
                .totalUploaded(10).build()).collect(Collectors.toList());
        fileRepository.saveAll(list);

        String uri = "/file/getAll";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(content, list);
    }

    @Test
    public void testDeleteById() throws Exception {

        UploadFile expected = fileRepository.save(new UploadFile()
                .toBuilder().fileName(RandomStringUtils
                        .randomAlphabetic(10))
                .userId(adminUser.getId())
                .totalRecords(10)
                .totalUploaded(10).build());

        String uri = "/file/delete/" + expected.getId();
        mvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        Optional<UploadFile> byId = fileRepository.findById(expected.getId());
        byId.ifPresent(uploadFile -> Assert.assertTrue(uploadFile.getDeleted()));
    }
}
