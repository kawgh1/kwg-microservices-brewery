package com.kwgdev.msscbeerservice.web.controller;

import com.kwgdev.msscbeerservice.bootstrap.BeerLoader;
import com.kwgdev.brewery.model.BeerDto;
import com.kwgdev.brewery.model.BeerStyleEnum;
import com.kwgdev.msscbeerservice.service.BeerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

/**
 * created by kw on 12/11/2020 @ 2:27 PM
 */


@ExtendWith(RestDocumentationExtension.class)
// updates URI under target/generated-snippets/curl-request
// way to customize URI in your generated Spring REST Docs, different servers, etc.
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
@WebMvcTest(BeerController.class)
//@ComponentScan(basePackages = "com.kwgdev.msscbeerservice.web.mappers") unresolvable
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {
        given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                // Spring REST Docs query param
                .param("iscold", "yes")


                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                // Spring REST Docs
                .andDo(document("v1/beer-get", pathParameters(
                        parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestParameters(
                                parameterWithName("iscold").description("Is Beer Cold Query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("ID of Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("lastModifiedDate").description("Date Updated"),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("Beer UPC"),
                                fieldWithPath("price").description("Beer Price"),
                                fieldWithPath("quantityOnHand").description("Beer Quantity on Hand")

                        )));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto =  getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        // ConstrainedFields
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())

                // Spring REST Docs
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Beer UPC").attributes(),
                                fields.withPath("price").description("Beer price"),
                                fields.withPath("quantityOnHand").ignored()
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto =  getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    // used to generate Spring REST docs under target/generated-docs and generated-snippets folders

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }


}