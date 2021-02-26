package com.epam.jwd_final.web.command.bookmaker;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Parameter;
import com.epam.jwd_final.web.command.RequestContext;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.page.ShowBookmakerPage;
import com.epam.jwd_final.web.domain.Match;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.exception.ListenerException;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.observer.PayoutUserWinListener;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;
import com.epam.jwd_final.web.service.impl.BetServiceImpl;
import com.epam.jwd_final.web.service.impl.MatchServiceImpl;
import com.epam.jwd_final.web.service.impl.MultiplierServiceImpl;

public enum SetResultCommand implements Command {

    INSTANCE;

    private final MatchService matchService;
    private final MultiplierService multiplierService;
    private final BetService betService;

    SetResultCommand() {
        this.matchService = MatchServiceImpl.INSTANCE;
        this.multiplierService = MultiplierServiceImpl.INSTANCE;
        this.betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext req) throws CommandException {
        try {
            final int matchId = req.getIntParameter(Parameter.MATCH_ID.getValue());
            final String newResultStr = req.getStringParameter(Parameter.RESULT_TYPE.getValue());
            Result result;
            final Match match = matchService.findById(matchId);
            if (match.getFirstTeam().equals(newResultStr)) {
                result = Result.FIRST_TEAM;
            } else if (match.getSecondTeam().equals(newResultStr)) {
                result = Result.SECOND_TEAM;
            } else if (Result.DRAW.name().equals(newResultStr.toUpperCase())) {
                result = Result.DRAW;
            } else {
                result = Result.NO_RESULT;
            }
            matchService.updateResult(matchId, result);
            new PayoutUserWinListener().update("payoutUserWin", req); // TODO: add to everyone setting userBalance

            betService.deleteAllByMultiplierId(
                    multiplierService.findIdByMatchIdAndResult(matchId, Result.FIRST_TEAM)
            );
            betService.deleteAllByMultiplierId(
                    multiplierService.findIdByMatchIdAndResult(matchId, Result.SECOND_TEAM)
            );
            betService.deleteAllByMultiplierId(
                    multiplierService.findIdByMatchIdAndResult(matchId, Result.DRAW)
            );

            return ShowBookmakerPage.INSTANCE.execute(req);
        } catch (ListenerException | ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }
    }
}