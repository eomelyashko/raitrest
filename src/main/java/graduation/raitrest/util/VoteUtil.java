package graduation.raitrest.util;


import graduation.raitrest.model.entities.Vote;
import graduation.raitrest.model.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    public static VoteTo Vote_2_VoteTo (Vote vote) {
        return new VoteTo(vote.id(),vote.getRestaurant().id(),vote.getDateTime(),vote.getUser().id());
    }

    public static List<VoteTo> Vote_2_VoteTo(List<Vote> voteList) {
        return voteList.stream().map(vote -> Vote_2_VoteTo(vote)).collect(Collectors.toList());
    }

}
