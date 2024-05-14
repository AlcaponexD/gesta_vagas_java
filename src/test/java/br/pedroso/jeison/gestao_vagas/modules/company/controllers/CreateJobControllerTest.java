package br.pedroso.jeison.gestao_vagas.modules.company.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.print.attribute.standard.Media;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.pedroso.jeison.gestao_vagas.exceptions.CompanyNotFoundException;
import br.pedroso.jeison.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.pedroso.jeison.gestao_vagas.modules.company.entities.CompanyEntity;
import br.pedroso.jeison.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.pedroso.jeison.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
        private MockMvc mvc;

        @Value("${security.token.secret}")
        private String secretKey;

        @Autowired
        private CompanyRepository companyRepository;

        @Autowired
        private WebApplicationContext context;

        @Before
        public void setUp() {
                this.mvc = MockMvcBuilders.webAppContextSetup(context)
                                .apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

        @Test
        public void should_be_able_to_create_a_new_job() throws Exception {

                var company = CompanyEntity.builder()
                                .description("COMPANY_DESCRIPTION")
                                .email(("com.example@example.com"))
                                .password("1234567890")
                                .username("COMPANY_USERNAME")
                                .name("COMPANY_NAME")
                                .build();

                company = companyRepository.saveAndFlush(company);

                System.out.println("ID XXX " + company.getId());

                CreateJobDTO createJobDTO = CreateJobDTO.builder()
                                .benefits(("BENEFICTS_TESTE"))
                                .description("DESCRIPTION")
                                .level("LEVEL_TEST")
                                .build();

                System.out.println(secretKey);

                var result = mvc.perform(
                                MockMvcRequestBuilders.post("/company/job/")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(TestUtils.objectToJSON(createJobDTO))
                                                .header("Authorization",
                                                                TestUtils.generateToken(company.getId(), secretKey)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
                System.out.println(result);

        }

        @Test
        public void shoud_not_be_able_to_create_new_job_without_company_not_found() throws Exception {
                CreateJobDTO createJobDTO = CreateJobDTO.builder()
                                .benefits(("BENEFICTS_TESTE"))
                                .description("DESCRIPTION")
                                .level("LEVEL_TEST")
                                .build();

                System.out.println(secretKey);

                try {
                        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(TestUtils.objectToJSON(createJobDTO))
                                        .header("Authorization",
                                                        TestUtils.generateToken(UUID.randomUUID(), secretKey)));
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(CompanyNotFoundException.class);
                }
        }
}
