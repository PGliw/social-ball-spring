package pwr.zpi.socialballspring.dto.Response;

import lombok.Data;
import pwr.zpi.socialballspring.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamResponse {
    private Long id;
    private String name;
    private Integer membersCount;
    private String shirtColours;
    private Long footballMatchId;
    private List<MatchMemberResponse> teamMembers;

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.membersCount = team.getMembersCount();
        this.shirtColours = team.getShirtColours();
        this.name = team.getName();
        if (team.getFootballMatch() != null) {
            this.footballMatchId = team.getFootballMatch().getId();
            this.teamMembers = team.getTeamMembers() != null ?
                    team.getTeamMembers()
                            .stream()
                            .map(MatchMemberResponse::new)
                            .collect(Collectors.toList())
                    :
                    new ArrayList<>();
        }
    }
}
