import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

  Map<String, Integer> teamsToId;
  Map<Integer, String> idToTeams;
  int[] wins;
  int[] looses;
  int[] remaining;
  int[][] games;
  boolean[] eliminated;
  // Not sure about this
  Map<Integer, Set<Integer>> certificates;

  /**
   * Create a baseball division from given filename in format specified below
   *
   * @param filename
   */
  public BaseballElimination(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null");
    }

    In in = new In(filename);
    int amountTeams = in.readInt();
    // Initialize variables
    teamsToId = new HashMap<>();
    idToTeams = new HashMap<>();
    wins = new int[amountTeams];
    looses = new int[amountTeams];
    remaining = new int[amountTeams];
    games = new int[amountTeams][amountTeams];
    certificates = new HashMap<>();

    for (int i = 0; i < amountTeams; i++) {
      String teamName = in.readString();
      teamsToId.put(teamName, i);
      idToTeams.put(i, teamName);
      certificates.put(i, new TreeSet<>());
      wins[i] = in.readInt();
      looses[i] = in.readInt();
      remaining[i] = in.readInt();
      for (int j = 0; j < amountTeams; j++) {
        games[i][j] = in.readInt();
      }
    }
    eliminated = new boolean[amountTeams];

    trivialElimination();
    nonTrivialElimination();

  }

  private void trivialElimination() {
    for (int x = 0; x < wins.length; x++) {
      int maxWinsX = wins[x] + remaining[x];
      for (int i = 0; i < wins.length; i++) {
        if (x == i) {
          assert (games[x][i] == 0);
          continue;
        }
        if (maxWinsX < wins[i]) {
          eliminated[x] = true;
          certificates.get(x).add(i);
        }
      }
    }
  }

  private void nonTrivialElimination() {
    int numberOfTeams = wins.length;
    final int gameVertices = ((numberOfTeams - 1)
        * (numberOfTeams - 2)) / 2;
    final int totalVertices = gameVertices + numberOfTeams - 1 + 2;

    for (int team = 0; team < numberOfTeams; team++) {
      // We avoid computing the ones trivialy eliminated
      if (eliminated[team]) {
        continue;
      }

      // We start using their API
      evaluateMaxFlowFor(team, totalVertices);
    }
  }

  private void evaluateMaxFlowFor(int team, int totalVertices) {
    int idTeam = teamsToId.get(team);
    FlowNetwork graphFlow = new FlowNetwork(totalVertices);

  }

  // number of teams
  public int numberOfTeams() {
    return teamsToId.size();
  }

  // all teams
  public Iterable<String> teams() {
    return new ArrayList<>(teamsToId.keySet());
  }

  // number of wins for given team
  public int wins(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);

    return wins[id];
  }

  private int getTeamId(String team) {
    if (teamsToId.get(team) == null) {
      throw new IllegalArgumentException("Team requested doesn't exist");
    }
    return teamsToId.get(team);
  }

  // number of losses for given team
  public int losses(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);

    return looses[id];
  }

  // number of remaining games for given team
  public int remaining(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);

    return remaining[id];
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    if (team1 == null || team2 == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id1 = getTeamId(team1);
    int id2 = getTeamId(team2);
    return games[id1][id2];
  }

  // is given team eliminated?
  public boolean isEliminated(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);
    return eliminated[id];
  }

  // subset R of teams that eliminates given team; null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return null;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      StdOut.print(team + " ");
      // if (division.isEliminated(team)) {
      // StdOut.print(team + " is eliminated by the subset R = { ");
      // for (String t : division.certificateOfElimination(team)) {
      // StdOut.print(t + " ");
      // }
      // StdOut.println("}");
      // } else {
      // StdOut.println(team + " is not eliminated");
      // }
    }
  }

}
