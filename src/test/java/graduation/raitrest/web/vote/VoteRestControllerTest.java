package graduation.raitrest.web.vote;

import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.VoteTo;
import graduation.raitrest.service.VoteService;
import graduation.raitrest.web.AbstractControllerTest;
import graduation.raitrest.web.json.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static graduation.raitrest.RestoranTestData.*;
import static graduation.raitrest.TestUtil.*;
import static graduation.raitrest.UserTestData.*;
import static graduation.raitrest.VotesTestData.assertMatch;
import static graduation.raitrest.VotesTestData.contentJson;
import static graduation.raitrest.VotesTestData.*;
import static graduation.raitrest.util.ValidationUtil.MAX_TIME;
import static graduation.raitrest.util.VoteUtil.Vote_2_VoteTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService service;


    @Test
    void get() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTES_ID).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatchTo(readFromJsonMvcResult(result, VoteTo.class), Vote_2_VoteTo(VOTES_1_YESTERDAY)));

    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTES_ID)
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + VOTES_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {

        // проверяем что голос есть
        Vote vote = service.getTodayVote(USER_2_ID);
        Assertions.assertNotNull(vote);
        ;
        // удаляем
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + (VOTES_ID + 6)).with(userHttpBasic(USER_2)))
                .andExpect(status().isNoContent());
        // проверяенм что голоса нет
        vote = service.getTodayVote(USER_2_ID);
        Assertions.assertNull(vote);
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + VOTES_ID)
                .with(userHttpBasic(USER_2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAllByUser() throws Exception {


        List<Vote> collect = VOTE_LIST.stream().filter(vote -> vote.getUser().getId() == USER_ID)
                .sorted((o1, o2) -> o2.getDateTime().toLocalDate().compareTo(o1.getDateTime().toLocalDate()))
                .collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(collect));


    }






    @Test
    void update() throws Exception {
        Assumptions.assumeTrue(checkDateTimeIsBefore(LocalDateTime.now()), "Validation time, run before " + MAX_TIME);
      //  Vote voteUpdate = service.get(VOTES_ID + 5, USER_1_ID);
        VoteTo voteUpdate = new VoteTo(VOTES_ID + 5, RESTAURANT_PEARL.getId());

      //  voteUpdate.setRestaurantID();

        mockMvc.perform(MockMvcRequestBuilders.put(
                REST_URL + (VOTES_ID + 5)).contentType(MediaType.APPLICATION_JSON).with(userHttpBasic(USER_1))
                .content(JsonUtil.writeValue(voteUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote expected = service.get(voteUpdate.getId());
        VoteTo expectedTo = new VoteTo(expected.getId(),expected.getRestaurant().getId());
        assertMatchTo(voteUpdate, expectedTo);
    }



    @Test
    void createByVote() throws Exception {
        Assumptions.assumeTrue(checkDateTimeIsBefore(LocalDateTime.now()), "Validation time, run before " + MAX_TIME);
        VoteTo newVote = new VoteTo(RESTAURANT_STAR.id());

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER_3))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print());

        VoteTo returned = readFromJson(action, VoteTo.class);
        newVote.setId(returned.getId());
        assertMatchTo(returned, newVote);

    }

    @Test
    void createByRestaurantID() throws Exception {
        Assumptions.assumeTrue(checkDateTimeIsBefore(LocalDateTime.now()), "Validation time, run before " + MAX_TIME);
        VoteTo newVote = new VoteTo();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "restaurant/" + RESTAURANT_STAR.id())
                .with(userHttpBasic(USER_3))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        VoteTo returned = readFromJson(action, VoteTo.class);
        newVote.setRestaurantID(RESTAURANT_STAR.id());
        newVote.setId(returned.getId());
        assertMatchTo(returned, newVote);

    }

    @Test
    void createDuplicateByVote() throws Exception {
        Assumptions.assumeTrue(checkDateTimeIsBefore(LocalDateTime.now()), "Validation time, run before " + MAX_TIME);
        VoteTo newVote = new VoteTo(RESTAURANT_STAR.id());

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER_3))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print());

        VoteTo returned = readFromJson(action, VoteTo.class);
        newVote.setId(returned.getId());
        assertMatchTo(returned, newVote);

         newVote = new VoteTo(RESTAURANT_SUPER_STAR.id());
         action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER_3))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print());
                 //.andExpect(status().isUnprocessableEntity());
        returned = readFromJson(action, VoteTo.class);
        newVote.setId(returned.getId());
        assertMatchTo(returned, newVote);

    }
    @Test
    void createDuplicateByRestaurantID() throws Exception {
        Assumptions.assumeTrue(checkDateTimeIsBefore(LocalDateTime.now()), "Validation time, run before " + MAX_TIME);
        VoteTo newVote = new VoteTo();

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "restaurant/" + RESTAURANT_STAR.id())
                .with(userHttpBasic(USER_3))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        VoteTo returned = readFromJson(action, VoteTo.class);
        newVote.setRestaurantID(RESTAURANT_STAR.id());
        newVote.setId(returned.getId());
        assertMatchTo(returned, newVote);

        action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "restaurant/" + RESTAURANT_SUPER_STAR.id())
                .with(userHttpBasic(USER_3))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        returned = readFromJson(action, VoteTo.class);
        newVote.setRestaurantID(RESTAURANT_SUPER_STAR.id());
        newVote.setId(returned.getId());
        assertMatchTo(returned, newVote);

    }
}