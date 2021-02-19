package com.menezes.santanarankbackend.dataprovider;

import com.menezes.santanarankbackend.domain.Match;
import com.menezes.santanarankbackend.domain.Team;
import com.menezes.santanarankbackend.services.MatchService;
import com.menezes.santanarankbackend.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Configuration
@EnableAsync
@EnableScheduling
public class BuscarDadosDoMZ {

    @Autowired
    MatchService matchService;

    @Autowired
    TeamService teamService;



    @Scheduled(cron = "* 5 * * * ?")
    public void buscarDadosParaCadaTime() throws IOException, SAXException, ParserConfigurationException {
        //SEDS
        buscarDadosDoMZ("http://www.managerzone.com/xml/team_matchlist.php?sport_id=1&team_id=1171006&match_status=1&limit=100");
        //ATMS
        buscarDadosDoMZ("http://www.managerzone.com/xml/team_matchlist.php?sport_id=1&team_id=1167818&match_status=1&limit=100");
        //CGF
        buscarDadosDoMZ("http://www.managerzone.com/xml/team_matchlist.php?sport_id=1&team_id=1171006&match_status=1&limit=100");
    }

    public void buscarDadosDoMZ(String uri) throws ParserConfigurationException, IOException, SAXException {
        List<Match> matches = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(uri);
        doc.getDocumentElement().normalize();

        Element managerZone_MatchList = doc.getDocumentElement();
        System.out.println("Root element :" + managerZone_MatchList.getNodeName());

        NodeList matchNodes = managerZone_MatchList.getElementsByTagName("Match");

        for (int i = 0; i < matchNodes.getLength(); i++) {
            Node matchNode = matchNodes.item(i);
            if (matchNode.getNodeType() == Node.ELEMENT_NODE) {
                Element matchElement = (Element) matchNode;
                NodeList teamNodes = matchElement.getElementsByTagName("Team");

                List<Team> teams = new ArrayList<>();
                List<Integer> teamsGoals = new ArrayList<>();

                for (int j = 0; j < teamNodes.getLength(); j++) {
                    Node teamNode = teamNodes.item(j);
                    if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element teamElement = (Element) teamNode;
                        Team team = new Team(Long.parseLong(teamElement.getAttribute("teamId")), teamElement.getAttribute("teamName"));
                        teamService.save(team);
                        teams.add(team);
                        teamsGoals.add(Integer.parseInt(teamElement.getAttribute("goals")));
                    }
                }

                Match match = new Match(
                        Long.parseLong(matchElement.getAttribute("id")),
                        matchElement.getAttribute("date"),
                        matchElement.getAttribute("status"),
                        matchElement.getAttribute("type"),
                        matchElement.getAttribute("typeName"),
                        teams.get(0),
                        teams.get(1),
                        teamsGoals.get(0),
                        teamsGoals.get(1)
                );

                matchService.save(match);

            }
        }
    }

}

