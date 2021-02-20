package com.epam.jwd_final.web.command.page;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.dao.MatchDao;
import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;

import java.util.Collections;

public enum ShowAllMatchesPage implements Command {

    INSTANCE;

    public static final String MATCHES_PARAMETER = "matches";

    private final MatchServiceImpl matchService;

    ShowAllMatchesPage() {
        this.matchService = new MatchServiceImpl(new MatchDao(), new TeamDao());
    }

    private static final ResponseContext ALL_MATCHES_PAGE_RESPONSE = new ResponseContext() {

        @Override
        public String getPage() {
            return "/WEB-INF/jsp/allmatches.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        req.setAttribute(MATCHES_PARAMETER, matchService.findAllUnfinishedMatches().orElse(Collections.emptyList()));
        return ALL_MATCHES_PAGE_RESPONSE;
    }
}