package graduation.raitrest.web.vote;


import graduation.raitrest.service.VoteService;
import graduation.raitrest.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static graduation.raitrest.TestUtil.userHttpBasic;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.VotesTestData.contentJsonTo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RatingRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RatingRestController.REST_URL + '/';

    @Autowired
    private VoteService service;



    @Test
    void getCurrentDayRatingRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL ).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(service.getTodayRating()));
    }
    @Test
    void filter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "all")
//                .param("startDate", "2019-09-03")
//                .param("endDate", "2019-09-03"))
                .param("startDate", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .param("endDate", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)).with(userHttpBasic(USER_1)))

                .andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonTo(service.getTodayRating()));
    }
    @Test
    void getRatingRestaurants() throws Exception {

        ResultMatcher matcher = contentJsonTo(service.getAllRating());
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "all").with(userHttpBasic(USER_3)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher);

    }

}