package pwr.zpi.socialballspring.service;

import pwr.zpi.socialballspring.dto.MatchMemberDto;
import pwr.zpi.socialballspring.dto.Response.MatchMemberResponse;

import java.util.List;

public interface MatchMemberService {
    MatchMemberResponse save(MatchMemberDto matchMember);

    List<MatchMemberResponse> findAll();

    void delete(long id);

    MatchMemberResponse findById(long id);

    MatchMemberResponse update(MatchMemberDto matchMember, long id);
}
